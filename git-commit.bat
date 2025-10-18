@echo off
echo ========================================
echo FMS Backoffice Backend - GitHub Commit
echo ========================================
echo.

cd "D:\_cluade_sendbox\FMS Backoffice\fms-backend"

echo Git 초기화 중...
git init

echo 원격 저장소 연결 중...
git remote add origin https://github.com/hajinwork53/fmsbackoffice.git

echo 모든 파일 추가 중...
git add .

echo Commit 생성 중...
git commit -m "Complete Backend Implementation

- Spring Boot 3.2.0 with JWT Authentication
- User Management (Registration, Approval)
- Driving Log API (Query, Statistics)
- Expense Management API (CRUD, Statistics)
- H2 Database with Flyway Migration
- Security Configuration
- All Domain Models and Repositories
- RESTful API Controllers
- Exception Handling
- Total 35+ Java files"

echo GitHub에 Push 중...
git branch -M main
git push -u origin main --force

echo.
echo ========================================
echo GitHub 업로드 완료!
echo URL: https://github.com/hajinwork53/fmsbackoffice
echo ========================================
pause
