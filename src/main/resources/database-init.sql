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
                       id CHAR(36) PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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

  