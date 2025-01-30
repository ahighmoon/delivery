-- 禁用外键检查
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS deliveries;
DROP TABLE IF EXISTS devices;

-- 启用外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- 创建用户表
CREATE TABLE users (
    id CHAR(36)PRIMARY KEY,               -- 用户 ID，主键
    email VARCHAR(255) NOT NULL UNIQUE, -- 邮箱，唯一
    password VARCHAR(255) NOT NULL,    -- 加密后的密码
    name VARCHAR(255),        -- 用户姓名
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER', -- 用户角色（默认普通用户）
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
);

-- 创建订单表
CREATE TABLE orders (
    id CHAR(36) PRIMARY KEY,               -- 订单 ID，主键
    user_id CHAR(36) NOT NULL,             -- 下单用户 ID，外键关联 users 表
    delivery_id CHAR(36),                  -- 配送 ID，外键关联 deliveries 表
    sender_name VARCHAR(255) NOT NULL, -- 发件人姓名
    sender_email VARCHAR(255) NOT NULL, -- 发件人邮箱
    sender_address TEXT NOT NULL,      -- 发件人地址
    recipient_name VARCHAR(255) NOT NULL, -- 收件人姓名
    recipient_email VARCHAR(255) NOT NULL, -- 收件人邮箱
    recipient_address TEXT NOT NULL,   -- 收件人地址
    package_weight FLOAT NOT NULL,     -- 包裹重量
    package_dimensions VARCHAR(50) NOT NULL, -- 包裹尺寸 (格式如 10x10x10)
    status ENUM('Ordered', 'Dispatched', 'In Transit', 'Completed') NOT NULL DEFAULT 'Ordered', -- 订单状态
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
);

-- 创建配送表
CREATE TABLE deliveries (
    id CHAR(36) PRIMARY KEY,               -- 配送 ID，主键
    delivery_type ENUM('Robot', 'Drone') NOT NULL, -- 配送方式
    device_id CHAR(36) NOT NULL,           -- 配送设备 ID，外键关联 devices 表
    route_info JSON NOT NULL,          -- 路线信息（JSON 格式）
    price FLOAT NOT NULL,              -- 估算价格
    estimate_time INT NOT NULL,        -- 估算时间（分钟）
    actual_time INT,                   -- 实际时间（分钟）
    dispatched_time TIMESTAMP,         -- 分配任务时间
    transit_start_time TIMESTAMP,      -- 开始递送时间
    transit_end_time TIMESTAMP,        -- 完成递送时间
    finish_time TIMESTAMP              -- 返回配送站时间
);

-- 创建设备表
CREATE TABLE devices (
    id CHAR(36) PRIMARY KEY,               -- 设备 ID，主键
    type ENUM('Robot', 'Drone') NOT NULL, -- 设备类型
    location POINT NOT NULL,           -- 实时位置（地理坐标）
    station VARCHAR(255) NOT NULL,     -- 当前所在配送站
    capacity FLOAT NOT NULL DEFAULT 0.0, -- 最大容量（单位：kg）
    status ENUM('Idle', 'Dispatched', 'In Transit') NOT NULL DEFAULT 'Idle' -- 当前状态
);

-- 添加外键约束
ALTER TABLE orders
    ADD CONSTRAINT fk_orders_users
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE;

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_deliveries
    FOREIGN KEY (delivery_id) REFERENCES deliveries(id)
    ON DELETE SET NULL;

ALTER TABLE deliveries
    ADD CONSTRAINT fk_deliveries_devices
    FOREIGN KEY (device_id) REFERENCES devices(id)
    ON DELETE CASCADE;

-- 生成一个 UUID 作为用户 ID
SET @new_uuid = UUID();

-- 插入用户数据
INSERT INTO users (id, email, password, name, role)
VALUES (
  @new_uuid,
  'admin@example.com',
  '$2b$12$LGfue2XVoBYwemyp7zAw5urpBeqxkRSX4KsXaW.bEahi7lni7hghm',
  'System Administrator',
  'ADMIN'
);