package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseClass.BaseClass;

public class HeaderSection extends BasePage {

	public HeaderSection(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h6[normalize-space()='Dashboard']")
	public WebElement contentDashboard;

	@FindBy(xpath = "//i[contains(@class,'oxd-userdropdown-icon')]")
	WebElement userDropdownIcon;

	@FindBy(xpath = "//a[normalize-space()='Logout']")
	WebElement lnkLogout;

	public String getDashboardHeader() throws IOException {
		BaseClass.waitforvisibilityOfElement(contentDashboard);
		return contentDashboard.getText();
	}

	public void clickOnUserDropdownIcon() throws IOException {

		BaseClass.waitforvisibilityOfElement(userDropdownIcon);
		userDropdownIcon.click();
	}

	public void clickLogout() throws IOException {
		BaseClass.waitforvisibilityOfElement(lnkLogout);
		lnkLogout.click();
	}

}
