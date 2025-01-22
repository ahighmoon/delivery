package com.laioffer.delivery.model;

public class Plan {
    private String type;  // "drone" or "robot"
    private double distance;
    private double time;
    private double price;
    private String origin;
    private String destination;

    public Plan(String type, double distance, double estimatedTime, double price, String origin, String destination) {
        this.type = type;
        this.distance = distance;
        this.time = estimatedTime;
        this.price = price;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "type='" + type + '\'' +
                ", distance=" + distance +
                ", estimatedTime=" + time +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
