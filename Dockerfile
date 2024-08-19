FROM azul/zulu-openjdk-alpine:21-jre-latest
MAINTAINER Luis Tobon <lumeche@gmail.com>

ENTRYPOINT ["java", "-jar", "/usr/share/demo-docker-maven/demo.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/demo-docker-maven/demo.jar