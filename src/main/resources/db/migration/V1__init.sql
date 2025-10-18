-- Users Table
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    
    company_name VARCHAR(200),
    business_number VARCHAR(12),
    vehicle_registration_number VARCHAR(20),
    ad_consent BOOLEAN DEFAULT FALSE,
    
    approval_status VARCHAR(20) DEFAULT 'PENDING',
    approved_by BIGINT,
    approved_at TIMESTAMP,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (approved_by) REFERENCES users(user_id)
);

CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_approval_status ON users(approval_status);

-- User Vehicles Table
CREATE TABLE user_vehicles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    vehicle_type VARCHAR(100) NOT NULL,
    vin VARCHAR(50),
    
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE INDEX idx_user_id ON user_vehicles(user_id);

-- Vehicle Info Table
CREATE TABLE vehicle_info (
    vehicle_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vin VARCHAR(50) UNIQUE NOT NULL,
    battery_capacity DECIMAL(10,2),
    vehicle_model VARCHAR(100),
    model_year INT,
    initial_mileage DECIMAL(10,2),
    user_id BIGINT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE INDEX idx_vin ON vehicle_info(vin);

-- Vehicle Driving Data Table
CREATE TABLE vehicle_driving_data (
    data_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id BIGINT NOT NULL,
    vin VARCHAR(50) NOT NULL,
    
    driving_date DATE NOT NULL,
    driving_time TIME NOT NULL,
    soc DECIMAL(5,2),
    soh DECIMAL(5,2),
    power_consumption DECIMAL(10,2),
    driving_time_after_start INT,
    distance_after_start DECIMAL(10,2),
    idle_time INT,
    rapid_accel_decel_count INT,
    driving_score INT,
    overspeed_120_count INT,
    low_pressure_warning BOOLEAN,
    detachment_detected BOOLEAN,
    fault_diagnosis VARCHAR(500),
    weather VARCHAR(50),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (vehicle_id) REFERENCES vehicle_info(vehicle_id) ON DELETE CASCADE
);

CREATE INDEX idx_driving_date ON vehicle_driving_data(driving_date);
CREATE INDEX idx_vin_date ON vehicle_driving_data(vin, driving_date);

-- Driving Logs Table
CREATE TABLE driving_logs (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    company_name VARCHAR(200),
    business_number VARCHAR(12),
    vehicle_type VARCHAR(100),
    vehicle_registration_number VARCHAR(20),
    
    driving_date DATE NOT NULL,
    department VARCHAR(100),
    driver_name VARCHAR(100),
    
    mileage_before DECIMAL(10,2),
    mileage_after DECIMAL(10,2),
    distance DECIMAL(10,2),
    commute_distance DECIMAL(10,2),
    business_distance DECIMAL(10,2),
    
    total_distance DECIMAL(10,2),
    business_usage_distance DECIMAL(10,2),
    business_usage_ratio DECIMAL(5,2),
    
    vehicle_data_id BIGINT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (vehicle_data_id) REFERENCES vehicle_driving_data(data_id)
);

CREATE INDEX idx_user_date ON driving_logs(user_id, driving_date);
CREATE INDEX idx_company_date ON driving_logs(business_number, driving_date);

-- Expenses Table
CREATE TABLE expenses (
    expense_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    company_name VARCHAR(200),
    business_number VARCHAR(12),
    driver_name VARCHAR(100),
    vehicle_registration_number VARCHAR(20),
    
    expense_type VARCHAR(20) NOT NULL,
    expense_date DATE NOT NULL,
    store_name VARCHAR(200),
    amount DECIMAL(12,2) NOT NULL,
    
    detail_content TEXT,
    charging_kwh DECIMAL(10,2),
    
    phone VARCHAR(20),
    card_name VARCHAR(100),
    memo TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE INDEX idx_user_expense ON expenses(user_id, expense_date);
CREATE INDEX idx_expense_type ON expenses(expense_type);

-- Audit Logs Table
CREATE TABLE audit_logs (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    action VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50),
    entity_id BIGINT,
    description TEXT,
    ip_address VARCHAR(50),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_action ON audit_logs(user_id, action);
CREATE INDEX idx_created_at ON audit_logs(created_at);

-- Insert Default Admin User
INSERT INTO users (username, password, user_type, name, department, phone, email, approval_status, approved_at)
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', '관리자', 'IT팀', '010-0000-0000', 'admin@fms.com', 'APPROVED', CURRENT_TIMESTAMP);
