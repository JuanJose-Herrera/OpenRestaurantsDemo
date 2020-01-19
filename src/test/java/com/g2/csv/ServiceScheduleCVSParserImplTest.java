package com.g2.csv;

import com.g2.restaurant.ServiceSchedule;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceScheduleCVSParserImplTest {

    @Test
    void parseServiceScheduleString() throws ParseException {
        ServiceScheduleCVSParserImpl serviceScheduleCVSParser= new ServiceScheduleCVSParserImpl();
        final List<ServiceSchedule> serviceScheduleList = serviceScheduleCVSParser.parseServiceScheduleString("Mon-Sun");
        assertEquals(7,serviceScheduleList.size());
        assertEquals(DayOfWeek.TUESDAY,serviceScheduleList.get(1).getOpenTime().getDayOfWeek());
        assertEquals(DayOfWeek.TUESDAY,serviceScheduleList.get(1).getCloseTime().getDayOfWeek());
        assertEquals(23,serviceScheduleList.get(5).getCloseTime().getHour());
        assertEquals(59,serviceScheduleList.get(5).getCloseTime().getMinute());
        assertEquals(59,serviceScheduleList.get(5).getCloseTime().getSecond());
    }

    @Test
    void parseServiceScheduleStringWithSimpleTimeInterval() throws ParseException {
        ServiceScheduleCVSParserImpl serviceScheduleCVSParser= new ServiceScheduleCVSParserImpl();
        final List<ServiceSchedule> serviceScheduleList = serviceScheduleCVSParser.parseServiceScheduleString("Mon-Thu 11 am - 11 pm  ");
        assertEquals(4,serviceScheduleList.size());
        for (ServiceSchedule serviceSchedule:
             serviceScheduleList) {
            final LocalDateTime openTime = serviceSchedule.getOpenTime();
            assertEquals(11, openTime.getHour());
            assertEquals(0, openTime.getMinute());
            final LocalDateTime closeTime = serviceSchedule.getCloseTime();
            assertEquals(23, closeTime.getHour());
            assertEquals(0, closeTime.getMinute());
        }
    }

    @Test
    void parseServiceScheduleStringWithDaysInterval() throws ParseException {
        ServiceScheduleCVSParserImpl serviceScheduleCVSParser= new ServiceScheduleCVSParserImpl();
        final List<ServiceSchedule> serviceScheduleList = serviceScheduleCVSParser.parseServiceScheduleString("Mon-Thu, Sun 11:30 am - 9 pm");
        assertEquals(5,serviceScheduleList.size());
        for (ServiceSchedule serviceSchedule:
                serviceScheduleList) {
            final LocalDateTime openTime = serviceSchedule.getOpenTime();
            assertEquals(11, openTime.getHour());
            assertEquals(30, openTime.getMinute());
            final LocalDateTime closeTime = serviceSchedule.getCloseTime();
            assertEquals(21, closeTime.getHour());
            assertEquals(0, closeTime.getMinute());
        }
    }

    @Test
    void parseServiceScheduleStringWithMultiTimeInterval() throws ParseException {
        ServiceScheduleCVSParserImpl serviceScheduleCVSParser= new ServiceScheduleCVSParserImpl();
        final List<ServiceSchedule> serviceScheduleList = serviceScheduleCVSParser.parseServiceScheduleString("Mon-Wed 5 pm - 12:30 am  / Thu-Fri 5 pm - 1:30 am  / Sat 3 pm - 1:30 am  / Sun 3 pm - 11:30 pm");
        assertEquals(7,serviceScheduleList.size());

        assertEquals(17,serviceScheduleList.get(1).getOpenTime().getHour());
        assertEquals(0,serviceScheduleList.get(1).getOpenTime().getMinute());
        assertEquals(0,serviceScheduleList.get(1).getCloseTime().getHour());
        assertEquals(30,serviceScheduleList.get(1).getCloseTime().getMinute());

        assertEquals(17,serviceScheduleList.get(4).getOpenTime().getHour());
        assertEquals(0,serviceScheduleList.get(4).getOpenTime().getMinute());
        assertEquals(1,serviceScheduleList.get(4).getCloseTime().getHour());
        assertEquals(30,serviceScheduleList.get(4).getCloseTime().getMinute());

        assertEquals(DayOfWeek.TUESDAY,serviceScheduleList.get(0).getCloseTime().getDayOfWeek());
    }
}