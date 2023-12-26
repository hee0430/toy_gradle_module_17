group = "com.hhlee"
version = "0.0.1-SNAPSHOT"

plugins {
    idea
    base
    java
    id("org.springframework.boot") version "3.1.6"
    id("io.spring.dependency-management") version "1.1.4"
}

dependencies {

    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.openjdk.jol:jol-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("com.vaadin.external.google", "android-json")
    }
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }

    test {
        useJUnitPlatform()
    }
}