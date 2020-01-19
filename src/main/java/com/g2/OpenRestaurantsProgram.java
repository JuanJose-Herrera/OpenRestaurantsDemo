package com.g2;

import com.g2.csv.RestaurantCsvFileParser;
import com.g2.csv.RestaurantCsvFileParserImpl;
import com.g2.parameters.InputParameters;
import com.g2.parameters.InputParametersParser;
import com.g2.parameters.InputParametersParserImpl;
import com.g2.restaurant.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class OpenRestaurantsProgram {

    private static Logger logger = LoggerFactory.getLogger(OpenRestaurantsProgram.class);

    public static void main(String[] args) throws IOException, ParseException {
        InputParametersParser inputParametersParser= new InputParametersParserImpl();
        InputParameters inputParameters = inputParametersParser.parseCommandLineParameters(args);

        RestaurantCsvFileParser restaurantCsvFileParser = new RestaurantCsvFileParserImpl();
        final List<Restaurant> restaurants = restaurantCsvFileParser.getRestaurantsFromCVSFile(inputParameters.getFilePath());

        // If port string is not valid, it will throw an exception.
        //int port = Integer.parseInt(cmd.getOptionValue("port"));
        final List<Restaurant> filteredRestaurants = restaurants.stream().filter(
                restaurant -> restaurant.getServiceSchedules().stream().anyMatch(
                        schedule -> schedule.isRestaurantOpen(inputParameters.getDateFilter(), inputParameters.getTimeFilter())
                )
        ).collect(Collectors.toList());
        for (Restaurant restaurant:
                filteredRestaurants) {
            System.out.println(restaurant.getName());
        }
    }

}
