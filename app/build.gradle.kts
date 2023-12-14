plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.finalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finalproject"
        minSdk = 30
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {



    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
   implementation ("com.mapbox.maps:android:10.14.1")
    implementation("androidx.navigation:navigation-ui:2.7.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    implementation( "com.mapbox.search:place-autocomplete:1.0.0-rc.6")

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

        implementation("com.google.firebase:firebase-firestore")

    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("org.osmdroid:osmdroid-android:6.1.14")
//    implementation("com.mapbox.maps:android:11.0.0")
//    implementation("com.mapbox.maps:android:10.16.3") {
//        exclude(group = "com.mapbox.common", module = "okhttp")
//
//    }
//    implementation ("androidx.annotation:annotation:1.7.0")

//    implementation("androidx.annotation:annotation:11.0.0")
//    compileOnly("com.mapbox.base:annotations:11.0.0")
//    kapt("com.mapbox.base:annotations-processor:11.0.0")
//  implementation ("com.mapbox.mapboxsdk:mapbox-sdk-services:6.15.0")






}






