FROM openjdk:8-jre-alpine
#VOLUME /tmp
COPY ./target/*.jar /tmp/
EXPOSE 8761
#ENTRYPOINT ["java","-jar","/tmp/shri-app-registry*.jar"]
CMD /usr/bin/java -jar /tmp/*.jar
