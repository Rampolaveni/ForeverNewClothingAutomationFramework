package com.kpmg.webAutomation.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log4jUtil {

    private static final String CONFIG_PATH =
            System.getProperty("user.dir")
                    + "/src/main/resources/commonConfig/log4j2.xml";

    private static volatile boolean initialized = false;

    private static final DateTimeFormatter DATE_FOLDER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter RUN_FILE =
            DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

    public static synchronized void init() {

        if (initialized) return;
        String projectRoot = System.getProperty("user.dir");

        // Root folder
        Path rootDir = Paths.get(projectRoot, "logs/webAutomationLogs");

        // Today's folder
        String today = LocalDate.now().format(DATE_FOLDER);

        Path todayDir = rootDir.resolve(today);

        try {
            Files.createDirectories(todayDir);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to create log directories: " + todayDir, e);
        }

        // Run filename
        String runId =
                LocalDateTime.now().format(RUN_FILE);

        // MUST be before log4j init
        System.setProperty("logRoot", rootDir.toString());
        System.setProperty("logDate", today);
        System.setProperty("runId", runId);

        // Force THIS config file
        Configurator.initialize(
                "FrameworkLogger",
                null,
                CONFIG_PATH
        );
        initialized = true;
    }

    public static Logger loadLogger(Class<?> clazz) {
        init();
        return LogManager.getLogger(clazz);
    }
}
