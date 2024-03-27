import com.google.protobuf.gradle.id

plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"

	id("com.google.protobuf") version "0.9.4"
}

group = "managment.system"
version = "0.0.1-SNAPSHOT"

object Versions {
	const val mapstruct = "1.5.5.Final"
	const val lombokMapstructBinding = "0.2.0"

	const val grpc = "1.62.2"
	const val protobuf = "3.25.1"
	const val reactorGrpc ="1.2.4"
	const val grpcSpringBootStarter = "2.15.0.RELEASE"
	const val tomcatAnnotations = "6.0.53"

	const val lang3 = "3.14.0"
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:${Versions.protobuf}"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:${Versions.grpc}"
		}
		id("reactor") {
			artifact = "com.salesforce.servicelibs:reactor-grpc:${Versions.reactorGrpc}"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("reactor")
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

dependencies {
	implementation("org.apache.commons:commons-lang3:${Versions.lang3}")

	//MAPSTRUCT DEPENDENCIES
	implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")
	annotationProcessor("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")
	implementation("org.projectlombok:lombok-mapstruct-binding:${Versions.lombokMapstructBinding}")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:${Versions.lombokMapstructBinding}")

	//GRPC/PROTOBUF DEPENDENCIES
	runtimeOnly("io.grpc:grpc-netty-shaded:${Versions.grpc}")
	implementation("io.grpc:grpc-protobuf:${Versions.grpc}")
	implementation("io.grpc:grpc-stub:${Versions.grpc}")
	compileOnly("org.apache.tomcat:annotations-api:${Versions.tomcatAnnotations}")
	implementation("net.devh:grpc-server-spring-boot-starter:${Versions.grpcSpringBootStarter}")
	implementation("com.salesforce.servicelibs:reactor-grpc-stub:${Versions.reactorGrpc}")

	//JUNIT TESTING DEPENDENCIES
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("io.grpc:grpc-testing:${Versions.grpc}")

	//DATABASE DEPENDENCIES
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:r2dbc-postgresql")

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	testImplementation("io.projectreactor:reactor-test")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
