plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.noxinfinity.pdate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.noxinfinity.pdate"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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
        }
    }
}

dependencies {
    //ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.35.0-alpha")

    //Hilt
    implementation(libs.hilt.android)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.firebase.messaging.ktx)
    kapt(libs.hilt.android.compiler)

    //DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    //Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.compose.v222)

    //Paging 3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen)

    //Util
    implementation(libs.lottie.compose)
    implementation(libs.compose.shimmer)

    //Icon
    implementation(libs.font.awesome)
    implementation(libs.simple.icons)
    implementation(libs.feather)


    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation(libs.firebase.analytics)
    implementation(libs.google.firebase.auth)
    implementation(libs.play.services.location)
    implementation (libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.messaging)

    //Stream chat
    implementation(libs.stream.chat.android.ui.components)
    implementation(libs.stream.chat.android.client)
    implementation(libs.stream.chat.android.state)
    implementation(libs.stream.chat.android.offline)
    implementation(libs.stream.chat.android.compose)



    // Mapbox
    implementation("com.mapbox.maps:android:11.8.0") {
        exclude(group = "com.google.android.gms", module = "play-services-cronet")
    }
    implementation ("com.mapbox.extension:maps-compose:11.8.0")

    //Graphql
    implementation(libs.apollo.runtime)


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
    implementation(libs.androidx.material)
    implementation("com.composables:icons-lucide:1.0.0")


    implementation("com.mapbox.maps:android:10.14.0")

}

kapt {
    correctErrorTypes = true
}

apollo {
    service("service") {
        packageName.set("com.noxinfinity.pdate")
        mapScalarToUpload("Upload")
        introspection {
            endpointUrl.set("https:/2ec1-14-177-92-116.ngrok-free.app/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

//endpointUrl.set("https://4577-14-177-92-116.ngrok-free.app/graphql")
//8212-14-248-82-60.ngrok-free.app