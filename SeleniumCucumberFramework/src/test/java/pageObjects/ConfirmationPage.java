package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPage extends BasePage {

	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@id='content']/child::h1[text()='Your Account Has Been Created!']")
	WebElement contentConfirm;

	public boolean isConfirmationMessageDisplayed() {
		try {
			return contentConfirm.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

}
