
plugins {
    java
    id("org.springframework.boot") version "3.1.6"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("plugin.spring") version "1.9.21"
}

java.sourceCompatibility = JavaVersion.VERSION_17
val springBootVersion : String by extra("3.1.6")

allprojects {
    group = "com.hhlee"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

//    apply(plugin = "kotlin")
//    apply(plugin = "kotlin-spring") //all-open
//    apply(plugin = "kotlin-jpa")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
        implementation("org.springframework.boot:spring-boot-starter-tomcat:$springBootVersion")
        implementation("org.springframework.boot:spring-boot-starter-mail:$springBootVersion")
        implementation("org.springframework.boot:spring-boot-starter-websocket:$springBootVersion")
        implementation("org.springframework.boot:spring-boot-starter-jdbc:$springBootVersion")
        implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
        implementation("org.springframework.boot:spring-boot-starter-aop:$springBootVersion")
        implementation("org.springframework.boot:spring-boot-starter-cache:$springBootVersion")
        implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")

//        implementation("org.apache.tomcat.embed:tomcat-embed-jasper:10.1.16")
//        implementation("javax.servlet:jstl:1.2")
//        implementation("org.apache.tiles:tiles-jsp:3.0.8")

        implementation("com.google.code.gson:gson:2.8.9")

        implementation(platform("software.amazon.awssdk:bom:2.21.1"))
        implementation("software.amazon.awssdk:aws-core")
        implementation("software.amazon.awssdk:auth")
        implementation("software.amazon.awssdk:lambda")
        implementation("software.amazon.awssdk:ecs")
        implementation("software.amazon.awssdk:s3")

        implementation("com.github.docker-java:docker-java:3.2.13")
        implementation("org.apache.xmlgraphics:batik-all:1.16")

        implementation("org.projectlombok:lombok:1.18.20")

        implementation("commons-beanutils:commons-beanutils:1.9.4")
        implementation("org.apache.commons:commons-csv:1.9.0")
        implementation("org.apache.commons:commons-lang3:3.12.0")
        implementation("org.apache.httpcomponents:httpcore:4.4.15")
        implementation("org.apache.commons:commons-compress:1.21")
        implementation("org.apache.commons:commons-text:1.10.0")
        implementation("commons-io:commons-io:2.8.0")
        //
        implementation("io.jsonwebtoken:jjwt-api:0.11.5")
        implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
        implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
        //pdf
        implementation("org.apache.pdfbox:pdfbox:2.0.29")
        //
        implementation("com.github.albfernandez:juniversalchardet:2.4.0")

        implementation("org.jcodec:jcodec:0.2.3")
        implementation("org.jcodec:jcodec-javase:0.2.3")

        implementation("com.jayway.jsonpath:json-path:2.8.0"){
            exclude(group = "org.slf4j", module = "slf4j-api")
            exclude(group ="net.minidev", module ="json-smart")
        }
        implementation("net.minidev:json-smart:2.4.11")

        implementation("com.googlecode.json-simple:json-simple:1.1.1") {
            exclude(group = "junit", module = "junit")
        }

        implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
        implementation("com.fasterxml.jackson.core:jackson-core:2.16.0")
        implementation("com.fasterxml.jackson.core:jackson-annotations:2.16.0")
//        implementation("org.json:json:20230227")
        implementation("org.apache.poi:poi-ooxml:5.2.0") {
            exclude(group = "xml-apis", module = "xml-apis")
        }
        implementation("com.zaxxer:HikariCP:5.0.1")

        implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
        implementation("org.mybatis:mybatis-spring:3.0.3")
        implementation("org.postgresql:postgresql:42.2.27")
        implementation("org.mybatis:mybatis:3.5.14")
        implementation("org.freemarker:freemarker:2.3.32")
        implementation("ch.qos.logback:logback-core:1.4.14")

        testImplementation("org.junit.platform:junit-platform-launcher:1.9.0")
        testImplementation("org.openjdk.jol:jol-core:0.16")
        testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion") {
            exclude("com.vaadin.external.google", "android-json")
            exclude(group ="net.minidev", module ="json-smart")
        }
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        compileOnly("org.projectlombok:lombok:1.18.20")
        annotationProcessor("org.projectlombok:lombok:1.18.20")
    }

    configurations {
        all {
            exclude(group="org.slf4j", module="slf4j-nop")
        }
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
    }

    tasks.test {
        useJUnitPlatform()
    }
}
// sub project간 의존성이 있는 경우 사용
////api <- domain 의존
//project(":api") {
//    dependencies {
//        implementation(project(":domain"))
//    }
//}
//
////domain 설정
//project(":domain") {
//    val jar: Jar by tasks
//    val bootJar: BootJar by tasks
//
//    bootJar.enabled = false
//    jar.enabled = true
//}
