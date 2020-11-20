import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.0"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	id("com.gorylenko.gradle-git-properties") version "2.2.3"
	id("com.google.cloud.tools.jib") version "2.6.0"
	kotlin("jvm") version "1.4.10"
	kotlin("plugin.spring") version "1.4.10"
	kotlin("plugin.jpa") version "1.4.10"
}

group = "de.digitalfrontiers.axon"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.axonframework:axon-spring-boot-starter:4.4.5")

	runtimeOnly("org.postgresql:postgresql:42.2.16")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.15.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

springBoot {
	buildInfo()
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName = "dxfrontiers/axon-infrastructure-tracking"
    targetJavaVersion.set(JavaVersion.VERSION_15)
}
