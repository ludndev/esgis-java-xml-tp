package com.example.alert.service;

import com.example.alert.model.Alert;
import jakarta.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of SOAP web service for alert management
 */
@WebService(endpointInterface = "com.example.alert.service.AlertService")
public class AlertServiceImpl implements AlertService {
    
    // Thread-safe storage for alerts by platform
    private final ConcurrentHashMap<String, List<Alert>> alertsByPlatform = new ConcurrentHashMap<>();

    @Override
    public String processAlert(Alert alert) {
        String platform = alert.getPlatform();
        String code = alert.getCode();
        String status = alert.getStatus();
        
        alertsByPlatform.putIfAbsent(platform, new ArrayList<>());
        List<Alert> platformAlerts = alertsByPlatform.get(platform);
        
        if ("OPEN".equals(status)) {
            // Check if alert with same code exists
            boolean exists = platformAlerts.stream()
                .anyMatch(a -> a.getCode().equals(code));
                
            if (exists) {
                // Update existing alert
                platformAlerts.replaceAll(a -> 
                    a.getCode().equals(code) ? alert : a);
                return "Updated";
            } else {
                // Add new alert
                platformAlerts.add(alert);
                return "Created";
            }
        } else if ("CLOSED".equals(status)) {
            // Remove alert if exists
            boolean removed = platformAlerts.removeIf(a -> a.getCode().equals(code));
            return removed ? "Closed" : "No action";
        }
        
        return "No action";
    }

    @Override
    public List<Alert> listAlerts() {
        List<Alert> allAlerts = new ArrayList<>();
        alertsByPlatform.values().forEach(allAlerts::addAll);
        return allAlerts;
    }
}