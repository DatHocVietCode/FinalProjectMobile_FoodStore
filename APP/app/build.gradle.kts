plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.app_foodstore"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.app_foodstore"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.circleimageview)
    implementation(libs.glide)
    implementation(libs.circleindicator)
    implementation(fileTree(mapOf(
        "dir" to "D:\\FinalProject\\zalopay",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf("")
    )))
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.5")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}