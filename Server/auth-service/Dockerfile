FROM openjdk:21 as buildSatge
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -Dmaven.test.skip=true
RUN mv target/*.jar service.jar

FROM openjdk:21
COPY --from=buildSatge /app/service.jar .
ENTRYPOINT ["java","-jar","/service.jar"]