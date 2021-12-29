plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("dagger.hilt.android.plugin")
}

android {
  compileSdk = 31
  defaultConfig {
    applicationId = "de.nilsdruyen.myboardgames"
    minSdk = 24
    targetSdk = 31
    versionCode = 1
    versionName = "1.0"
  }
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    kotlinOptions {
      jvmTarget = "11"
      allWarningsAsErrors = true
      freeCompilerArgs = listOf(
        "-Xopt-in=kotlin.RequiresOptIn"
      )
    }
  }
  sourceSets.getByName("main").java.srcDirs("src/main/kotlin")
  buildFeatures {
    compose = true
    buildConfig = true
    aidl = false
    renderScript = false
    resValues = false
    shaders = false
  }
  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
  }
  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  kapt {
    correctErrorTypes = true
    arguments {
      arg("room.schemaLocation", "$projectDir/schemas")
    }
  }
}

configurations {
  // https://github.com/ben-manes/gradle-versions-plugin/issues/393#issuecomment-726275755
  create("dependencyUpdatesConfig") {
    isCanBeResolved = false
    isCanBeConsumed = true
  }
}
val dependencyUpdatesConfig by configurations
val composeVersion = libs.versions.compose.get()
dependencies {
  implementation(libs.core)

  implementation(libs.coroutines)

  implementation(libs.lifecycleRuntime)
  implementation(libs.viewModel)
  implementation(libs.viewModelCompose)
  implementation("androidx.activity:activity-compose:1.4.0")

  implementation("androidx.compose.ui:ui:$composeVersion")
  implementation("androidx.compose.material3:material3:1.0.0-alpha02")
  implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
  debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
  debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

  dependencyUpdatesConfig(libs.desugar)
  coreLibraryDesugaring(libs.desugar)

  implementation(libs.hilt)
  implementation(libs.hiltNavigation)
  kapt(libs.hiltCompiler)

  implementation(libs.room)
  kapt(libs.roomCompiler)

  implementation(libs.moshi)
  kapt(libs.moshiCompiler)
}