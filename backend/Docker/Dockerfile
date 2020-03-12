# our base image
FROM openjdk:8-alpine

# copy files required for the app to run
COPY ./target/instarecipes-0.0.1-SNAPSHOT.jar ./instarecipes.jar
COPY ./temp /temp
COPY ./src/main/resources/static ./src/main/resources/static

# tell the port number the container should expose
EXPOSE 8443 3306

# run the application
CMD ["java","-jar","instarecipes.jar"]
