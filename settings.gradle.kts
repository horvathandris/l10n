rootProject.name = "localisation"

// Core library
includeBuild("l10n-gradle-plugin")
include("l10n-spring")
include("l10n-spring-boot-starter")

// Examples
include("spring-boot-example")
include("simple-example")
include("groovy-dsl-example")