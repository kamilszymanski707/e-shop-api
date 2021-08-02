FROM openjdk:18-slim
ADD target/basket-0.0.1-SNAPSHOT.jar basket-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /basket-0.0.1-SNAPSHOT.jar" ]
