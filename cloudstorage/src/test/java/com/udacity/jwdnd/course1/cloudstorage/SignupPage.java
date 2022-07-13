package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstnameField;

    @FindBy(id = "inputLastName")
    private WebElement lastnameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "buttonSignUp")
    private WebElement signupBtn;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public void signup(String firstname, String lastname, String username, String password) {
        firstnameField.sendKeys(firstname);
        lastnameField.sendKeys(lastname);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        signupBtn.click();
    }
}
