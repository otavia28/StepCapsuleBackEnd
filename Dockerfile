FROM maven:3.5-jdk-8-alpine as builder
# 设置容器内的工作目录为 /app。这是后续命令（如 COPY 和 RUN）的基准目录。
WORKDIR /app
# 将宿主机上的 pom.xml 文件和 src 目录复制到容器内的 /app 目录。
COPY pom.xml .
COPY src ./src
# 执行 Maven 的 package 命令，用于编译和打包应用程序，同时通过 -DskipTests 参数跳过测试。这会生成一个可执行的 JAR 文件。
RUN mvn package -DskipTests
# 设置时区为 Asia/Shanghai
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 容器启动时默认执行的命令
CMD ["java","-jar","/app/target/StepCapsuleBackEnd-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]

