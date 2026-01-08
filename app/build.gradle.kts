plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.tosai"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.tosai"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // --- VERSIONS ---
    val voyagerVersion = "1.1.0-beta03" // Latest for Compose + Hilt
    val hiltVersion = "2.57.2"
    val roomVersion = "2.8.4"
    val retrofitVersion = "3.0.0"
    val lifecycleVersion = "2.10.0"

    // 1. Voyager (Navigation)
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.screenmodel) // ViewModel integration
    implementation(libs.voyager.hilt)        // Hilt integration
    implementation(libs.voyager.transitions) // Screen transitions

    // 2. Dagger Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler) // Using KSP instead of KAPT

    // 3. Room (Database)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // Coroutines support
    ksp(libs.androidx.room.compiler)       // Using KSP

    // 4. Retrofit (Networking)
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // JSON Converter

    // 5. ViewModel & Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation("androidx.compose.material:material-icons-extended:1.7.6") // Or use the BOM version
}