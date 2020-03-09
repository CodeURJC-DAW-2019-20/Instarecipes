FROM openjdk:8-alpine
COPY ./code /code
COPY Images /Images
WORKDIR /DAW

RUN apt-get update

RUN pip install -r requierements.txt 

CMD ["java", "Application.java"]