# 使用具有Maven的官方OpenJDK镜像作为基础镜像
FROM maven:latest AS build

# 设置工作目录
WORKDIR /app

# 将项目的POM文件和源代码复制到容器中
COPY pom.xml .
RUN mvn dependency:resolve

COPY src ./src

# 使用Maven打包应用程序
RUN mvn package -DskipTests=true

# 使用官方的OpenJDK 21作为运行镜像的基础
FROM openjdk:21-jdk

# 设置工作目录
WORKDIR /app

# 从构建阶段复制打包好的JAR文件到运行镜像中
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# 设置环境变量，这些是您的应用程序的配置
#ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/postgres
#ENV SPRING_DATASOURCE_USERNAME=postgres
#ENV SPRING_DATASOURCE_PASSWORD=1
#ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
#ENV SERVER_PORT=4000

# 运行Spring Boot应用程序
CMD ["java", "-jar", "app.jar"]