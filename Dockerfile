FROM openjdk:17-jdk-slim
VOLUME /tmp

LABEL name="dev-commons"
LABEL version="0.0.1"
LABEL description="Dev Commons Application"

COPY target/dev-commons-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
COPY start_app.sh start_app.sh
RUN chmod +x start_app.sh
#ENTRYPOINT ["./start_app.sh"]
ENTRYPOINT ["java","-jar","/app.jar"]