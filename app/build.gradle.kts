plugins {
    id("com.android.application")
}

android {
    namespace = "com.hand33h.tulostaulu"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hand33h.tulostaulu"
        minSdk = 26
        targetSdk = 35
        versionCode = 30001
        versionName = "3.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("com.google.mlkit:text-recognition:16.0.1")
    implementation("com.google.mlkit:translate:17.0.3")
    implementation("com.google.mlkit:language-id:17.0.6")
}
