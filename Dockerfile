FROM openjdk:11.0.7
ARG JAR_FILE=target/ms-saving-account-*.jar

ENV JAVA_OPTS="-Xms64m -Xmx256m"

COPY ${JAR_FILE} ms-saving-account.jar

ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -server -jar ms-saving-account.jar