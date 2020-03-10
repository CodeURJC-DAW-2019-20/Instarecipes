FROM openjdk:8-alpine
COPY ./instarecipes.jar /instarecipes.jar
COPY temp /temp

ENTRYPOINT ["java", "-jar", "instarecipes.jar"]