FROM java:openjdk-8-alpine
VOLUME /tmp
ADD carparking-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.library.path="/library" -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]



