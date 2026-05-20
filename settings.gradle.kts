rootProject.name = "testtask"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./gradle/libs.toml"))
        }
    }
}