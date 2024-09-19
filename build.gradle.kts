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
}



