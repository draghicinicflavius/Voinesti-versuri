# Folosim o imagine de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Copiem fișierul jar generat în container
COPY target/*.jar app.jar

# Comanda de pornire
ENTRYPOINT ["java","-jar","/app.jar"]