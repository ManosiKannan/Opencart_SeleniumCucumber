package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import baseClass.BaseClass;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='orangehrm-login-slot']/child::h5[normalize-space()='Login']")
	WebElement contentLogin;

	@FindBy(xpath = "//input[@placeholder='Username']")
	WebElement txtUserName;

	@FindBy(xpath = "//input[@placeholder='Password']")
	WebElement txtPassword;

	@FindBy(xpath = "//button[contains(@class,'login')]")
	WebElement btnLogin;

	public boolean isLoginDisplayed() throws IOException {
		BaseClass.waitforvisibilityOfElement(contentLogin);
		return contentLogin.isDisplayed();
	}

	public void setUserName(String username) throws IOException {
		BaseClass.waitforvisibilityOfElement(txtUserName);
		txtUserName.sendKeys(username);
	}

	public void setPassword(String password) throws IOException {
		BaseClass.waitforvisibilityOfElement(txtPassword);
		txtPassword.sendKeys(password);
	}

	public void clickLogin() throws IOException {
		BaseClass.waitforvisibilityOfElement(btnLogin);
		btnLogin.click();
	}
}
