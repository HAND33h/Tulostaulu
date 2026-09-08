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
        versionCode = 4
        versionName = "1.3"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
