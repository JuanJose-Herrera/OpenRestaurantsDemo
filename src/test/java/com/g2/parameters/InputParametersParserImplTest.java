package com.g2.parameters;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InputParametersParserImplTest {

    @Test
    void parseCommandLineParametersEmptyParameters() {
        InputParametersParser inputParametersParser= new InputParametersParserImpl();
        assertThrows(RuntimeException.class, () -> inputParametersParser.parseCommandLineParameters(new String[]{}));
    }


    @Test
    void parseCommandLineParameters() {
        InputParametersParser inputParametersParser= new InputParametersParserImpl();
        String[] args= new String[]{ "-fC:\\restaurant_hours.csv" , "-dTue", "-t10:15:30"};

        InputParameters inputParameters = inputParametersParser.parseCommandLineParameters(args);
        Path filePath = inputParameters.getFilePath();
        assertNotNull(filePath);
        assertEquals(Path.of("C:\\restaurant_hours.csv"),filePath);

        LocalDate dateFilter = inputParameters.getDateFilter();
        assertNotNull(dateFilter);
        assertEquals(DayOfWeek.TUESDAY,dateFilter.getDayOfWeek());
        assertNotNull(inputParameters.getTimeFilter());
        assertEquals(10,inputParameters.getTimeFilter().getHour());

    }

    @Test
    void parseCommandLineParametersAM() {
        InputParametersParser inputParametersParser= new InputParametersParserImpl();
        String[] args= new String[]{ "-fC:\\restaurant_hours.csv" , "-dMon", "-t01:30"};

        InputParameters inputParameters = inputParametersParser.parseCommandLineParameters(args);
        Path filePath = inputParameters.getFilePath();
        assertNotNull(filePath);
        assertEquals(Path.of("C:\\restaurant_hours.csv"),filePath);

        LocalDate dateFilter = inputParameters.getDateFilter();
        assertNotNull(dateFilter);
        assertEquals(DayOfWeek.MONDAY,dateFilter.getDayOfWeek());
        assertNotNull(inputParameters.getTimeFilter());
        assertEquals(1,inputParameters.getTimeFilter().getHour());

    }

    @Test
    void parseCommandLineParametersPM() {
        InputParametersParser inputParametersParser= new InputParametersParserImpl();
        String[] args= new String[]{ "-fC:\\restaurant_hours.csv" , "-dWed", "-t22:15"};

        InputParameters inputParameters = inputParametersParser.parseCommandLineParameters(args);
        Path filePath = inputParameters.getFilePath();
        assertNotNull(filePath);
        assertEquals(Path.of("C:\\restaurant_hours.csv"),filePath);

        LocalDate dateFilter = inputParameters.getDateFilter();
        assertNotNull(dateFilter);
        assertEquals(DayOfWeek.WEDNESDAY,dateFilter.getDayOfWeek());
        assertNotNull(inputParameters.getTimeFilter());
        assertEquals(22,inputParameters.getTimeFilter().getHour());

    }



}