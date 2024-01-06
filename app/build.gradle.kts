import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
//    id("com.google.relay") version "0.3.09"
}
android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("C:\\Users\\marcl\\AndroidStudioProjects\\AVZ\\keystore.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
        create("release") {
            storeFile = file("C:\\Users\\marcl\\AndroidStudioProjects\\AVZ\\keystore.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }
    namespace = "com.creamydark.avz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.creamydark.avz"
        minSdk = 29
        targetSdk = 34
        versionCode = getIncrementedVersionCode()
        versionName = "1.${getIncrementedVersionCode()}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


    }


    buildTypes {
        release {
            isMinifyEnabled = true
            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17//JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_17//JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = true
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {
    val ktor_version: String by project
    val work_version = "2.9.0"
    implementation("androidx.work:work-runtime-ktx:$work_version")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

//    implementation 'io.github.grizzi91:bouquet:1.1.2'
    implementation("io.github.grizzi91:bouquet:1.1.2")
//    implementation("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1")
//    implementation("com.github.arcanegolem:Lycoris:0.1.4")
//    implementation("com.github.barteksc:android-pdf-viewer:2.8.2")
//    implementation ("com.github.afreakyelf:Pdf-Viewer:2.0.3")
    implementation("com.google.accompanist:accompanist-permissions:0.33.2-alpha")
    implementation("id.zelory:compressor:3.0.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.2.0")
//    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.2.0")
//    implementation("com.maxkeppeler.sheets-compose-dialogs:date_time:1.2.0")
//    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.2.0")
    implementation("androidx.hilt:hilt-work:1.1.0")

    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.google.dagger:hilt-android:2.49")

    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-android-compiler:2.44")


    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Add the dependency for the Cloud Storage library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-storage-ktx")

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth-ktx")

    // Also add the dependency for the Google Play services library and specify its version

    implementation("com.google.android.gms:play-services-auth:20.7.0")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))




    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
//    implementation("com.google.firebase:firebase-analytics-ktx")


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth:22.1.0")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("androidx.core:core-ktx:1.12.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
//    implementation("androidx.compose.material:material-icons-extended")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

fun getIncrementedVersionCode(): Int {
    val versionPropertiesFile = File("version.properties")
    val properties = Properties()

    // Load existing properties
    if (versionPropertiesFile.exists()) {
        versionPropertiesFile.inputStream().use { properties.load(it) }
    }

    // Get the current version code
    val currentVersionCode = properties.getProperty("versionCode", "1").toInt()

    // Increment the version code by 1
    val newVersionCode = currentVersionCode + 1

    // Print the new version code for reference
    println("New Version Code: $newVersionCode")

    // Save the new version code to properties file
    properties.setProperty("versionCode", newVersionCode.toString())
    versionPropertiesFile.outputStream().use { properties.store(it, null) }

    return newVersionCode
}