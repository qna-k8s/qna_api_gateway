# #Stage 1
FROM maven:3.8.1-jdk-11 as builder
ADD . /buildDir
WORKDIR /buildDir
RUN mvn clean package -DskipTests
# Stage 2
FROM openjdk:11.0.2-jdk-oraclelinux7
COPY --from=builder /buildDir/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]