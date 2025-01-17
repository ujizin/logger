import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.atomicfu)
    id("module.publication")
}

kotlin {
    explicitApi()

    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    linuxX64()
    mingwX64()
    macosX64()
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            }
        }
        nodejs()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.atomicfu)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "de.halfbit.logger"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

// Customization of module.publications
publishing {
    publications.withType<MavenPublication> {
        pom {
            description.set("Minimalistic Logger for Kotlin Multiplatform")
        }
    }
}

// more dependencies fixes (it would be nice to have it in module.publications)
tasks {
    "compileTestKotlinIosSimulatorArm64" {
        mustRunAfter("signIosSimulatorArm64Publication")
    }
    "compileTestKotlinIosX64" {
        mustRunAfter("signIosX64Publication")
    }
    "compileTestKotlinIosArm64" {
        mustRunAfter("signIosArm64Publication")
    }
    "compileTestKotlinLinuxX64" {
        mustRunAfter("signLinuxX64Publication")
    }
    "compileTestKotlinMacosX64" {
        mustRunAfter("signMacosX64Publication")
    }
    "compileTestKotlinMingwX64" {
        mustRunAfter("signMingwX64Publication")
    }
}
