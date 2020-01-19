package com.g2.parameters;

import com.g2.OpenRestaurantsProgram;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class InputParametersParserImpl implements InputParametersParser {


    private static final Logger logger = LoggerFactory.getLogger(InputParametersParser.class);

    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);

    @Override
    public InputParameters parseCommandLineParameters(String[] args) {
        Options options = new Options();
        options.addRequiredOption("f", "filePath", true, "Path of the CSV file to be used as a database");
        options.addRequiredOption("d", "day", true, "Day used to filter open restaurants (3 letters format)");
        options.addRequiredOption("t", "time", true, "Date used to filter open restaurants");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Error parsing the command line arguments",e);
            throw new RuntimeException(e);
        }



        InputParameters inputParameters= new InputParameters();
        inputParameters.setFilePath(Path.of(cmd.getOptionValue("filePath")));
        inputParameters.setTimeFilter(LocalTime.parse(cmd.getOptionValue("time"), DateTimeFormatter.ISO_TIME));
        try {
            inputParameters.setDateFilter(  dayFormat.parse(cmd.getOptionValue("day")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return  inputParameters;
    }
}
