# Usar la imagen base oficial de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Crear el directorio de trabajo interno en el contenedor
WORKDIR /app

# Copiar el archivo empaquetado .jar generado por Maven
COPY target/*.jar app.jar

# Abrir el puerto oficial de notificaciones
EXPOSE 8089

# Comando de ejecución inicial
ENTRYPOINT ["java", "-jar", "app.jar"]