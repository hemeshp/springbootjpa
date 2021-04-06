FROM openjdk:8-jdk-alpine
COPY target/springbootjpa-0.0.1-SNAPSHOT.jar springbootjpa-1.0.0.jar
EXPOSE 9088
ENTRYPOINT ["java","-jar","/springbootjpa-1.0.0.jar"]