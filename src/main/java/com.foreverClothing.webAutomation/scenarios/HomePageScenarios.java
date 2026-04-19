package com.kpmg.webAutomation.scenarios;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.kpmg.webAutomation.controllers.SetUpTest;
import com.kpmg.webAutomation.dao.HomePageDAO;
import com.kpmg.webAutomation.extentReport.ExtentFactory;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePageScenarios extends SetUpTest {

    private HomePageDAO homePageDAO;

//    public HomePageDAO getHomePageDAO() {
//        return this.homePageDAO = new HomePageDAO();
//    }

    public synchronized HomePageDAO getSTProps(String scenario) throws Exception {
        this.homePageDAO = new HomePageDAO(scenario);
        return homePageDAO;
    }

    public synchronized void setExtentlog(String scenario) {
        try {
            ExtentFactory extentFactory = new ExtentFactory();
            ExtentReports extentReport = extentFactory.getInstance(scenario);
            Reporter.getCurrentTestResult().getTestContext()
                    .setAttribute("extentReport" + Thread.currentThread().hashCode(), extentReport);
            ExtentTest extlogger = extentReport.createTest(scenario.substring(0, scenario.length() - 19));
            extlogger.assignCategory((String) Reporter.getCurrentTestResult().getTestContext()
                    .getAttribute("methodName" + Thread.currentThread().hashCode()));
            Reporter.getCurrentTestResult().getTestContext()
                    .setAttribute("extLogger" + Thread.currentThread().hashCode(), extlogger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyUserDataFromHomePage() throws Exception {
            String scenario = "";
            scenario = scenarioName
                    + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            setExtentlog(scenario);
            this.homePageDAO = getSTProps(scenario);
            log.info("Starting Home Page Scenario" + scenario);
            homePageDAO.enterUserData();
    }

    public void verifyUserDataFromHomePageTwo() throws Exception {
        String scenario = "";
        scenario = scenarioName
                + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        log.info("Scenario in scenario class: " + scenario);
        setExtentlog(scenario);
        this.homePageDAO = getSTProps(scenario);
        log.info("Starting Home Page Scenario");
        homePageDAO.enterUserDataTwo();
    }
}
