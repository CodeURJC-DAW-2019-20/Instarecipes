FROM openjdk:8-alpine
COPY ./instarecipes.jar /instarecipes.jar
COPY temp /temp

CMD ["java", "-jar", "instarecipes.jar"]