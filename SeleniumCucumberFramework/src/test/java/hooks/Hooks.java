package hooks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.assertthat.selenium_shutterbug.core.Shutterbug;

import factory.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	WebDriver driver;
	Properties prop;

	@Before
	public void setup() throws IOException, URISyntaxException {
		driver = BaseClass.initializeBrowser();
		prop = BaseClass.getProperties();
		driver.get(prop.getProperty("appURL"));
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@AfterStep
	public void addScreenshot(Scenario scenario) {

		if (scenario.isFailed()) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			byte[] screenshots = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshots, "image/png", scenario.getName());
		}
	}

}
