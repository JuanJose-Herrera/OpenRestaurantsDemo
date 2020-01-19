package com.g2.csv;

import com.g2.restaurant.ServiceSchedule;

import java.text.ParseException;
import java.util.List;

public interface ServiceScheduleCVSParser {

    List<ServiceSchedule> parseServiceScheduleString(String serviceSchedule) throws ParseException;

}
