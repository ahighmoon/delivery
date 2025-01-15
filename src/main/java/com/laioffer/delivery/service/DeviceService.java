package com.laioffer.delivery.service;

import com.laioffer.delivery.model.Device;
import com.laioffer.delivery.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    // example
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    // example
    public Device updateDeviceStatus(String id, Device.Status status) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Device not found"));
        device.setStatus(status);
        return deviceRepository.save(device);
    }
}
