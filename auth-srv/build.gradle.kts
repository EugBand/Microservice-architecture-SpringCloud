plugins {
    val springBootVersion = "2.2.6.RELEASE"
    java
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
}

    group = "com.epam.epmcacm.msademo"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
    }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("io.micrometer:micrometer-registry-prometheus:1.3.6")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.4.0.RELEASE")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:2.2.10.RELEASE")
    implementation("org.springframework.security:spring-security-jwt:1.1.0.RELEASE")
    implementation("com.nimbusds:nimbus-jose-jwt:8.11")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.2")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.2")
}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
