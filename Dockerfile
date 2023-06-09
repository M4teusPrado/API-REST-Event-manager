FROM openjdk:11-jre
WORKDIR /eventoapp
COPY target/*.jar /eventoapp/eventoapp-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD java -XX:+UseContainerSupport -Xmx512m -jar eventoapp-0.0.1-SNAPSHOT.jar


