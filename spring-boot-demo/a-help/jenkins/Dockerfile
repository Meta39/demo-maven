#jdk版本
FROM openjdk:8-jre-alpine
#时区
ENV TZ=Asia/Shanghai
#JVM参数
ENV JAVA_OPTS="-Xms256m -Xmx256m"
#映射端口
EXPOSE 9090 9090
#指定工作文件夹，因为我们是用jenkins推送到部署服务器的/jenkins文件夹下的，然后推送项目的是又remote了一个gitlabdemo文件夹
#路径就由/jenkis+/gitlabdemo，因此工作文件夹就是一个组合文件夹。执行Dockerfile也要进入到这个文件夹执行，否则会报错
WORKDIR /jenkins/gitlabdemo
#把jar包放入镜像内
ADD gitlabdemo*.jar /gitlabdemo.jar
#启动参数
ENTRYPOINT ["nohup","java","-jar","/gitlabdemo.jar","&"]