FROM openjdk:11-jdk

ADD ./build/libs/*.jar rqs.jar

CMD ["java", "-jar", "-Duser.timezone=Asia/Seoul", "rqs.jar"]