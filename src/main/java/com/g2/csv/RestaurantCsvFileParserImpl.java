package com.g2.csv;

import com.g2.restaurant.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RestaurantCsvFileParserImpl implements RestaurantCsvFileParser {

    private static Logger logger = LoggerFactory.getLogger(RestaurantCsvFileParserImpl.class);
    private final ServiceScheduleCVSParser serviceScheduleCVSParser = new ServiceScheduleCVSParserImpl();


    /**
     * @param filePath The file should exist or any restaurant will be loaded (no exception in this case)
     * @return A list of restaurants with a list of service schedule for every restaurant
     * @throws IOException    This exception is throws when an issue reading the file
     * @throws ParseException This is exception is throws when an error in the content in the file is found
     */
    @Override
    public List<Restaurant> getRestaurantsFromCVSFile(Path filePath) throws IOException, ParseException {
        if (Files.notExists(filePath) || Files.isDirectory(filePath) || !Files.isReadable(filePath)) {
            logger.error("The files can not be read");
            return Collections.emptyList();
        }
        Scanner dataScanner;
        int index = 0;
        List<Restaurant> restaurants = new ArrayList<>();
        //read foñe
        try (Scanner scanner = new Scanner(filePath)) {
            while (scanner.hasNextLine()) {
                dataScanner = new Scanner(scanner.nextLine());
                dataScanner.useDelimiter("\",\"");
                Restaurant restaurant = new Restaurant();
                //read every column
                while (dataScanner.hasNext()) {
                    String data = dataScanner.next().replace("\"","");
                    if (index == 0)
                        restaurant.setName(data);
                    else if (index == 1)
                        restaurant.setServiceSchedules(serviceScheduleCVSParser.parseServiceScheduleString(data));
                    else
                        throw new ParseException("The file contains more columsn that needed for the restaurants", 1);
                    index++;
                }

                index = 0;
                restaurants.add(restaurant);
            }

        } catch (IOException ex) {
            logger.error("Error reading the CSV file, an issue reading the file");
            throw ex;
        } catch (ParseException ex) {
            logger.error("Error parsing the CSV file");
            throw ex;
        }
        return restaurants;
    }


}
