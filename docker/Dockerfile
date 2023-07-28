FROM eclipse-temurin:17.0.8_7-jre
RUN mkdir -p /opt/jsr
COPY reactivestack.jar /opt/jsr/reactivestack.jar
WORKDIR /opt/jsr
EXPOSE 8080
ENTRYPOINT java -jar reactivestack.jar
