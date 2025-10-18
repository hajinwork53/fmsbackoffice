package com.fms.backoffice.service;

import com.fms.backoffice.domain.User;
import com.fms.backoffice.dto.request.LoginRequest;
import com.fms.backoffice.dto.request.RegisterAdminRequest;
import com.fms.backoffice.dto.request.RegisterUserRequest;
import com.fms.backoffice.dto.response.LoginResponse;
import com.fms.backoffice.exception.CustomException;
import com.fms.backoffice.repository.UserRepository;
import com.fms.backoffice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    
    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException("AUTH-001", "로그인 정보가 올바르지 않습니다"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("AUTH-001", "로그인 정보가 올바르지 않습니다");
        }
        
        if (user.getApprovalStatus() != User.ApprovalStatus.APPROVED) {
            throw new CustomException("AUTH-002", "승인 대기중인 계정입니다");
        }
        
        String accessToken = tokenProvider.createToken(
                user.getUsername(), 
                "ROLE_" + user.getUserType().name());
        String refreshToken = tokenProvider.createRefreshToken(user.getUsername());
        
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userType(user.getUserType().name())
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }
    
    @Transactional
    public Long registerAdmin(RegisterAdminRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException("VALID-001", "이미 존재하는 사용자명입니다");
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(User.UserType.ADMIN)
                .name(request.getName())
                .department(request.getDepartment())
                .phone(request.getPhone())
                .email(request.getEmail())
                .approvalStatus(User.ApprovalStatus.APPROVED)
                .approvedAt(LocalDateTime.now())
                .build();
        
        User savedUser = userRepository.save(user);
        return savedUser.getUserId();
    }
    
    @Transactional
    public Long registerUser(RegisterUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException("VALID-001", "이미 존재하는 사용자명입니다");
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(User.UserType.USER)
                .name(request.getName())
                .department(request.getDepartment())
                .phone(request.getPhone())
                .email(request.getEmail())
                .companyName(request.getCompanyName())
                .businessNumber(request.getBusinessNumber())
                .vehicleRegistrationNumber(request.getVehicleRegistrationNumber())
                .adConsent(request.getAdConsent())
                .approvalStatus(User.ApprovalStatus.PENDING)
                .build();
        
        User savedUser = userRepository.save(user);
        return savedUser.getUserId();
    }
}
