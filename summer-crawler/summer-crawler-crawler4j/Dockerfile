#FROM mvn3.5 as builder
#WORKDIR /build
#COPY pom.xml .
#RUN mvn dependency:go-offline
#
#COPY src/ /build/src/
#RUN mvn package


### 基础镜像，使用alpine操作系统，openjkd使用8u201
FROM openjdk:8u201-jdk-alpine3.9
#作者
MAINTAINER liuwei <liuweizhenhaoa@163.com>

#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

#暴露8080端口
EXPOSE 8080

#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/*.jar app.jar

#启动容器时的进程
ENTRYPOINT ["java","-jar","/app.jar"]
#
#CMD exec java -Dloader.path="/home/libs/" -jar /home/app.jar
#COPY --from=builder /build/target/*.jar /home/app.jar
#COPY --from=builder /build/target/libs /home/libs/




