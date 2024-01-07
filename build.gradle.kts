plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.lerw"
version = "1.0"

repositories {
    mavenCentral()
}

val ktorVersion = "2.3.7"
val logbackVersion = "1.4.12"
dependencies {
    // logging
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    // ktor
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    // ktor server
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    // ktor client
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}