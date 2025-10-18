package com.fms.backoffice.controller;

import com.fms.backoffice.dto.request.LoginRequest;
import com.fms.backoffice.dto.request.RegisterAdminRequest;
import com.fms.backoffice.dto.request.RegisterUserRequest;
import com.fms.backoffice.dto.response.ApiResponse;
import com.fms.backoffice.dto.response.LoginResponse;
import com.fms.backoffice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse<Long>> registerAdmin(
            @Valid @RequestBody RegisterAdminRequest request) {
        Long userId = authService.registerAdmin(request);
        return ResponseEntity.ok(ApiResponse.success(userId, "관리자 회원가입이 완료되었습니다"));
    }
    
    @PostMapping("/register/user")
    public ResponseEntity<ApiResponse<Long>> registerUser(
            @Valid @RequestBody RegisterUserRequest request) {
        Long userId = authService.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success(userId, "회원가입 신청이 완료되었습니다. 승인 후 이용 가능합니다"));
    }
}
