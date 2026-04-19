package com.kpmg.webAutomation.extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.kpmg.webAutomation.controllers.SetUpTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExtentFactory extends SetUpTest {
    public ExtentReports getInstance(String scenarioMethod) throws IOException {

        String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        String testType = System.getProperty("testType").toUpperCase();

        String extentPath =
                reportPath + File.separator +
                        date + File.separator +
                        suiteName.toUpperCase() + File.separator +
                        strFlow + File.separator +
                        testType + File.separator +
                        scenarioMethod;

        File reportDir = new File(extentPath);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        String reportFile =
                extentPath + File.separator + scenarioMethod + ".html";

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(reportFile);

        sparkReporter.loadXMLConfig(
                new File(System.getProperty("user.dir")
                        + File.separator
                        + "src"
                        + File.separator
                        + "test"
                        + File.separator
                        + "resources"
                        + File.separator
                        + "extent"
                        + File.separator
                        + "extent-config.xml")
        );

        ExtentReports extent = new ExtentReports();
        Locale.setDefault(Locale.ENGLISH);
        extent.attachReporter(sparkReporter);

        return extent;
    }
}