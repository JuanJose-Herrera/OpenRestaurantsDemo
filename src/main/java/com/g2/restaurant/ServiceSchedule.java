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
        //review that the open time and close time is in the same day
        if(this.openTime.getDayOfWeek().equals(this.closeTime.getDayOfWeek())) {
            //review  the day of the week is inside the open period
            if (dayOfWeek >= this.openTime.getDayOfWeek().getValue()
                    && dayOfWeek <= this.closeTime.getDayOfWeek().getValue()) {
                        //if day is inside the period review that the time is inside the open period
                        if ((time.isAfter(this.openTime.toLocalTime()) || time.equals(this.openTime.toLocalTime()))
                                && time.isBefore(this.closeTime.toLocalTime())) {
                            return Boolean.TRUE;
                        }
            }
        }else{
            //review  the day of the week is inside the open period
            if (dayOfWeek >= this.openTime.getDayOfWeek().getValue()
                    && dayOfWeek <= this.closeTime.getDayOfWeek().getValue()) {
                //review if is open after the open time until the end of the day
                if((this.openTime.getDayOfWeek().equals(date.getDayOfWeek()) && (time.isAfter(this.openTime.toLocalTime()) || time.equals(this.openTime.toLocalTime())))
                        //review if is open before the close time in the next day
                    || (this.closeTime.getDayOfWeek().equals(date.getDayOfWeek()) && time.isBefore(this.closeTime.toLocalTime()) ))
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
