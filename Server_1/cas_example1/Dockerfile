FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
RUN ls
COPY --from=build /app/target/cas_example1.jar .
CMD ["java", "-jar", "cas_example1.jar"]