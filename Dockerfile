# 使用tomcat:10.1.17-jdk21作为基础镜像
FROM tomcat:10.1.17-jdk21

# 设置镜像作者
LABEL authors="Lhy"

# 删除/usr/local/tomcat/webapps/目录下的所有文件
RUN rm -f /usr/local/tomcat/webapps/*

# 将本地的web.xml文件复制到镜像的/usr/local/tomcat/conf/目录下
COPY ./init/web.xml /usr/local/tomcat/conf/web.xml

# 将本地的vevss-0.1.war文件复制到镜像的/usr/local/tomcat/webapps/目录下，并重命名为ROOT.war
COPY ./target/vevss-0.1.war /usr/local/tomcat/webapps/ROOT.war