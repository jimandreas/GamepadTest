import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.bammellab.gamepadtest"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.bammellab.gamepadtest"
        minSdk = 24
        targetSdk = 36

        // HIGHER than your current highest production code (1040130)
        versionCode = 1050000

        // The version string visible to users
        versionName = "1.5.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("C:/a/j/bammellab/keystoresBammellab.jks")
            storePassword = localProperties.getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = localProperties.getProperty("RELEASE_KEY_ALIAS")
            keyPassword = localProperties.getProperty("RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    buildFeatures {
        buildConfig = true
    }
}


// https://github.com/sauravjha/kotlin-application-multiple-test-config/blob/master/build.gradle.kts
tasks.withType<Test> {
    useJUnitPlatform()

    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor) {}
        override fun beforeTest(testDescriptor: TestDescriptor) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}

        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            val suiteName = suite.name
            println(suiteName)
            if (suiteName.contains("Test Executor")) { // root suite
                println(
                    "Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeeded, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped"
                )

            }
        }
    })
}
// a good guide to structuring dependencies in the brave new toml world
// https://github.com/RedMadRobot/gradle-version-catalogs/blob/main/versions-androidx/libs.versions.toml
dependencies {
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.fragment)

    implementation(libs.constraintlayout)
    implementation(libs.appcompat)
    implementation(libs.androidx.preference)

    implementation(libs.androidx.cardView)
    implementation(libs.androidx.recyclerview)

    implementation(libs.google.material)

    implementation (libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.service)

//    implementation(libs.timber)

    debugImplementation(libs.androidx.fragment.testing)
    testImplementation(libs.junit)
    testImplementation(libs.jupiter)
    debugImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.espresso.core)
}