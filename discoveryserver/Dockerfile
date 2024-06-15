FROM maven:3.8.4-openjdk-17 as builder

#copy local code to the container image
WORKDIR /app
COPY pom.xml .
COPY src ./src

#build the application
RUN mvn package -DskipTests

FROM maven:3.8.4-openjdk-17
COPY --from=builder /app/target/discoveryserver-0.0.1-SNAPSHOT.jar discoveryserver-0.0.1-SNAPSHOT.jar

# Run the web service on container startup
CMD ["java", "-jar", "/discoveryserver-0.0.1-SNAPSHOT.jar"]
