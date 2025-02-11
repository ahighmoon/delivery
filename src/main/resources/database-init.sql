-- 禁用外键检查，防止删除表时报错
SET FOREIGN_KEY_CHECKS = 0;

-- 删除旧表
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
                        id CHAR(36) PRIMARY KEY,
                        user_id CHAR(36) NOT NULL,
                        delivery_id CHAR(36),
                        sender_name VARCHAR(255) NOT NULL,
                        sender_email VARCHAR(255) NOT NULL,
                        sender_address TEXT NOT NULL,
                        recipient_name VARCHAR(255) NOT NULL,
                        recipient_email VARCHAR(255) NOT NULL,
                        recipient_address TEXT NOT NULL,
                        package_weight FLOAT NOT NULL,
                        package_dimensions VARCHAR(50) NOT NULL,
                        status ENUM('ORDERED', 'DISPATCHED', 'IN_TRANSIT', 'COMPLETED') NOT NULL DEFAULT 'ORDERED',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建配送表
CREATE TABLE deliveries (
                            id CHAR(36) PRIMARY KEY,
                            delivery_type ENUM('ROBOT', 'DRONE') NOT NULL,
                            device_id CHAR(36) NOT NULL,
                            route_info JSON NOT NULL,
                            price FLOAT NOT NULL,
                            estimate_time INT NOT NULL,
                            actual_time INT,
                            dispatched_time TIMESTAMP,
                            transit_start_time TIMESTAMP,
                            transit_end_time TIMESTAMP,
                            finish_time TIMESTAMP
);

-- 创建设备表
CREATE TABLE devices (
                         id CHAR(36) PRIMARY KEY,
                         type ENUM('ROBOT', 'DRONE') NOT NULL,
                         location JSON NOT NULL,
                         station VARCHAR(255) NOT NULL,
                         capacity FLOAT NOT NULL DEFAULT 0.0,
                         status ENUM('IDLE', 'DISPATCHED', 'IN_TRANSIT') NOT NULL DEFAULT 'IDLE'
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
