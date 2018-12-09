FROM jdk11
VOLUME /tmp
ADD ./target/metro-transit-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "app.jar"]

