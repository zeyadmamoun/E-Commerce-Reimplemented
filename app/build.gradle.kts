plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("androidx.navigation.safeargs.kotlin")
    kotlin("plugin.serialization") version "2.0.0"
    // Google services Gradle plugin
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.e_commmercefixed"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.e_commmercefixed"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding =  true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion ="1.5.1"
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
}

dependencies {
    // adding compose to the view System
    implementation(platform("androidx.compose:compose-bom:2024.09.03"))
    // other dependencies
    // Compose
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.accompanist:accompanist-themeadapter-material3:0.28.0")
    // Firebase Bom
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    // firebase Ui
    implementation (libs.firebase.ui.auth)
    // basic project dependency
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization.json)
    //serialization
    implementation(libs.kotlinx.serialization.json)
    //koin
    implementation(libs.koin.android)
    // dataStore preferences
    implementation(libs.data.store)
    implementation(libs.androidx.datastore.core.android)
    // splash screen
    implementation (libs.androidx.core.splashscreen)
    //coil for compose
    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}