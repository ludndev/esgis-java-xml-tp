package com.example.alert.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import java.time.ZonedDateTime;

/**
 * Represents an alert message with JAXB annotations for XML binding
 */
@XmlRootElement(name = "alert")
public class Alert {
    private ZonedDateTime raisedAt;
    private String platform;
    private String code;
    private String description;
    private String status;
    private ZonedDateTime lastUpdatedAt;

    @XmlElement
    public ZonedDateTime getRaisedAt() {
        return raisedAt;
    }

    public void setRaisedAt(ZonedDateTime raisedAt) {
        this.raisedAt = raisedAt;
    }

    @XmlElement
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @XmlElement
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement
    public ZonedDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}