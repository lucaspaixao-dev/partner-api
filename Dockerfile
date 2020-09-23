#FROM gradle:5.6.2-jdk8  AS BUILD_IMAGE
#COPY . /home/source/java
#WORKDIR /home/source/java
#USER root
#RUN chown -R gradle /home/source/java
#USER gradle
#RUN gradle clean build
#
#FROM openjdk:8-jre-alpine
#ENV APPLICATION_USER ktor
#RUN adduser -D -g '' $APPLICATION_USER
#
#RUN mkdir /app
#RUN chown -R $APPLICATION_USER /app
#
#USER $APPLICATION_USER
#
#COPY --from=BUILD_IMAGE /home/source/java/build/libs/simian-application.jar /app/simian-application.jar
#WORKDIR /app

ENV MONGO_HOST=mongodb
ENV MONGO_AUTH_USER=partner
ENV MONGO_DATABASE=partner_service
ENV MONGO_AUTH_PASSWORD=1234

#CMD ["java", "-server", "-jar", "simian-application.jar"]