# Usar una imagen base oficial de Java 17 compacta (Alpine Linux)
FROM eclipse-temurin:17-jdk-alpine

# Crear un directorio de trabajo dentro del contenedor donde se guardará la app
WORKDIR /app

# Copiar el archivo JAR compilado por Maven desde tu PC hacia el contenedor
COPY target/*.jar app.jar

# Exponer el puerto en el que corre ms_factura para que Docker lo abra
EXPOSE 8087

# Comando predeterminado que arranca el microservicio al encender el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]