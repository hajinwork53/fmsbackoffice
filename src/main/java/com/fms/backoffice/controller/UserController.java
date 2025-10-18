package com.fms.backoffice.controller;

import com.fms.backoffice.dto.response.ApiResponse;
import com.fms.backoffice.dto.response.UserResponse;
import com.fms.backoffice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getUsers(
            @RequestParam(required = false) String status,
            Pageable pageable) {
        Page<UserResponse> users = userService.getUsers(status, pageable);
        return ResponseEntity.ok(ApiResponse.success(users));
    }
    
    @PutMapping("/{userId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveUser(@PathVariable Long userId) {
        userService.approveUser(userId);
        return ResponseEntity.ok(ApiResponse.success(null, "회원이 승인되었습니다"));
    }
    
    @PutMapping("/{userId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectUser(
            @PathVariable Long userId,
            @RequestBody RejectRequest request) {
        userService.rejectUser(userId, request.getReason());
        return ResponseEntity.ok(ApiResponse.success(null, "회원이 반려되었습니다"));
    }
    
    @lombok.Data
    static class RejectRequest {
        private String reason;
    }
}
