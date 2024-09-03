package pageObjects;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import baseClass.BaseClass;

public class MyInfoPage extends BasePage {

	public MyInfoPage(WebDriver driver) {
		super(driver);

	}

	@FindBy(xpath = "//a[normalize-space()='Personal Details']")
	WebElement lnkPersonalDetails;

	@FindBy(xpath = "//input[@placeholder='First Name']")
	WebElement txtFirstName;

	@FindBy(xpath = "//input[@placeholder='Middle Name']")
	WebElement txtMiddleName;

	@FindBy(xpath = "//input[@placeholder='Last Name']")
	WebElement txtLastName;

	@FindBy(xpath = "//label[text()='Employee Id']/following::input[1]")
	WebElement txtEmployeeId;

	@FindBy(xpath = "//label[text()='Other Id']/following::input[1]")
	WebElement txtOtherId;

	@FindBy(xpath = "//label[text()=\"Driver's License Number\"]/following::input[1]")
	WebElement txtDriverLicenseNumber;

	@FindBy(xpath = "//label[text()='Nationality']/following::i[contains(@class, 'oxd-icon bi-caret-down-fill')][1]")
	WebElement iconNationalityArrow;

	@FindBy(xpath = "//div[@class='oxd-select-text-input']")
	WebElement dynamicNationality;

	@FindBy(xpath = "//p[contains(@class,'orangehrm-form-hint')]/following-sibling::button[normalize-space()='Save']")
	WebElement btnFirstSave;

	@FindBy(xpath = "//div[@class='orangehrm-custom-fields']//button[@type='submit'][normalize-space()='Save']")
	WebElement btnSecondSSave;

	@FindBy(xpath = "//button[@fdprocessedid='swsvrh']")
	WebElement btnThirdSave;

	public void clickPersonalDetails() throws IOException {
		BaseClass.waitforElementToBeClicable(lnkPersonalDetails);
		lnkPersonalDetails.click();
	}

	public String setFirstName(String firstName) throws IOException {

		BaseClass.waitForPageToLoad();
		BaseClass.waitforElementToBeClicable(txtFirstName);
		BaseClass.clearInputFieldValueUsingBackspaceKeys(txtFirstName);
		txtFirstName.sendKeys(firstName);
		return txtFirstName.getAttribute("value");
	}

	public String setMiddleName(String middleName) throws IOException {
		BaseClass.waitforElementToBeClicable(txtMiddleName);
		BaseClass.clearInputFieldValueUsingBackspaceKeys(txtMiddleName);
		txtMiddleName.sendKeys(middleName);
		return txtMiddleName.getAttribute("value");
	}

	public String setLastName(String lastName) throws IOException {
		BaseClass.clearInputFieldValueUsingBackspaceKeys(txtLastName);
		txtLastName.sendKeys(lastName);
		return txtLastName.getAttribute("value");
	}

	public String setEmployeeId(String employeeId) {
		BaseClass.clearInputFieldValueUsingBackspaceKeys(txtEmployeeId);
		txtEmployeeId.sendKeys(employeeId);
		return txtEmployeeId.getAttribute("value");
	}

	public String setOtherId(String otherId) {
		BaseClass.clearInputFieldValueUsingBackspaceKeys(txtOtherId);
		txtOtherId.sendKeys(otherId);
		return txtOtherId.getAttribute("value");
	}

	public void clickFirstSaveButton() {
		btnFirstSave.click();
	}

}
