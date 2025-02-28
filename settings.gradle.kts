pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Gateway Android SDK repo
        maven {
            url = uri("/home/programmer/AndroidStudioProjects/MasterCardGatewayTest/Mobile_SDK_Android-2.0.12/gateway-repo")
        }
    }
}

rootProject.name = "MasterCardGatewayTest"
include(":app")
