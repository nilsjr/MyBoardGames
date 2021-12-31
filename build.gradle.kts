/*
 * Created by Nils Druyen on 12-31-2021
 * Copyright © 2021 Nils Druyen. All rights reserved.
 */

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
  id("com.android.application") version "7.2.0-alpha06" apply false
  kotlin("android") version "1.6.10" apply false
  id("dagger.hilt.android.plugin") version "2.40.5" apply false
  id("com.github.ben-manes.versions") version "0.40.0"
  id("io.gitlab.arturbosch.detekt") version "1.19.0"

  id("com.google.gms.google-services") version "4.3.10" apply false
  id("com.google.firebase.crashlytics") version "2.8.1" apply false
}

subprojects {
  apply(plugin = "io.gitlab.arturbosch.detekt")
  configureDetekt("src/main/kotlin")
}

tasks.register<Delete>("clean") {
  delete(buildDir)
}

tasks.dependencyUpdates.configure {
  gradleReleaseChannel = "current"
}

fun Project.configureDetekt(vararg paths: String) {
  configure<DetektExtension> {
    toolVersion = "1.19.0"
    source = files(paths)
    parallel = true
    config = files("$rootDir/config/detekt-config.yml")
    buildUponDefaultConfig = true
    ignoreFailures = false
  }
  tasks.withType<Detekt>().configureEach {
    this.jvmTarget = "11"
    reports {
      xml {
        required.set(true)
        outputLocation.set(file("$buildDir/reports/detekt/detekt.xml"))
      }
      html.required.set(false)
      txt.required.set(true)
    }
  }
  dependencies {
    "detektPlugins"("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
  }
}