FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD java -Xms256m -Xmx256m -Xss512k -Dserver.port=$PORT $JAVA_OPTS -jar app.jar