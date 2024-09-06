plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.20"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" // this version matches your Kotlin version
}

android {
    namespace = "com.example.pokemons"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pokemons"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.converter.gson)
    implementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // compose & hilt navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // serialization
    implementation(libs.kotlinx.serialization.json)

    // api client
    implementation(libs.retrofit)

    // coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.kt.coil.gif)

    // compose ui
    implementation(libs.androidx.constraintlayout.compose)

    // Test rules and transitive dependencies:
    androidTestImplementation(libs.ui.test.junit4)
    // Needed for createComposeRule(), but not for createAndroidComposeRule<YourActivity>():
    debugImplementation(libs.ui.test.manifest)
    testImplementation(libs.robolectric) //Robolectric
    testImplementation(libs.ui.test.junit4) // compose test

    // For Robolectric tests.
    testImplementation(libs.google.hilt.android.testing)
    // ...with Kotlin.
    kaptTest(libs.com.google.dagger.hilt.android.compiler.v244)
    // ...with Java.
    testAnnotationProcessor(libs.com.google.dagger.hilt.android.compiler.v244)

    // For instrumented tests.
    androidTestImplementation(libs.google.hilt.android.testing)
    // ...with Kotlin.
    kaptAndroidTest(libs.com.google.dagger.hilt.android.compiler.v244)
    // ...with Java.
    androidTestAnnotationProcessor(libs.com.google.dagger.hilt.android.compiler.v244)
    // MockK
    testImplementation(libs.mockk)
    // SLF4J Android implementation
    testImplementation(libs.slf4j.android)
    // compose navigation test
    androidTestImplementation(libs.navigation.testing)
    testImplementation(libs.kotlin.test.junit)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}