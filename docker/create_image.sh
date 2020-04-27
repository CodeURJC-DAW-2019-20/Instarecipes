#Angular compile project
cd ..

cd ./frontend/instarecipes

docker run -it --rm --name insta-angular -v "$PWD":/usr/src/app -w /usr/src/app node:12.16.1 /bin/bash -c "cd /usr/src/app && npm install > /dev/null && npm i -g @angular/cli > /dev/null && ng build --baseHref=/new/"

mv -v "$PWD"/dist/instarecipes ../../backend/src/main/resources/static/new

cd ../../backend

docker run -it --rm --name insta-ng -v "$PWD":/usr/src/app -w /usr/src/app maven:3.6.3-openjdk-15 mvn clean install

cp target/instarecipes-0.0.1-SNAPSHOT.jar ../docker/app/

cd ../docker

docker build . -t lordkener/instarecipes-app

docker login

docker push lordkener/instarecipes-app