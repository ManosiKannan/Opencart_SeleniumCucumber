package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import baseClass.BaseClass;

public class MainMenu extends BasePage {

	public MainMenu(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[normalize-space()='My Info']")
	WebElement menuMyInfo;

	public void clickMyInfoMenu() throws IOException {
		BaseClass.waitforvisibilityOfElement(menuMyInfo);
		menuMyInfo.click();
	}

}
