FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar

ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","$JAR_FILE"]