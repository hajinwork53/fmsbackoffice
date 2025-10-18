package com.fms.backoffice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "사용자명은 필수입니다")
    @Size(min = 4, max = 50, message = "사용자명은 4-50자 사이여야 합니다")
    private String username;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", 
             message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다")
    private String password;
    
    @NotBlank(message = "상호명은 필수입니다")
    private String companyName;
    
    @NotBlank(message = "사업자 등록번호는 필수입니다")
    @Pattern(regexp = "^\\d{10}$|^\\d{3}-\\d{2}-\\d{5}$", 
             message = "올바른 사업자 등록번호 형식이 아닙니다")
    private String businessNumber;
    
    private List<VehicleInfo> vehicles;
    
    @NotBlank(message = "차량 등록번호는 필수입니다")
    private String vehicleRegistrationNumber;
    
    @NotBlank(message = "이름은 필수입니다")
    private String name;
    
    private String department;
    
    @NotBlank(message = "전화번호는 필수입니다")
    @Pattern(regexp = "^01[0-9]-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다")
    private String phone;
    
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    
    private Boolean adConsent = false;
    
    @Data
    public static class VehicleInfo {
        private String vehicleType;
        private String vin;
    }
}
