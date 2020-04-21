if($args[0] == "frontend")
then
    echo "Frontend"
    cd Angular
    docker run --rm -it -v "$(pwd):/usr/src/app" -w "/usr/src/app"  node:12.16.2-alpine npm run build
    cd ..
    if [[$PATH== "./Angular/dist/angular" ]]
    then
        while($PATH== "./Backend/src/main/resources/static/new") do
            if [[$PATH== "./Backend/src/main/resources/static/new" ]]
            then
                cp -R ~/Angular/dist/angular /Backend/src/main/resources/static/new -Recurse
            else
                rm -r -- /Backend/src/main/resources/static/new
                 cp -R  ~/Angular/dist/angular /Backend/src/main/resources/static/new -Recurse
            

           # if (!(Test-Path ./Backend/src/main/resources/static/new -PathType Any)){
            #    echo "Move folder fails"
             #   echo "Retrying..."
         #}
        
        
    else
        echo "Angular compilation fail"
        exit
    



cd Backend
# Clean and packge using local .m2 repository to do not download already gotten libraries
docker run -it --rm -v "$(pwd):/usr/src/project" `
                    -v"$HOME/.m2:/root/.m2" `
                    -w /usr/src/project maven:alpine mvn clean package

# If there is any problem in compilation we move and rename the .jar


if [[$PATH== "./target/padelversus-0.0.1-SNAPSHOT.jar" ]]
then
    cp -R ~/target/padelversus-0.0.1-SNAPSHOT.jar  ../Docker/PadelVersus.jar -force
else
    echo "Maven compilation fail"
    exit

cd ..

# If there is not already moved we copy recursive the folder with images needed for the demo
if (!(Test-Path ./Docker/DemoImages -PathType Any)){
    Copy-Item -Path ./DemoImages -Destination ./Docker/DemoImages -Recurse
}else{
    Remove-Item -Recurse -Force ./Docker/DemoImages
    Copy-Item -Path ./DemoImages -Destination ./Docker/DemoImages -Recurse
}

# We move into the folder were the .Dockerfile and docker-compose.yml is
cd Docker

# We remove the images in case we have done changes in .Dockerfile
docker rmi teampina/padelversus

# We build the backend image
docker image build -t teampina/padelversus -f .Dockerfile .

# We push the images to docker hub
docker push teampina/padelversus
# We reutrned to the started directory
cd ..