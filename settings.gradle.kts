rootProject.name = "toy_gradle_module_17"
include(":export-data")
include(":backend")
project(":export-data").projectDir = file("export")
