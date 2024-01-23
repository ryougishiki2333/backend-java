# 使用官方的OpenJDK 21作为基础镜像
FROM openjdk:21-jdk

# 设置工作目录
WORKDIR /app

# 复制Spring Boot应用程序JAR文件到容器中
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# 设置环境变量，这些是您的应用程序的配置
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/postgres
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=1
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SERVER_PORT=4000

# 运行Spring Boot应用程序
CMD ["java", "-jar", "app.jar"]