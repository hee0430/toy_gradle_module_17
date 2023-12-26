rootProject.name = "toy_gradle_17_module"
include(":export-data")
include(":backend")
project(":export-data").projectDir = file("export")
