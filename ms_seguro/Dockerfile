# Usar una imagen base de Java 17 compacta
FROM eclipse-temurin:17-jdk-alpine

# Crear un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado por tu Maven local
COPY target/*.jar app.jar

# Exponer el puerto oficial asignado a Seguros
EXPOSE 8088

# Comando para ejecutar el microservicio al iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]