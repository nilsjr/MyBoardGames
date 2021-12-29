rootProject.name = "MyBoardGames"

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
  resolutionStrategy {
    eachPlugin {
      when (requested.id.id) {
        "com.android.application", "com.android.library" -> {
          useModule("com.android.tools.build:gradle:${requested.version}")
        }
        "dagger.hilt.android.plugin" -> useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
      }
    }
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

include(":app")

enableFeaturePreview("VERSION_CATALOGS")