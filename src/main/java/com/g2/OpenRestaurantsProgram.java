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

        //review if the input parameters was parsed correctly, if not, end the program.
        if(null == inputParameters){
            return;
        }
        RestaurantCsvFileParser restaurantCsvFileParser = new RestaurantCsvFileParserImpl();
        final List<Restaurant> restaurants = restaurantCsvFileParser.getRestaurantsFromCVSFile(inputParameters.getFilePath());

        // If port string is not valid, it will throw an exception.
        //int port = Integer.parseInt(cmd.getOptionValue("port"));
        final List<Restaurant> filteredRestaurants = restaurants.stream().filter(
                restaurant -> restaurant.getServiceSchedules().stream().anyMatch(
                        schedule -> schedule.isRestaurantOpen(inputParameters.getDateFilter(), inputParameters.getTimeFilter())
                )
        ).collect(Collectors.toList());

        if(filteredRestaurants.isEmpty()){
            System.out.println(String.format("No restaurants open on %s at %s", inputParameters.getDateFilter().getDayOfWeek().name(),inputParameters.getTimeFilter().toString()));
        }else {
            System.out.println(String.format("The following restaurants are open on %s at %s :", inputParameters.getDateFilter().getDayOfWeek().name(),inputParameters.getTimeFilter().toString()));
            for (Restaurant restaurant :
                    filteredRestaurants) {
                System.out.println(restaurant.getName());
            }
        }

    }

}
