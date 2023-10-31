import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.google.firebase:firebase-bom:30.4.1")
    implementation ("com.google.android.gms:play-services-auth:20.3.0")
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation ("com.google.firebase:firebase-auth-ktx:21.0.8")

    implementation ("com.google.firebase:firebase-firestore-ktx:24.3.0")
    implementation ("com.google.firebase:firebase-storage-ktx:20.0.2")
    implementation ("com.github.bumptech.glide:glide:4.13.2")
    implementation ("com.firebaseui:firebase-ui-storage:8.0.1")
    implementation ("com.github.bumptech.glide:compiler:4.13.2")

    implementation ("com.google.firebase:firebase-messaging-ktx:23.0.8")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.1.1")
}