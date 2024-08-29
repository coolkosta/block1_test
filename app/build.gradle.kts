plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.coolkosta.simbirsofttestapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.coolkosta.simbirsofttestapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    testOptions {
        packaging {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
}

dependencies {
   implementation( libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation (libs.mockk.android)
    androidTestImplementation (libs.androidx.fragment.testing)
    debugImplementation (libs.androidx.fragment.testing.manifest)
    debugImplementation(libs.androidx.fragment.testing.manifest)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":features:profile"))
    implementation(project(":features:news"))
    implementation(project(":features:help"))
    implementation(project(":features:search"))
    implementation(project(":core"))



    //Retrofit
    implementation(libs.retrofit)
    //Gson
    implementation(libs.gson)
    //Kotlin Datetime
    implementation(libs.kotlinx.datetime)
    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    //LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    //Fragment
    implementation(libs.androidx.fragment.ktx)
    //RxJava
    implementation(libs.rxjava)
    //RxAndroid
    implementation(libs.rxandroid)
    //RxBinding
    implementation(libs.rxbinding)

    implementation(libs.rxbinding.appcompat)

    implementation(libs.rxkotlin)

    implementation(libs.rxjava2.rxjava)
    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))

    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.database)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.rxjava3.retrofit.adapter)

    implementation(platform(libs.okhttp.bom))

    // define any required OkHttp artifacts without version
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.glide)

    //Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    testImplementation(libs.mockito.core)

}