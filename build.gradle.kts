import com.google.protobuf.gradle.id

plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"

	id("com.google.protobuf") version "0.9.4"
}

group = "managment.system"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.25.1"
	}
	plugins {
		create("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.62.2"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc") {}
			}
		}
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val mapstructVersion = "1.5.5.Final"
val lombokMapstructBindingVersion = "0.2.0"

dependencies {
	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
	implementation("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

	runtimeOnly("io.grpc:grpc-netty-shaded:1.62.2")
	implementation("io.grpc:grpc-protobuf:1.62.2")
	implementation("io.grpc:grpc-stub:1.62.2")
	compileOnly("org.apache.tomcat:annotations-api:6.0.53")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.flywaydb:flyway-core")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
