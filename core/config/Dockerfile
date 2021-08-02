FROM openjdk:18-slim
ADD target/config-0.0.1-SNAPSHOT.jar config-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /config-0.0.1-SNAPSHOT.jar" ]
