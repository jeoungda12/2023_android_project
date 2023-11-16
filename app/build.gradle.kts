plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("com.google.gms.google-services")

    //firebase db
    id("kotlin-kapt")
}

android {
    namespace = "com.example.a2023_android_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.a2023_android_project"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth-ktx:22.2.0")
    implementation("androidx.annotation:annotation:1.6.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.naver.maps:map-sdk:3.17.0")
    implementation ("com.google.android.gms:play-services-location:20.0.0")

    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("androidx.multidex:multidex:2.0.1")

    //CameraX
    val camerax_version = "1.1.0-beta01"
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-video:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}")
    implementation("androidx.camera:camera-extensions:${camerax_version}")
    implementation("io.coil-kt:coil-compose:2.3.0")

    val activity_version = "1.8.0"
    implementation("androidx.activity:activity-ktx:$activity_version")

    //image 처리 관련 Coil 라이브러리
    implementation("io.coil-kt:coil:2.0.0-rc03")

    //facebook
    implementation("com.facebook.android:facebook-login:latest.release")

    //firebase db
    implementation("com.google.firebase:firebase-firestore-ktx:24.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation("com.github.bumptech.glide:glide:4.13.2")
    implementation("com.firebaseui:firebase-ui-storage:8.0.1")
    implementation("com.github.bumptech.glide:compiler:4.13.2")
}