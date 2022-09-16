FROM 1352255400/openjdk8
ENV TimeZone=Asia/Shanghai
ENV LANG C.UTF-8
#设置时区
RUN ln -snf /usr/share/zoneinfo/$TimeZone /etc/localtime && echo $TimeZone > /etc/timezone
# 指定启动端口    ENV PARAMS="--server.port=8080 --spring.profiles.active=prod --spring.cloud.nacos.discovery.server-addr=his-nacos.his:8848 --spring.cloud.nacos.config.server-addr=his-nacos.his:8848 --spring.cloud.nacos.config.namespace=prod --spring.cloud.nacos.config.file-extension=yml"
ENV PARAMS="--server.port=8080"

VOLUME /tmp
COPY ./target/*.jar /app.jar
#ENTRYPOINT ["java","-jar","-Xms1024m", "-Xmx1024m","/app.jar", "&"]
EXPOSE 8080
ENTRYPOINT ["/bin/sh","-c","java -Dfile.encoding=utf8 -Djava.security.egd=file:/dev/./urandom -jar app.jar ${PARAMS}"]
