import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("multiplatform") version "2.0.20"
}

kotlin {
    js {
        moduleName = "lib-math"
        compilations["main"].packageJson {
            name = "your-custom-library-name"
            version = "1.0.09"
            main = "your-library.js" // This is the entry point of your library
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
        destinationDirectory.set(file("$buildDir/your-output-directory"))
    }

    tasks.register<Copy>("renameJsFile") {
        dependsOn("compileKotlinJs")
        from(file("$buildDir/your-output-directory"))
        into(file("$buildDir/your-output-directory"))
        rename("your-library\\.js", "custom-library-name.js")
    }
}



