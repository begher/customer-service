FROM eclipse-temurin:21-ubi9-minimal
VOLUME /tmp
ARG JAR_FILE=build/libs/customer-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY entrypoint.sh /entrypoint.sh
COPY src/main/resources/key.json /app/imports/key.json
#COPY src/main/resources/keystore.p12 /app/keystore.p12
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]