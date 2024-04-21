# Primeira fase: compilação do projeto
FROM maven:3.8.1-openjdk-11-slim AS build
WORKDIR /workspace/app
COPY . /workspace/app
RUN mvn clean package -DskipTests

# Segunda fase: criação da imagem final
FROM openjdk:11-jdk-slim
ARG APP_DIR=/usr/app/
RUN mkdir -p $APP_DIR
WORKDIR $APP_DIR

# Criação de um usuário não root
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copia o arquivo jar da primeira fase
COPY --from=build /workspace/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

