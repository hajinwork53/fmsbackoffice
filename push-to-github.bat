@echo off
echo ========================================
echo FMS Backoffice Backend - GitHub Push
echo ========================================
echo.

cd /d "D:\_cluade_sendbox\FMS Backoffice\fms-backend"

echo [1/5] Git 초기화...
git init

echo [2/5] 원격 저장소 추가...
git remote remove origin 2>nul
git remote add origin https://github.com/hajinwork53/fmsbackoffice.git

echo [3/5] 모든 파일 추가...
git add .

echo [4/5] 커밋...
git commit -m "Complete Backend Implementation

- Spring Boot 3.2.0 + Java 17
- JWT Authentication & Authorization
- H2 Database with Flyway Migration
- User Management (Admin/User roles)
- Driving Log API with statistics
- Expense Management API
- Complete CRUD operations
- RESTful API design
- Spring Security configuration
- Exception handling
- Data validation

Features:
- Login/Register (Admin & User)
- User approval workflow
- Driving log search & filter
- Expense tracking by type
- Role-based access control
"

echo [5/5] GitHub에 Push...
git branch -M main
git push -u origin main --force

echo.
echo ========================================
echo 완료! 
echo GitHub: https://github.com/hajinwork53/fmsbackoffice
echo ========================================
pause
