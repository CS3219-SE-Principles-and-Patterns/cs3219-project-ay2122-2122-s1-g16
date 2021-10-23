FROM openjdk:11-jdk
VOLUME /tmp
ADD target/peerprep-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=live" ,"/app.jar"]