# Usar la imagen base oficial de Java 17 compacta
FROM eclipse-temurin:17-jdk-alpine

# Crear el directorio de trabajo interno en el contenedor
WORKDIR /app

# Copiar el archivo empaquetado .jar generado por tu Maven
COPY target/*.jar app.jar

# Abrir el puerto analítico oficial de la centralizador de métricas
EXPOSE 8090

# Comando para arrancar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]