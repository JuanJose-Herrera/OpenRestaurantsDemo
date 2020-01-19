package com.g2.restaurant;

import java.util.List;

public class Restaurant {

    private String name;
    private List<ServiceSchedule> serviceSchedules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceSchedule> getServiceSchedules() {
        return serviceSchedules;
    }

    public void setServiceSchedules(List<ServiceSchedule> serviceSchedules) {
        this.serviceSchedules = serviceSchedules;
    }
}
