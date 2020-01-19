package com.g2.parameters;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;

public class InputParameters {

    private Path filePath;
    private LocalDate dateFilter;
    private LocalTime timeFilter;

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public LocalDate getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(LocalDate dateFilter) {
        this.dateFilter = dateFilter;
    }

    public LocalTime getTimeFilter() {
        return timeFilter;
    }

    public void setTimeFilter(LocalTime timeFilter) {
        this.timeFilter = timeFilter;
    }
}
