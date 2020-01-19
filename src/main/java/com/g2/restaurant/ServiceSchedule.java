package com.g2.restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ServiceSchedule {
    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isRestaurantOpen(LocalDate date, LocalTime time){
        int dayOfWeek = date.getDayOfWeek().getValue();
        if(this.openTime.getDayOfWeek().equals(this.closeTime.getDayOfWeek())) {
            if (dayOfWeek >= this.getOpenTime().getDayOfWeek().getValue() && dayOfWeek <= this.getCloseTime().getDayOfWeek().getValue()) {
                if ((time.isAfter(this.openTime.toLocalTime()) || time.equals(this.openTime.toLocalTime())) && (time.isBefore(this.closeTime.toLocalTime()) || time.equals(this.closeTime.toLocalTime()))) {
                    return Boolean.TRUE;
                }
            }
        }else{
            if (dayOfWeek >= this.getOpenTime().getDayOfWeek().getValue() && dayOfWeek <= this.getCloseTime().getDayOfWeek().getValue()) {
                if(this.openTime.getDayOfWeek().equals(date.getDayOfWeek()) && (time.isAfter(this.openTime.toLocalTime()) || time.equals(this.openTime.toLocalTime()))
                    ||this.closeTime.getDayOfWeek().equals(date.getDayOfWeek()) && (time.isBefore(this.closeTime.toLocalTime()) || time.equals(this.closeTime.toLocalTime())))
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
