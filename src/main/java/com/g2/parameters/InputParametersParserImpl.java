package com.g2.parameters;


import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class InputParametersParserImpl implements InputParametersParser {


    private static final Logger logger = LoggerFactory.getLogger(InputParametersParser.class);

    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);

    @Override
    public InputParameters parseCommandLineParameters(String[] args) {
        Options options = new Options();
        options.addRequiredOption("f", "filePath", true, "Path of the CSV file to be used as a database");
        options.addRequiredOption("d", "day", true, "Day used to filter open restaurants (3 letters format)");
        options.addRequiredOption("t", "time", true, "Time used to filter open restaurants");

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
        try {
            inputParameters.setTimeFilter(LocalTime.parse(cmd.getOptionValue("time"), DateTimeFormatter.ISO_LOCAL_TIME));
        }catch (DateTimeParseException ex){
            logger.error("The hour can not be parsed, please review the readme file.");
            return null;
        }
        try {
            inputParameters.setDateFilter(  dayFormat.parse(cmd.getOptionValue("day")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate());
        } catch (java.text.ParseException e) {
           logger.error("The day can not be parsed, please review the readme file.");
           return null;
        }
        return  inputParameters;
    }
}
