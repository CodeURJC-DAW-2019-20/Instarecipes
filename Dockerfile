# our base image
FROM openjdk:8-alpine

# copy files required for the app to run
COPY ./../backend/target/instarecipes-0.0.1-SNAPSHOT.jar ./instarecipes.jar
COPY ./../backend/temp /temp
COPY ./../backend/src/main/resources/static ./src/main/resources/static




COPY ../frontend/instarecipes/dist/instarecipes ./instarecipes

COPY ../frontend/instarecipes/angular.json ./instarecipes/angular.json
RUN npm install
RUN npm install -g @angular/cli@9.1.0

# tell the port number the container should expose
EXPOSE 8443 3306

# run the application
CMD ["java","-jar","instarecipes.jar"]
CMD npm start