plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.renta"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.renta"
        minSdk = 24
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Kotlin extensions for Android
    implementation("androidx.core:core-ktx:1.12.0")

    // AppCompat library for backward compatibility
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Material Design components for UI
    implementation("com.google.android.material:material:1.10.0")

    // ConstraintLayout for flexible layouts
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // Updated ConstraintLayout version
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Lifecycle components for ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Jetpack Compose dependencies for UI development
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Navigation components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")

    // Annotations for AndroidX
    implementation("androidx.annotation:annotation:1.7.0")

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Additional Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-runtime:2.3.1")

    // Google Play services and Firebase dependencies
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.android.identity:identity-credential:20231002")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation("com.google.android.gms:play-services-cast-framework:21.3.0")

    // ImagePicker library and Glide for image loading
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    // SwipeRefreshLayout for pull-to-refresh functionality
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
}
