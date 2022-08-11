FROM openjdk:17-alpine
COPY target/cms-member-service.jar cms-member-service.jar
ENTRYPOINT ["java","-jar","/cms-member-service.jar"]