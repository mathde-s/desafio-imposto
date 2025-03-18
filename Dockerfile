FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copia o arquivo pom.xml e as dependências para o cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte da aplicação
COPY src ./src

# Compila a aplicação
RUN mvn clean package -DskipTests

# Etapa 2: Execução
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/*.jar app.jar

# Define o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]