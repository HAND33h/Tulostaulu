plugins {
    id("com.android.application")
}

android {
    namespace = "com.hand33h.tulostaulu"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hand33h.tulostaulu.installtest"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "2.8.1-installtest"
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
}
