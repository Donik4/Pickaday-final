package com.donik.pickaday;

public class Services {
    private long serviceID;
    private String serviceName;

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    private String serviceIcon;
    private double serviceRate;

    public Services() {
    }

    public Services(long serviceID, String serviceName, String serviceIcon, double serviceRate) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceRate = serviceRate;
        this.serviceIcon = serviceIcon;
    }

    public long getServiceID() {
        return serviceID;
    }

    public void setServiceID(long serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }
}
