package stepDefinitions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.StepNameTracker;
import io.cucumber.datatable.DataTable;
import org.testng.Assert;
import annotations.StepMethod;
import baseClass.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HeaderSection;
import pageObjects.LoginPage;
import pageObjects.MainMenu;
import pageObjects.MyInfoPage;

public class StepDefinitions extends BaseClass {

	LoginPage loginPage;
	HeaderSection headerSection;
	MainMenu mainMenu;
	MyInfoPage myInfoPage;
	Map<String, String> dataMap;

	@Given("User navigate to OrangeHRM Login screen")
	@StepMethod("User navigate to OrangeHRM Login screen")
	public void user_navigate_to_orange_hrm_login_screen() throws IOException {
		StepNameTracker.setStepName("User navigate to OrangeHRM Login screen");
		loginPage = new LoginPage(getDriver());
		Assert.assertTrue(loginPage.isLoginDisplayed());
		getLogger().info("User navigated to OrangeHRM Login screen");
	}

	@Given("User enters Username and Password")
	@StepMethod("User enters Username and Password")
	public void user_enters_username_and_password() throws IOException {
		StepNameTracker.setStepName("User enters Username and Password");
		String userName = getProperties().getProperty("username");
		String password = getProperties().getProperty("password");
		loginPage.setUserName(userName);
		loginPage.setPassword(password);
		getLogger().info("User entered the username and password successfully");
	}

	@When("User clicks on Login button")
	@StepMethod("User clicks on Login button")
	public void user_clicks_on_login_button() throws IOException {
		StepNameTracker.setStepName("User clicks on Login button");
		loginPage.clickLogin();
		headerSection = new HeaderSection(getDriver());
		getLogger().info("User click on Login button sucessfully");
	}

	@Then("User should be successfully logged into OrangeHRM and Dashbord text should be displayed")
	@StepMethod("User should be successfully logged into OrangeHRM and Dashbord text should be displayed")
	public void user_should_be_successfully_logged_into_orange_hrm() throws IOException {
		StepNameTracker
				.setStepName("User should be successfully logged into OrangeHRM and Dashbord text should be displayed");
		String dashboardHeader = headerSection.getDashboardHeader();
		Assert.assertEquals(dashboardHeader, "Dashboard");
		getLogger().info("Dashboard header text is displayed");

	}

	@Then("Clicks on Logout button")
	@StepMethod("Clicks on Logout button")
	public void clicks_on_logout_button() throws IOException {
		StepNameTracker.setStepName("Clicks on Logout button");
		headerSection.clickOnUserDropdownIcon();
		headerSection.clickLogout();
		getLogger().info("User clicked on Logout button successfully");
	}

	@Then("Verify that successfully the user is logged out")
	@StepMethod("Verify that successfully the user is logged out")
	public void verify_that_successfully_the_user_is_logged_out() throws IOException {
		StepNameTracker.setStepName("Verify that successfully the user is logged out");
		Assert.assertTrue(loginPage.isLoginDisplayed());
		getLogger().info("Logged out is successful");
	}

	@Given("the user is logged in and navigates to the personal details page for employee")
	@StepMethod("the user is logged in and navigates to the personal details page for employee")
	public void the_user_is_logged_in_and_navigates_to_the_personal_details_page_for_employee()
			throws IOException, InterruptedException {
		StepNameTracker.setStepName("the user is logged in and navigates to the personal details page for employee");
		loginPage = new LoginPage(getDriver());
		Assert.assertTrue(loginPage.isLoginDisplayed());
		getLogger().info("User navigated to OrangeHRM Login screen");
		String userName = getProperties().getProperty("username");
		String password = getProperties().getProperty("password");
		loginPage.setUserName(userName);
		loginPage.setPassword(password);
		getLogger().info("User entered the username and password successfully");
		loginPage.clickLogin();
		headerSection = new HeaderSection(getDriver());
		getLogger().info("User click on Login button sucessfully");
		String dashboardHeader = headerSection.getDashboardHeader();
		Assert.assertEquals(dashboardHeader, "Dashboard");
		getLogger().info("Dashboard header text is displayed");
		mainMenu = new MainMenu(getDriver());
		mainMenu.clickMyInfoMenu();
		myInfoPage = new MyInfoPage(getDriver());
		myInfoPage.clickPersonalDetails();
		Thread.sleep(3000);
	}

	@Given("the user updates the following details:")
	@StepMethod("the user updates the following details:")
	public void the_user_updates_the_following_details(DataTable dataTable) throws IOException {
		StepNameTracker.setStepName("the user updates the following details:");
		dataMap = dataTable.asMap(String.class, String.class);
		String firstname = myInfoPage.setFirstName(dataMap.get("FirstName"));
		Assert.assertEquals(firstname, dataMap.get("FirstName"));
		String middlename = myInfoPage.setMiddleName(dataMap.get("MiddleName"));
		Assert.assertEquals(middlename, dataMap.get("MiddleName"));
		String lastname = myInfoPage.setLastName(dataMap.get("LastName"));
		Assert.assertEquals(lastname, dataMap.get("LastName"));
		String employeeid = myInfoPage.setEmployeeId(dataMap.get("EmployeeId"));
		Assert.assertEquals(employeeid, dataMap.get("EmployeeId"));
		String otherid = myInfoPage.setOtherId(dataMap.get("OtherId"));
		Assert.assertEquals(otherid, dataMap.get("OtherId"));
		
	}

	@Given("the user clicks the first Save button")
	@StepMethod("the user clicks the first Save button")
	public void the_user_clicks_the_first_save_button() {
		StepNameTracker.setStepName("the user clicks the first Save button");
		myInfoPage.clickFirstSaveButton();
	}

	@Given("the user attach the file and add comment")
	@StepMethod("the user attach the file and add comment")
	public void the_user_attach_the_file_and_add_comment() {
		StepNameTracker.setStepName("the user attach the file and add comment");
	}

	@Given("the user clicks the second Save button")
	@StepMethod("the user clicks the second Save button")
	public void the_user_clicks_the_second_save_button() {
		StepNameTracker.setStepName("the user clicks the second Save button");
	}

	@Given("the user clicks the third Save button")
	@StepMethod("the user clicks the third Save button")
	public void the_user_clicks_the_third_save_button() {
		StepNameTracker.setStepName("the user clicks the third Save button");
	}

	@Given("the updated personal details should be displayed correctly on the personal details page")
	@StepMethod("the updated personal details should be displayed correctly on the personal details page")
	public void the_updated_personal_details_should_be_displayed_correctly_on_the_personal_details_page() {
		StepNameTracker
				.setStepName("the updated personal details should be displayed correctly on the personal details page");
	}

}
