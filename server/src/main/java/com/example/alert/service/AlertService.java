package com.example.alert.service;

import com.example.alert.model.Alert;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;

/**
 * SOAP Web Service interface for alert management
 */
@WebService
public interface AlertService {
    
    /**
     * Processes an alert (create, update or close)
     * @param alert The alert to process
     * @return Action performed (Created, Updated, Closed)
     */
    @WebMethod
    String processAlert(Alert alert);
    
    /**
     * Lists all active alerts
     * @return List of active alerts
     */
    @WebMethod
    List<Alert> listAlerts();
}