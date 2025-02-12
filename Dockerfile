# 빌드 단계
FROM bellsoft/liberica-openjdk-alpine:21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# 실행 단계
FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

#docker build -t devossian-hompage:latest .
#docker run --name devossian-hompage -p 8080:8080 devossian-hompage:latest