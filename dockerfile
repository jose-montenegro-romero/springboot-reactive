# Usa una imagen base con Gradle y Java
FROM gradle:8.10-jdk21-alpine AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo de configuración de Gradle y el script de construcción
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Copia el código fuente de la aplicación
COPY src src

# Ejecuta el comando de construcción
RUN ./gradlew clean build -x test

# Usa Temurin JRE 21
FROM eclipse-temurin:21-jre-alpine

# Establece una variable de entorno para el puerto
ENV PORT=8080
# ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:G1HeapRegionSize=16M -XX:ActiveProcessorCount=2 -XX:+UseContainerSupport -XX:MaxRAMPercentage=85.0 -Xlog:gc*:file=/app/logs/gc.log:time,uptime,level,tags"
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=85.0"

# Crea un usuario y grupo no root para mayor seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Crea un directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de la aplicación
COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app/app.jar
# Copia el archivo JAR generado desde la etapa de construcción
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expone el puerto especificado por la variable de entorno
EXPOSE $PORT

# Cambia al usuario no root
USER spring:spring

# Ejecuta la aplicación Java
CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
