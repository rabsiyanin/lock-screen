plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.screenlock"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.screenlock"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    // Один и тот же ключ на любой машине — новую сборку можно ставить поверх старой.
    // Если файла нет, используется стандартный debug-ключ.
    signingConfigs {
        getByName("debug") {
            val keystore = file("debug.keystore")
            if (keystore.exists()) {
                storeFile = keystore
                storePassword = "android"
                keyAlias = "androiddebugkey"
                keyPassword = "android"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    // Java и Kotlin должны компилироваться под одну и ту же версию JVM
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}
