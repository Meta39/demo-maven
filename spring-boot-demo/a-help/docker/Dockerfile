#java需要jre才能运行，因此需要jre镜像
FROM openjdk:8-jre-alpine
#作者
MAINTAINER Meta39
#时区
ENV TZ=Asia/Shanghai
#JVM参数
ENV JAVA_OPTS="-Xms2048m -Xmx2048m"
#指定临时文件目录为tmp。在主机/var/lib/docker目录下创建了一个临时文件并链接到容器的/tmp
VOLUME /tmp
#将jar包添加到容器中并更改为相应的名称，确保Dockerfile和jar包在同一个文件夹内
ADD demo.jar demo.jar
#运行容器内的jar包
ENTRYPOINT ["nohup","java","-jar","/demo.jar","&"]
#暴露端口第一个端口是宿主机端口，第二个是容器内部端口
EXPOSE 81 81