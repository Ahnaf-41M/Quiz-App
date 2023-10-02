FROM openjdk:8               
VOLUME /tmp                   
EXPOSE 8080                   
ADD target/SpringBootDockerTest-0.0.1-SNAPSHOT.jar SpringBootDockerTest-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","/SpringBootDockerTest-0.0.1-SNAPSHOT.jar"]             