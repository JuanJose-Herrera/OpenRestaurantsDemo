# **OpenRestaurantsDemo**
An exercise that show how to work with an small CVS file, and filter the information by date and time

This demo was created using JDK 11, so please make sure to run it with Java 11 or later.

To run this demo you can use the maven exec instructions, the application needs 3 parameters (path, day, time), the 
following list will show how to fill the parameters:

- f: this is the input file that should contains the list of restaurants with the service schedules, the path should be an absolute path.
- d: this is the day to filter the open restaurants, this parameters should be send in 3 letters format in english 
(Mon,Tue,Wen,Thu,Fri,Sat,Sun) 
- t: this is the time (hour) to be used to filter the open restaurants, the format should be on 24 hours like the following examples: 10:30, 18:21 or 21

#### Please try the following command:
 
`mvn exec:java -D exec.mainClass=com.g2.OpenRestaurantsProgram -D exec.args="-f'E:\Mis Documentos\G2WebServices\OpenRestaurantsDemo\src\main\resources\restaurant_hours.csv' -dTue -t00:30"`

#### In order to run it from the JAR, execute the following command:

`java -jar .\OpenRestaurantsDemo-1.0-SNAPSHOT-jar-with-dependencies.jar -f'E:\Mis Documentos\G2WebServices\OpenRestaurantsDemo\src\main\resources\restaurant_hours.csv' -d Tue -t 00:30`

Note: if the restaurant close at 19:30 every friday, and you search for an open restaurant on friday at 19:30 will not appear on the results, the system takes this as already closed restaurant. 
 


