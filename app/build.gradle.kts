plugins {
    id("com.android.application")
	id("kotlin-android")
	id("com.google.gms.google-services")
	id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.eclipse.bot"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appAuthRedirectScheme"] = "com.redirectScheme.comm"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
			isShrinkResources = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources.excludes += "META-INF/DEPENDENCIES"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
	implementation(platform("com.google.firebase:firebase-bom:29.2.0"))
	implementation("com.google.firebase:firebase-crashlytics-ktx:18.2.9")
	implementation("com.google.firebase:firebase-analytics-ktx:20.1.0")
	implementation("androidx.preference:preference-ktx:1.2.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
	implementation("androidx.security:security-crypto-ktx:1.1.0-alpha03")
	implementation("androidx.browser:browser:1.4.0")
	implementation("androidx.core:core-ktx:1.7.0")
	implementation("androidx.appcompat:appcompat:1.4.1")
	implementation("com.google.android.material:material:1.5.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.3")
	implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
	implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
	implementation("com.github.bumptech.glide:glide:4.13.1")
	annotationProcessor("com.github.bumptech.glide:compiler:4.13.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
