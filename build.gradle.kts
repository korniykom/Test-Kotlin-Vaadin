plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
	alias(libs.plugins.vaadin)
	alias(libs.plugins.kotlin.jpa)
}

group = "com.korniykom"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.karibu.dsl)
	implementation(libs.spring.boot.starter.data.jpa)
	runtimeOnly(libs.potgresql)
	developmentOnly(libs.vaadin.dev)
	implementation(libs.vaadin.springboot.starter)
	implementation(libs.kotlin.refrect)
}

dependencyManagement {
	imports {
		mavenBom(libs.vaadin.bom.get().toString())
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
