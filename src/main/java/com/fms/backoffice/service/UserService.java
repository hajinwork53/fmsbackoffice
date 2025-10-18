package com.fms.backoffice.service;

import com.fms.backoffice.domain.User;
import com.fms.backoffice.dto.response.UserResponse;
import com.fms.backoffice.exception.CustomException;
import com.fms.backoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsers(String status, Pageable pageable) {
        Page<User> users;
        
        if (status != null && !status.isEmpty()) {
            User.ApprovalStatus approvalStatus = User.ApprovalStatus.valueOf(status);
            users = userRepository.findByApprovalStatus(approvalStatus, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        
        return users.map(this::toUserResponse);
    }
    
    @Transactional
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("DATA-001", "사용자를 찾을 수 없습니다"));
        
        user.setApprovalStatus(User.ApprovalStatus.APPROVED);
        user.setApprovedAt(LocalDateTime.now());
        
        userRepository.save(user);
    }
    
    @Transactional
    public void rejectUser(Long userId, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("DATA-001", "사용자를 찾을 수 없습니다"));
        
        user.setApprovalStatus(User.ApprovalStatus.REJECTED);
        
        userRepository.save(user);
    }
    
    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .name(user.getName())
                .userType(user.getUserType().name())
                .companyName(user.getCompanyName())
                .businessNumber(user.getBusinessNumber())
                .department(user.getDepartment())
                .phone(user.getPhone())
                .email(user.getEmail())
                .approvalStatus(user.getApprovalStatus().name())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
