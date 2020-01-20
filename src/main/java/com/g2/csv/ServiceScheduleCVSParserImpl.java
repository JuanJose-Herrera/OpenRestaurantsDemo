package com.g2.csv;

import com.g2.restaurant.ServiceSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

public class ServiceScheduleCVSParserImpl implements ServiceScheduleCVSParser {

    //use this format to get the day
    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);
    //use this parser to parse the time
    private static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendValue(CLOCK_HOUR_OF_AMPM)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR)
            .optionalEnd()
            .appendLiteral(' ')
            .appendPattern("a").toFormatter(Locale.US);

    @Override
    public List<ServiceSchedule> parseServiceScheduleString(String serviceSchedule) throws ParseException {
        if (serviceSchedule.isBlank()) {
            return Collections.emptyList();
        }
        //split by / to get all the different schedules
        final String[] strSchedules = serviceSchedule.split("/");

        List<ServiceSchedule> serviceScheduleList = new ArrayList<>();
        for (String strSchedule : strSchedules) {
            serviceScheduleList.addAll(this.parseScheduleStr(strSchedule.trim()));
        }
        return serviceScheduleList;
    }

    /**
     *
     * @param strSchedule Column 2 of the CSV file, only one schedule time
     * @return Return the list of services schedulers for just one restaurant
     * @throws ParseException If something is wrong in the schedule string
     */
    private List<ServiceSchedule> parseScheduleStr(String strSchedule) throws ParseException {

        //split the string in days interval and time interval
        Matcher matcher = Pattern.compile("([^\\d]+)(.*)").matcher(strSchedule);
        String daysInterval = "";
        String strTimeInterval;

        if (matcher.matches()) {
            daysInterval = matcher.group(1).trim();
        }
        strTimeInterval = matcher.group(2).trim();
        final TimeInterval timeInterval = this.parseTimeInterval(strTimeInterval);

        return this.parseMultiSchedule(daysInterval, timeInterval);


    }

    /**
     * This function parse the time interval set in the file
     * @param strTimeInterval String with just the string of the time to be parsed
     * @return Return an internal class that has the initial and final time
     */
    private TimeInterval parseTimeInterval(String strTimeInterval) {
        TimeInterval timeInterval = new TimeInterval();
        if (strTimeInterval.isBlank()) {
            timeInterval.setInitialTime(LocalTime.of(0, 0, 0));
            timeInterval.setEndTime(LocalTime.of(23, 59, 59));
        } else {
            final String[] times = strTimeInterval.split(" - ");
            timeInterval.setInitialTime(LocalTime.parse(times[0].toUpperCase(), dateTimeFormatter));
            timeInterval.setEndTime(LocalTime.parse(times[1].toUpperCase(), dateTimeFormatter));
        }
        return timeInterval;
    }

    private List<ServiceSchedule> parseMultiSchedule(String daysInterval, TimeInterval timeInterval) throws ParseException {
        String[] multipleDays = daysInterval.split(", ");
        List<ServiceSchedule> serviceScheduleList = new ArrayList<>();
        for (String interval :
                multipleDays) {
            if (interval.contains("-")) {
                serviceScheduleList.addAll(parseDaysIntervalSchedule(interval, timeInterval));
            } else {
                serviceScheduleList.add(parseDaySchedule(interval, timeInterval));
            }
        }

        return serviceScheduleList;
    }

    private ServiceSchedule parseDaySchedule(String strSchedule, TimeInterval timeInterval) throws ParseException {
        DayOfWeek dayOfWeek = dayFormat.parse(strSchedule.substring(0, 3)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek();

        ServiceSchedule serviceSchedule = setOpenAndCloseTime(timeInterval, dayOfWeek.getValue());


        return serviceSchedule;
    }

    private List<ServiceSchedule> parseDaysIntervalSchedule(String strSchedule, TimeInterval timeInterval) {
        GetDaysInterval getDaysInterval = new GetDaysInterval(strSchedule).invoke();

        if (getDaysInterval.is()) {
            return Collections.emptyList();
        }

        List<ServiceSchedule> serviceScheduleList = new ArrayList<>();
        int startDay = getDaysInterval.getStartDay();
        int endDay = getDaysInterval.getEndDay();

        for (int startLoop = startDay; startLoop <= endDay; startLoop++) {
            ServiceSchedule serviceSchedule = setOpenAndCloseTime(timeInterval, startLoop);

            serviceScheduleList.add(serviceSchedule);
        }
        return serviceScheduleList;
    }

    private ServiceSchedule setOpenAndCloseTime(TimeInterval timeInterval, int dayOfWeek) {
        ServiceSchedule serviceSchedule = new ServiceSchedule();

        serviceSchedule.setOpenTime(
                LocalDateTime.of(
                        LocalDate.of(1970, 1, 4),
                        timeInterval.getInitialTime()
                ).with(TemporalAdjusters.next(DayOfWeek.of(dayOfWeek))));

        LocalDateTime localEndTime= LocalDateTime.of(
                LocalDate.of(1970, 1, 4),
                timeInterval.getEndTime()
        ).with(TemporalAdjusters.next(DayOfWeek.of(dayOfWeek)));

        if ((timeInterval.getEndTime().isAfter(LocalTime.of(0,0,0)) || timeInterval.getEndTime().equals(LocalTime.of(0,0,0)))
                && timeInterval.getEndTime().isBefore(timeInterval.getInitialTime())){
            localEndTime = localEndTime.plusDays(1);
        }
        serviceSchedule.setCloseTime(localEndTime);
        return serviceSchedule;
    }


    private static class TimeInterval {
        private LocalTime initialTime;
        private LocalTime endTime;

        private LocalTime getInitialTime() {
            return initialTime;
        }

        private void setInitialTime(LocalTime initialTime) {
            this.initialTime = initialTime;
        }

        private LocalTime getEndTime() {
            return endTime;
        }

        private void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }
    }

    private static class GetDaysInterval {
        private boolean myResult;
        private String strSchedule;
        private int startDay;
        private int endDay;

        public GetDaysInterval(String strSchedule) {
            this.strSchedule = strSchedule;
        }

        boolean is() {
            return myResult;
        }

        public int getStartDay() {
            return startDay;
        }

        public int getEndDay() {
            return endDay;
        }

        public GetDaysInterval invoke() {
            try {
                startDay = dayFormat.parse(strSchedule.substring(0, 3)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().getValue();
                endDay = dayFormat.parse(strSchedule.substring(4, 7)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().getValue();
            } catch (ParseException e) {
                myResult = true;
                return this;
            }
            myResult = false;
            return this;
        }
    }
}
