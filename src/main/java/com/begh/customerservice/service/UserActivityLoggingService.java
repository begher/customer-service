package com.begh.customerservice.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import org.springframework.stereotype.Service;

@Service
public class UserActivityLoggingService {

    private static final String BASE_LOG_DIR = "./logs/userActivities";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void logActivity(String user, String action) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int weekOfYear = now.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        String directoryPath = BASE_LOG_DIR + "/" + year;
        String logFileName = String.format("%s/week-%d.txt", directoryPath, weekOfYear);

        try {
            Files.createDirectories(Paths.get(directoryPath));
            File logFile = new File(logFileName);
            try (FileWriter fw = new FileWriter(logFile, true)) {
                String formattedDateTime = LocalDateTime.now().format(dateTimeFormatter);
                fw.write(String.format("%s - User: %s, Action: %s%n", formattedDateTime, user, action));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}