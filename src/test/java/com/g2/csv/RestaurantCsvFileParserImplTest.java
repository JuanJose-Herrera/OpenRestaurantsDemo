package com.g2.csv;

import com.g2.restaurant.Restaurant;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantCsvFileParserImplTest {

    @Test
    void getRestaurantsFromCVSFileFileNotExist() throws IOException, ParseException {
        RestaurantCsvFileParser restaurantCsvFileParser = new RestaurantCsvFileParserImpl();
        final List<Restaurant> restaurants = restaurantCsvFileParser.getRestaurantsFromCVSFile(Path.of("./"));
        assertTrue(restaurants.isEmpty());
    }

    @Test
    void getRestaurantsFromCVSFileFile() throws IOException, ParseException {
        Path resourceDirectory = Paths.get("src","test","java","resources","restaurant_hours.csv");
        RestaurantCsvFileParser restaurantCsvFileParser = new RestaurantCsvFileParserImpl();
        final List<Restaurant> restaurants = restaurantCsvFileParser.getRestaurantsFromCVSFile(resourceDirectory);
        assertFalse(restaurants.isEmpty());
    }
}