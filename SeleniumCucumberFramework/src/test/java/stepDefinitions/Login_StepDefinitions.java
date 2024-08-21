package stepDefinitions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import factory.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.AccountHomePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;

public class Login_StepDefinitions {
	WebDriver driver;
	HomePage homePage;
	LoginPage loginPage;
	AccountHomePage accountHomePage;

	List<HashMap<String, String>> dataMap;

	@Given("the user navigate to Login Page")
	public void the_user_navigate_to_login_page() throws IOException {
		BaseClass.getLogger().info("Go to My Account --> Click on Login");
		homePage = new HomePage(BaseClass.getDriver());
		homePage.clickMyAccount();
		homePage.clickLogin();
	}

	@Given("the user enters email as {string} password as {string}")
	public void the_user_enters_email_as_password_as(String email, String password) throws IOException {
		BaseClass.getLogger().info("Entering email and password");
		loginPage = new LoginPage(BaseClass.getDriver());
		loginPage.setEmailAddress(email);
		loginPage.setPassword(password);
	}

	@When("the user clicks on Login button")
	public void the_user_clicks_on_login_button() throws IOException {
		BaseClass.getLogger().info("Click on Login Button");
		loginPage = new LoginPage(BaseClass.getDriver());
		loginPage.clickLogin();
	}

	@Then("the user should be redirected to the My Account Page")
	public void the_user_should_be_redirected_to_the_my_account_page() throws IOException {
		BaseClass.getLogger().info("Navigate to My Account Page");
		accountHomePage = new AccountHomePage(BaseClass.getDriver());
		boolean myAccount = accountHomePage.isMyAccountDisplayed();
		Assert.assertEquals(myAccount, true);
	}

}
