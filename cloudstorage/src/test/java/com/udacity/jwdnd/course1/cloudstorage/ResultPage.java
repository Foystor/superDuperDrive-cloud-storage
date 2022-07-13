package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
    @FindBy(className = "continue-link")
    private WebElement continueLink;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public void continueBack() {
        continueLink.click();
    }
}
