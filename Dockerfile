FROM openjdk:11-jre-slim

RUN apt-get update && apt-get install -y wget

VOLUME /tmp

EXPOSE 8080

WORKDIR /app

RUN wget -q -O city.json.gz http://bulk.openweathermap.org/sample/city.list.json.gz \
    && gzip -d city.json.gz

# Add the application's jar to the container
COPY /build/libs/weather-meather-server-*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]