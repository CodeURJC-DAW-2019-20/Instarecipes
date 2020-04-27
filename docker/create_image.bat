#Angular compile project
cd ..

cd ./frontend/instarecipes

docker run --rm --name insta-angular -v "$PWD":/angular -w /angular node:13.3.0 /bin/bash -c "npm install; npm run-script build"

xcopy -R "$PWD"\dist\instarecipes ..\..\backend\src\main\resources\static\new

cd ../../backend

docker run --rm -v "$PWD":/usr/src/project -w /usr/src/project maven:alpine mvn package

cd ../docker

docker build . -t lordkener/instarecipes-app

# docker login

# docker push lordkener/instarecipes-app