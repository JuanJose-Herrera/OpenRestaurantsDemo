package com.g2.csv;

import com.g2.restaurant.Restaurant;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;

public interface RestaurantCsvFileParser {

    List<Restaurant> getRestaurantsFromCVSFile(Path filePath) throws IOException, ParseException;

}
