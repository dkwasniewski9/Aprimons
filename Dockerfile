FROM openjdk:17
RUN mkdir /app \

RUN ./gradlew build

COPY . /app

WORKDIR /app
CMD ["java", "-jar", "./build/libs/Aprimons.jar"]