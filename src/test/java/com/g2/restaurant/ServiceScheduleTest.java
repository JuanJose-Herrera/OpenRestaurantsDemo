package com.g2.restaurant;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ServiceScheduleTest {

    @Test
    void isRestaurantOpen() {
        ServiceSchedule serviceSchedule= new ServiceSchedule();
        //open monday from 11:30 to 23:30
        serviceSchedule.setOpenTime(LocalDateTime.of(1970,1,5,11,30));
        serviceSchedule.setCloseTime(LocalDateTime.of(1970,1,5,23,30));

        //not open on monday at 8 am
        assertFalse(serviceSchedule.isRestaurantOpen(LocalDate.of(1970,1,5), LocalTime.of(8,0)));
    }

    @Test
    void isRestaurantOpenBetweenDays() {
        ServiceSchedule serviceSchedule= new ServiceSchedule();
        //open monday from 11:30 to 23:30
        serviceSchedule.setOpenTime(LocalDateTime.of(1970,1,5,18,30));
        serviceSchedule.setCloseTime(LocalDateTime.of(1970,1,6,2,30));

        //not open on tuesday at 4 am
        assertFalse(serviceSchedule.isRestaurantOpen(LocalDate.of(1970,1,6), LocalTime.of(4,0)));
        //open at tuesday at 1 am
        assertTrue(serviceSchedule.isRestaurantOpen(LocalDate.of(1970,1,6), LocalTime.of(1,0)));
        //not open on monday at 5 pm
        assertFalse(serviceSchedule.isRestaurantOpen(LocalDate.of(1970,1,5), LocalTime.of(17,0)));
        //open at monday at 7 pm
        assertTrue(serviceSchedule.isRestaurantOpen(LocalDate.of(1970,1,5), LocalTime.of(19,0)));
    }
}