package com.kpmg.webAutomation.scenarios;

import com.kpmg.webAutomation.controllers.SetUpTest;
import com.kpmg.webAutomation.dao.HomePageDAO;

public class HomePageScenarios extends SetUpTest {

    private HomePageDAO homePageDAO;

    public HomePageDAO getHomePageDAO() {
        return this.homePageDAO = new HomePageDAO();
    }

    public void verifyUserDataFromHomePage() throws Exception {
            log.info("Starting Home Page Scenario");
            homePageDAO = getHomePageDAO();
            homePageDAO.enterUserData();
    }
}
