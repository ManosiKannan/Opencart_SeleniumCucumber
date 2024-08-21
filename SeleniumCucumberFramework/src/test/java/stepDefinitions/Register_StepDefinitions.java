package stepDefinitions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import factory.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.AccountHomePage;
import pageObjects.AccountRegisterPage;
import pageObjects.ConfirmationPage;
import pageObjects.HomePage;
import utilities.DataReader;

public class Register_StepDefinitions {

	HomePage homePage;
	AccountRegisterPage accountRegister;
	AccountHomePage accountHome;
	ConfirmationPage confirmPage;
	List<HashMap<String, String>> dataMap;

	@Given("the user navigate to Register Page")
	public void the_user_navigate_to_register_page() throws IOException {
		homePage = new HomePage(BaseClass.getDriver());
		homePage.clickMyAccount();
		BaseClass.getLogger().info("Home page is loaded");
		homePage.clickRegister();
	}

	@Given("the user enters the following details")
	public void the_user_enters_the_following_details(DataTable dataTable) throws IOException {

		Map<String, String> mapData = dataTable.asMap(String.class, String.class);
		accountRegister = new AccountRegisterPage(BaseClass.getDriver());
		BaseClass.getLogger().info("User navigate to Registration Page");
		accountRegister.setFirstName(mapData.get("firstName"));
		accountRegister.setLastName(mapData.get("lastName"));
		accountRegister.setEmail(BaseClass.randomString() + "@gmail.com");
		accountRegister.setPhone(mapData.get("telePhone"));
		String password = BaseClass.randomAlphaNumeric();
		accountRegister.setPassword(password);
		accountRegister.setConfirmPassword(password);
		BaseClass.getLogger().info("User entered all the required details");
	}

	@Given("the user enters first name as {string} and last name as {string}")
	public void the_user_enters_first_name_and_last_name(String firstName, String lastName) throws IOException {
		accountRegister = new AccountRegisterPage(BaseClass.getDriver());
		BaseClass.getLogger().info("User navigate to Registration Page");
		accountRegister.setFirstName(firstName);
		accountRegister.setLastName(lastName);
		accountRegister.setEmail(BaseClass.randomString() + "@gmail.com");
		accountRegister.setPhone(BaseClass.randomNumeric());
		String password = BaseClass.randomAlphaNumeric();
		accountRegister.setPassword(password);
		accountRegister.setConfirmPassword(password);
		BaseClass.getLogger().info("User entered all the required details");
	}

	@Given("the user enters first name and last name with excel row {string}")
	public void the_user_enters_first_name_and_last_name_with_excel_row(String rowIndex) throws IOException {

		try {
			dataMap = DataReader.data(System.getProperty("user.dir") + "\\testData\\userRegistrationData.xlsx",
					"RegistrationData");
		} catch (Exception e) {
			e.printStackTrace();
		}

		int index = Integer.parseInt(rowIndex)-1;
		System.out.println(index);
		String firstName = dataMap.get(index).get("firstName");
		String lastName = dataMap.get(index).get("lastName");

		accountRegister = new AccountRegisterPage(BaseClass.getDriver());
		BaseClass.getLogger().info("User navigate to Registration Page");
		accountRegister.setFirstName(firstName);
		accountRegister.setLastName(lastName);
		accountRegister.setEmail(BaseClass.randomString() + "@gmail.com");
		accountRegister.setPhone(BaseClass.randomNumeric());
		String password = BaseClass.randomAlphaNumeric();
		accountRegister.setPassword(password);
		accountRegister.setConfirmPassword(password);
		BaseClass.getLogger().info("User entered all the required details");
	}

	@Given("the user selects Privacy Policy")
	public void the_user_submit_the_details() {
		accountRegister.clickAgree();
		BaseClass.getLogger().info("User selects Privacy Policy");
	}

	@When("the user clicks on Continue button")
	public void the_user_clicks_on_Continue_button() {
		accountRegister.clickContinue();
		BaseClass.getLogger().info("User submits the registration details");
	}

	@Then("the user should able to the see the confirmation text")
	public void the_user_should_able_to_the_see_the_confirmation_text() throws IOException {
		confirmPage = new ConfirmationPage(BaseClass.getDriver());
		boolean myAccount = confirmPage.isConfirmationMessageDisplayed();
		Assert.assertEquals(myAccount, true);
		BaseClass.getLogger().info("User registration is successful");
	}
}
