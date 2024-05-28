plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
//    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    namespace = "com.example.todo_list"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todo_list"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources {
            excludes += "mockito-extensions/org.mockito.plugins.MockMaker"
        }
    }



}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.test.espresso:espresso-intents:3.5.1")

    // JUnit for unit tests
//    testImplementation("junit:junit:4.13.2")

    // AndroidX Test-Instrumented testing
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation("androidx.test:core:1.4.0")

    // Mockito for unit testing
    // Mockito dependencies for JDK 17 compatibility
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.mockito:mockito-inline:4.6.1")
    androidTestImplementation("org.mockito:mockito-android:4.6.1")
//
//    // Robolectric for unit testing
//    testImplementation("org.robolectric:robolectric:4.6")

    // JUnit for unit tests
    androidTestImplementation ("junit:junit:4.13.2")

    // AndroidX Test - Instrumented testing
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation ("androidx.test:core:1.3.0")
    androidTestImplementation ("androidx.test:runner:1.3.0")
    androidTestImplementation ("androidx.test:rules:1.3.0")


    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.4.0")

    // Mockito for unit testing
    testImplementation ("org.mockito:mockito-core:4.6.1")


//    testImplementation ("org.mockito:mockito-inline:4.6.1")
    androidTestImplementation ("org.mockito:mockito-android:4.6.1")

    // Robolectric for unit testing
    testImplementation ("org.robolectric:robolectric:4.6")

    // PowerMockito for unit testing
    testImplementation ("org.powermock:powermock-api-mockito2:2.0.9")
    testImplementation ("org.powermock:powermock-module-junit4:2.0.9")
    androidTestImplementation ("org.powermock:powermock-api-mockito2:2.0.9")
    androidTestImplementation ("org.powermock:powermock-module-junit4:2.0.9")
//    androidTestImplementation ("androidx.recyclerview:recyclerview:1.2.1")
    testImplementation ("org.mockito:mockito-core:4.2.0")
    testImplementation ("org.mockito:mockito-inline:4.2.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.4.0")

   //Option fragment grid style
    implementation ("androidx.gridlayout:gridlayout:1.0.0")
   //Cardview animation
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
}

//apply plugin: 'com.google.gms.google-services'