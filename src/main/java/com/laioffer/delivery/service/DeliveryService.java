package com.laioffer.delivery.service;

import com.laioffer.delivery.model.Delivery;
import com.laioffer.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    // example
    public Delivery getDeliveryById(String id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    // example
    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }
}
