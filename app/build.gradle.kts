plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.test.movieapps"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.test.movieapps"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String"," API_KEY", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYzg2OGY5MDU1Yzg1OGVhMzlmYzBiMWRlMTIwNTEzOSIsIm5iZiI6MS43NDY5NzcwMDYzNTY5OTk5ZSs5LCJzdWIiOiI2ODIwYzBlZWQwNzZkMTM0YTVjODUxNTkiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.zgMk3fnTO7INAarFJMrQap2hgXoYS1t0_ew_lTCk_38\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String"," API_KEY", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYzg2OGY5MDU1Yzg1OGVhMzlmYzBiMWRlMTIwNTEzOSIsIm5iZiI6MS43NDY5NzcwMDYzNTY5OTk5ZSs5LCJzdWIiOiI2ODIwYzBlZWQwNzZkMTM0YTVjODUxNTkiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.zgMk3fnTO7INAarFJMrQap2hgXoYS1t0_ew_lTCk_38\"")
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
        buildConfig = true
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

    // Retrofit and OKHttp
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Paging 3
    implementation("androidx.paging:paging-compose:3.3.6")
    implementation("androidx.paging:paging-runtime:3.3.6")
    implementation("androidx.room:room-paging:2.6.1")
}