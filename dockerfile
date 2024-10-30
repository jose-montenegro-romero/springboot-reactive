# Usa Temurin JRE 20 en Alpine como imagen base para reducir tamaño
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

# Expone el puerto especificado por la variable de entorno
EXPOSE $PORT

# Cambia al usuario no root
USER spring:spring

# Ejecuta la aplicación Java
CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
