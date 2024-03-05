FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} game-auction-server.jar
ENTRYPOINT ["java","-jar","/game-auction-server.jar"]