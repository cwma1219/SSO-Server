FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
RUN yum update -y && yum install -y sudo iputils

FROM openjdk:17
WORKDIR /app

COPY --from=build /app/target/cas.jar .
CMD ["java", "-jar", "cas.jar"]