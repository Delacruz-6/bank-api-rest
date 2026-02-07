# Etapa 1: Construcción
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=builder /app/target/bank-api-rest-*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=dev
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
