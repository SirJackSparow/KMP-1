import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildKonfig)
    kotlin("plugin.serialization") version "2.0.0"
}

kotlin {

    val ktorVersion = "2.3.7"

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
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

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.kotlinx.datetime)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation(libs.bundles.ktor)
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            //coil
            implementation(libs.bundles.coil)
            //news

            //koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeViewModel)

            //napier
            api(libs.napier)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android.v237)
            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:$ktorVersion")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.cleanarchitecturekmm2024"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

buildkonfig {
    packageName = "com.example.cleanarchitecturekmm2024"
    defaultConfigs {
        buildConfigField(
            STRING,
            "API_KEY",
            gradleLocalProperties(rootDir).getProperty("api_key") ?: ""
        )
        buildConfigField(
            STRING,
            "BASE_URL",
            gradleLocalProperties(rootDir).getProperty("base_url") ?: ""
        )

    }

}
