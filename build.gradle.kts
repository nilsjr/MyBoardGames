plugins {
    id("com.android.application") version "7.2.0-alpha06" apply false
    kotlin("android") version "1.6.10" apply false
    id("dagger.hilt.android.plugin") version "2.40.5" apply false
    id("com.github.ben-manes.versions") version "0.39.0"
}

tasks.register<Delete>("clean") {
    delete(buildDir)
}

tasks.dependencyUpdates.configure {
    gradleReleaseChannel = "current"
}