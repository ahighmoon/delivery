package com.laioffer.delivery.controller;

import com.laioffer.delivery.model.Device;
import com.laioffer.delivery.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // 示例：更新设备状态
    @PatchMapping("/{id}/status")
    public void updateDeviceStatus(@PathVariable String id, @RequestParam String status) {
        // TODO: Implement the actual logic
    }
}
