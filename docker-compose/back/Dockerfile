# Gradle을 사용하여 소스 코드 빌드 ---
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

# Gradle 캐시 최적화를 위한 의존성 먼저 다운로드
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew .
RUN gradle dependencies --no-daemon

# 소스 복사 및 빌드
COPY src ./src
RUN gradle bootJar --no-daemon -x test

# 실행 이미지
FROM eclipse-temurin:17-jre
WORKDIR /app

# JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar back.jar

EXPOSE 8080

# 컨테이너가 시작될 때 실행할 명령어
ENTRYPOINT ["java", "-jar", "back.jar"]