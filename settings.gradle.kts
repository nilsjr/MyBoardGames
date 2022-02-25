/*
 * Created by Nils Druyen on 01-23-2022
 * Copyright © 2022 Nils Druyen. All rights reserved.
 */

rootProject.name = "MyBoardGames"

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
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