LABEL authors="Katleho Mpheqeke"

#Start with base image containing Java runtime
FROM quay.io/sdase/openjdk-runtime:17-hotspot

# Make port 8080 available outside container
EXPOSE 8080

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]