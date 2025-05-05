# FROM maven:latest AS build
# WORKDIR /app
# COPY . .
# RUN mvn clean package -DskipTests

# FROM openjdk:21-jdk-slim
# WORKDIR /app
# COPY --from=build /app/target/Groceries-0.0.1-SNAPSHOT.jar Groceries.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","Groceries.jar"]

# Use official Tomcat image
FROM tomcat:9.0

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR into webapps
COPY C:/Users/Andrew/AppData/Local/Jenkins/.jenkins/workspace/CI_CDPipeline/target/Groceries-0.0.1-SNAPSHOT.jar.original /usr/local/tomcat/webapps

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
