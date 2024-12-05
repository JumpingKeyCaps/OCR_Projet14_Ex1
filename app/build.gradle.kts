import com.android.build.gradle.BaseExtension

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("jacoco")
}
tasks.withType<Test> {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}
android {
    namespace = "com.kirabium.relayance"
    compileSdk = 34

    testCoverage {
        version = "0.8.8"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    defaultConfig {
        applicationId = "com.kirabium.relayance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

      //  testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner  = "io.cucumber.android.runner.CucumberAndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
            isMinifyEnabled = false
            isShrinkResources = false
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
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }




}

val androidExtension = extensions.getByType<BaseExtension>()

val jacocoTestReport by tasks.registering(JacocoReport::class) {
    dependsOn("testDebugUnitTest","connectedDebugAndroidTest", "createDebugCoverageReport")
    group = "Reporting"
    description = "Generate Jacoco coverage reports"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }


    val kotlinDebugClassesDir = fileTree("${project.buildDir}/tmp/kotlin-classes/debug/") {

        exclude("**/com/kirabium/relayance/ui/composable/*$*.class")
        exclude("**/com/kirabium/relayance/ui/activity/*$*.class")
        exclude("**/com/kirabium/relayance/ui/adapter/*$*.class")
        exclude("**/com/kirabium/relayance/ui/viewmodel/*$*.class")
        exclude("**/com/kirabium/relayance/data/service/*$*.class")
        exclude("**/com/kirabium/relayance/ui/RelayanceApplication.class")
    }




    val mainSrc = androidExtension.sourceSets.getByName("main").java.srcDirs

    classDirectories.setFrom(kotlinDebugClassesDir)
    sourceDirectories.setFrom(files(mainSrc))
    executionData.setFrom(fileTree(project.buildDir) {
          include("**/*.exec", "**/*.ec")
    })
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Espresso pour les tests UI (integration tests)
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    // Activity testing support
    androidTestImplementation ("androidx.activity:activity-compose:1.6.0")
    // Jetpack Compose Test
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.0")
    // Espresso contrib for RecyclerView testing
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.6.1")
    // Espresso Intents for Intent testing
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.6.1")
    // Jetpack Compose UI testing for Compose-based apps
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.0")
    // Jetpack Activity Compose for launching activities in tests
    androidTestImplementation ("androidx.activity:activity-compose:1.6.0")
    // Espresso core and Espresso contrib for RecyclerView testing
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")
    // JUnit for testing
    androidTestImplementation ("androidx.test.espresso:espresso-idling-resource:3.5.0")




    //Hilt (DI)
    implementation(libs.hilt)
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp(libs.hilt.compiler)



    //Cumcumber -------------
    // Cucumber pour les tests Android
    androidTestImplementation ("io.cucumber:cucumber-android:7.18.1")
    androidTestImplementation ("io.cucumber:cucumber-java:7.20.1")
    androidTestImplementation ("io.cucumber:cucumber-junit:7.20.1")



    // [Unit Test stuff]  ----------------
    testImplementation(libs.junit)
    // MockK for unit testing
    testImplementation ("io.mockk:mockk:1.13.5")
    // Coroutine testing (for runTest and TestDispatcher)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    // AndroidX Core Testing  useful for Flow testing)
    testImplementation ("androidx.arch.core:core-testing:2.2.0")


}