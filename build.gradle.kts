plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}
sourceSets.test {
    java.srcDirs("src/test/kotlin")
}

dependencies {
    implementation(libs.kotlin.coroutines)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}
