# Usa la imagen de Java JDK para compilar y ejecutar Spring Boot
FROM openjdk:17-jdk-slim

# Define la carpeta de trabajo para todos los comandos siguientes
WORKDIR /exercise-api

# Copia el archivo JAR generado por Maven o Gradle
COPY target/exercise-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080
EXPOSE 8080

# Ejecuta la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
