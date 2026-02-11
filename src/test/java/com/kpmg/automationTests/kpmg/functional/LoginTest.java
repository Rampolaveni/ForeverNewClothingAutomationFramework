package com.kpmg.automationTests.kpmg.functional;

import com.kpmg.webAutomation.controllers.SetUpTest;
import com.kpmg.webAutomation.scenarios.HomePageScenarios;
import org.testng.annotations.Test;

public class LoginTest extends SetUpTest {

    HomePageScenarios homePageScenario = new HomePageScenarios();

    @Test
    public void verifyLoginfromHomePage() throws Exception {
       homePageScenario.verifyUserDataFromHomePage();
    }
}
