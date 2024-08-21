package factory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {
	public static WebDriver driver;
	public static Logger logger; // Log4j
	public static Properties prop;
	public static JavascriptExecutor js;

	public static WebDriver initializeBrowser() throws IOException, URISyntaxException {

		prop = getProperties();
		String browser = prop.getProperty("browser").toLowerCase();
		String operatingSystem = prop.getProperty("operatingSystem").toLowerCase();
		String execution_env = prop.getProperty("execution_env");

		if (execution_env.equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			switch (operatingSystem) {
			case "windows":
				capabilities.setPlatform(Platform.WIN11);
				break;
			case "linux":
				capabilities.setPlatform(Platform.LINUX);
				break;
			default:
				System.out.println("No matching Operating System");
				return null;
			}

			switch (browser.toLowerCase()) {
			// Initialize ChromeDriver if "chrome" is specified
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			// Initialize EdgeDriver if "edge" is specified
			case "edge":
				capabilities.setBrowserName("edge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			default:
				throw new RuntimeException("Specified browser is not supported");
			}
			URI gridUri = new URI("http://192.168.0.25:4444/wd/hub");
			URL gridUrl = gridUri.toURL();
			driver = new RemoteWebDriver(gridUrl, capabilities);
		}

		else if (execution_env.equalsIgnoreCase("local")) {
			// Switch statement to select the browser driver based on the input string
			switch (browser.toLowerCase()) {
			// Initialize ChromeDriver if "chrome" is specified
			case "chrome":
				driver = new ChromeDriver();
				break;
			// Initialize EdgeDriver if "edge" is specified
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				throw new RuntimeException("Specified browser is not supported");
			}
		}
		// Delete all cookies to ensure a clean state before starting the tests
		driver.manage().deleteAllCookies();
		// Set an implicit wait time of 5 seconds for elements to be located
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		// Maximize the browser window
		driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver getDriver() throws IOException {
			return driver;
	}

	public static Properties getProperties() throws IOException {
		// Load configuration properties from a file located at the specified path

		FileReader file = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
		prop = new Properties();
		prop.load(file);
		return prop;
	}

	public static Logger getLogger() {
		logger = LogManager.getLogger();
		return logger;

	}

	/**
	 * Generates a random string of 5 alphabetic characters.
	 * 
	 * @return A random alphabetic string.
	 */
	public static String randomString() {
		String generatedRandomString = RandomStringUtils.randomAlphabetic(5);
		return generatedRandomString;
	}

	public static String randomNumeric() {
		String generatedRandomNumber = RandomStringUtils.randomNumeric(10);
		return generatedRandomNumber;
	}

	/**
	 * Generates a random alphanumeric string with 6 alphabetic characters followed
	 * by 3 numeric characters, separated by an "@" symbol.
	 *
	 * @return A random alphanumeric string in the format: "letters@numbers".
	 */
	public static String randomAlphaNumeric() {
		String generatedRandomString = RandomStringUtils.randomAlphabetic(6);
		String generatedRandomNumber = RandomStringUtils.randomNumeric(3);
		String generatedRandomAlphaNumberic = (generatedRandomString + "@" + generatedRandomNumber);
		return generatedRandomAlphaNumberic;
	}

}
