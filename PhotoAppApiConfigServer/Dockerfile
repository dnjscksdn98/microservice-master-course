FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY UnlimitedJCEPolicyJDK8/* /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
COPY target/PhotoAppApiConfigServer-0.0.1-SNAPSHOT.jar ConfigServer.jar
ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]