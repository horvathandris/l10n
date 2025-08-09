plugins {
    id("kotlin-common-conventions")
    id("publish-conventions")
}

dependencies {
    api(project(":l10n-spring"))

    implementation(libs.spring.boot.autoconfigure)
}