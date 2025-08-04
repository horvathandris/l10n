plugins {
    id("kotlin-common-conventions")
}

dependencies {
    api(project(":l10n-spring"))

    implementation(libs.spring.boot.autoconfigure)
}