FROM openjdk:18-slim
ADD target/catalog-0.0.1-SNAPSHOT.jar catalog-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /catalog-0.0.1-SNAPSHOT.jar" ]
