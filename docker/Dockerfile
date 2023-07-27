FROM openjdk:17-jdk-slim
RUN mkdir -p /opt/jsr
COPY reactivestack.jar /opt/jsr/reactivestack.jar
WORKDIR /opt/jsr
EXPOSE 8080
ENTRYPOINT java -jar reactivestack.jar
