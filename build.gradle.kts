import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import kotlin.io.path.Path

plugins {
    kotlin("multiplatform") version "2.0.20"
}

val wrapperName = "lib-math"
kotlin {
    js {
        moduleName = wrapperName
        compilations["main"].packageJson {
            name = "your-custom-library-name"
            version = "1.0.09"
            main = "your-library.js" // This is the entry point of your library
            customField("type", "module")
        }
        binaries.library()
        nodejs()
//        nodejs {
//            testTask {
//                environment("key", "value")
//            }
//        }
//        compilations["jsMain"].packageJson {
//            name = "your-kotlin-library"
//            version = "1.0.0"
//            main = "your-library.js"
//        }
    }

    sourceSets {
        jsMain.dependencies {
            dependencies {
                // 添加 Kotlin 协程核心库依赖
                implementation(kotlin("stdlib-js"))
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

//                implementation(kotlin("stdlib-js"))
//                implementation(kotlin("stdlib-common"))
//                implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
//                implementation(npm("react", "> 14.0.0 <=18.2.0"))
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
            }
        }
        jsTest.dependencies {
        }
    }

    tasks.named<Kotlin2JsCompile>("compileKotlinJs") {
        val buildPath = layout.buildDirectory.get().toString()
        val outputPath = Path(buildPath, "output")
        destinationDirectory.set(file(outputPath))
    }

    tasks.register<Copy>("renameJsFile") {
//        dependsOn("compileKotlinJs")
        dependsOn("build")
        val buildPath = layout.buildDirectory.get().toString()
        val sourcePath = Path(buildPath, "dist", "js", "productionLibrary")
        val targetPath = Path(buildPath, "output")

        println("-------------->> $sourcePath")
        from(file(sourcePath))
        into(file(targetPath))
        rename("$wrapperName\\.js", "MyKotlinModule.js")
    }

    tasks.register("runJS") {
        doLast {
            val jsCode = """
            import { greet, Person } from 'C:\Users\Ming\Desktop\my\KotlinJSSample\build\output\MyKotlinModule.js';

            console.log(greet('Ming'));  
            
            const person = new Person('Alice');
            console.log(person.sayHello());  
        """
            // 执行JavaScript代码
            exec {
                commandLine("node", "-e", jsCode)
            }
        }
    }

    tasks.register("testLib") {
        val buildPath = layout.buildDirectory.get().toString()
        val sourcePath = Path(buildPath, "dist", "js", "productionLibrary")
        // 将路径中的 "/" 替换为 "\"
        val modulePath = sourcePath.toString().replace("\\", "/")
        doLast {
            val jsCode = """
                            import * as Stdlib from "${modulePath}/kotlin-kotlin-stdlib.js";
                            import * as AtomicfuRuntime from "${modulePath}/kotlin-kotlinx-atomicfu-runtime.js";
                            import * as Atomicfu from "${modulePath}/kotlinx-atomicfu.js";
                            import * as CoroutinesCore from "${modulePath}/kotlinx-coroutines-core.js";
                            import * as LibMath from "${modulePath}/lib-math.js";
                            
                            const libKotlinJs = globalThis["lib-kotlin-js"];
                            // 创建 Calculator 实例
                            const calculator = new libKotlinJs.Calculator();
                            
                            // 调用 add 方法
                            const result = calculator.add(5, 3);
                            console.log(result); // 输出 8
                            
                            // 调用 hello 方法
                            libKotlinJs.hello(); // 输出 'hi'
                            
                            libKotlinJs.hello2();
                            
                            // 使用协程
                            libKotlinJs.hello4();
                        """
            // 执行JavaScript代码
            exec {
                commandLine("node", "--experimental-modules" , "-e",  jsCode)
            }
        }
    }



}



