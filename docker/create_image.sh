if($args[0] -eq "frontend"){
	echo "Frontend"
	cd Angular
	docker run --rm -it -v "$(pwd):/usr/src/app" -w "/usr/src/app"  node:13.3.0-alpine npm run build
	cd ..

	Copy-Item -Path ./Angular/dist/angular -Destination ./Backend/src/main/resources/static/new -Recurse

}