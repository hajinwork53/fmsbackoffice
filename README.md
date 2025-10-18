# FMS Backoffice Backend

Fleet Management System (FMS) 차량 운행 관리 및 차계부 관리를 위한 백엔드 시스템

## 기술 스택

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: H2 Database (개발), PostgreSQL (프로덕션)
- **Security**: Spring Security + JWT
- **ORM**: Spring Data JPA + Hibernate
- **Build Tool**: Gradle 8.x

## 실행 방법

### 사전 요구사항
- JDK 17 이상
- Gradle 8.x

### 실행

```bash
# Gradle Wrapper 생성
gradle wrapper

# 애플리케이션 실행
./gradlew bootRun

# 또는
./gradlew build
java -jar build/libs/fms-backoffice-1.0.0.jar
```

## 접속 정보

- **Application**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:fmsdb`
  - Username: `sa`
  - Password: (비워두기)
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html

## 기본 계정

- **아이디**: admin
- **비밀번호**: password

## API 문서

Swagger UI를 통해 API 문서를 확인할 수 있습니다.
