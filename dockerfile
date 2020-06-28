FROM java:8
EXPOSE 2405
COPY generalproject.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8","-jar","/app.jar"]

# docker build -t registry.cn-hongkong.aliyuncs.com/souther/generalproject:1.1-master .