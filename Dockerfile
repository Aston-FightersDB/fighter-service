# Используем официальный образ OpenJDK 17 на основе slim-дистрибутива
FROM openjdk:17-jdk-slim

# Аргумент для передачи имени jar-файла
ARG JAR_FILE=target/fighter-microservice.jar

# Копируем jar-файл в контейнер под именем app.jar
COPY ${JAR_FILE} app.jar

# Указываем порт, который будет прослушивать приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app.jar"]