package com.example.alert.client;

import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.ws.WebServiceClient;

/**
 * Main client class that monitors for alert files and processes them
 */
public class AlertMonitor {
    
    @WebServiceRef
    private static AlertService alertService;
    
    private final String watchDir;
    private final String backupDir;
    private final int scanInterval;
    private final int backupInterval;
    
    public AlertMonitor(String watchDir, String backupDir, int scanInterval, int backupInterval) {
        this.watchDir = watchDir;
        this.backupDir = backupDir;
        this.scanInterval = scanInterval;
        this.backupInterval = backupInterval;
    }
    
    public void startMonitoring() {
        // Timer for scanning alert files
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scanAlertFiles();
            }
        }, 0, scanInterval * 1000L);
        
        // Timer for backup alerts
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                backupAlerts();
            }
        }, 0, backupInterval * 1000L);
    }
    
    private void scanAlertFiles() {
        try {
            Files.list(Paths.get(watchDir))
                .filter(p -> p.toString().endsWith(".xml"))
                .forEach(this::processAlertFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void processAlertFile(Path filePath) {
        try {
            // Read and process alert file
            JAXBContext context = JAXBContext.newInstance(Alert.class);
            Alert alert = (Alert) context.createUnmarshaller().unmarshal(filePath.toFile());
            
            // Send to server
            String result = alertService.processAlert(alert);
            System.out.println("Alert processed: " + result);
            
            // Delete processed file
            Files.delete(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void backupAlerts() {
        try {
            List<Alert> alerts = alertService.listAlerts();
            if (!alerts.isEmpty()) {
                File backupFile = new File(backupDir, "alerts_" + System.currentTimeMillis() + ".xml");
                
                JAXBContext context = JAXBContext.newInstance(Alert.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(alerts, backupFile);
                
                System.out.println("Backup created: " + backupFile.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
