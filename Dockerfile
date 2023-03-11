FROM openjdk:21
ADD /target/social_network-0.0.1-SNAPSHOT.jar backend_v.0.0.1.jar
ENTRYPOINT ["java","-jar", "backend_v.0.0.1.jar"]
