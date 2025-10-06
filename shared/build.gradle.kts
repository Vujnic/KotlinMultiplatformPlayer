import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    // iOS ciljevi
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    js(IR) {
        browser ()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // zajedničke zavisnosti za sve platforme
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting
        val androidUnitTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }
        val jsTest by getting
    }
}

android {
    namespace = "com.example.project1"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.register("packForXcode", Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"

    val target = when {
        sdkName.startsWith("iphoneos") -> "iosArm64"
        sdkName.startsWith("iphonesimulator") -> "iosSimulatorArm64"
        else -> error("Unknown SDK_NAME: $sdkName")
    }

    val framework = kotlin.targets
        .withType<KotlinNativeTarget>()
        .getByName(target)
        .binaries.getFramework(mode)

    dependsOn(framework.linkTask)
    from({ framework.outputDirectory })
    into(targetDir)
}