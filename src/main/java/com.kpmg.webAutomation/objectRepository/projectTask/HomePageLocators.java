package com.kpmg.webAutomation.objectRepository.projectTask;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageLocators {

    @FindBy(xpath = "//div/label[text()='Name:']/following-sibling::input[@id='name']")
    public WebElement btnName;

    @FindBy(xpath = "//div/label[text()='Email:']/following-sibling::input[@id='email']")
    public WebElement btnPassword;

    @FindBy(xpath = "//div/label[text()='Phone:']/following-sibling::input[@id='phone']")
    public WebElement btnPhoneNumber;

    @FindBy(xpath = "//div/label[text()='Address:']/following-sibling::textarea[@id='textarea']")
    public WebElement btnAddress;

    @FindBy(xpath = "//div/label[text()='Gender:']/parent::div/div/label[text()='Male']/parent::div/input")
    public WebElement btnGender;

    @FindBy(xpath = "//div/label[text()='Days:']/parent::div/div/label[text()='Monday']/parent::div/input")
    public WebElement btnWeekDay;


//        Select select = new Select(driver.findElement(By.xpath("//label[text()='Country:']/parent::div/select")));
//        select.selectByValue("canada");

}
