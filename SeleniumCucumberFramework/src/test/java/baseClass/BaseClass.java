package baseClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
	public static WebDriver driver;
	public static Logger logger; // Log4j
	public static Properties prop;
	public static JavascriptExecutor js;
	public static WebDriverWait wait;

	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

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
			webDriver.set(new RemoteWebDriver(gridUrl, capabilities));
			// driver = new RemoteWebDriver(gridUrl, capabilities);
		}

		else if (execution_env.equalsIgnoreCase("local")) {
			// Switch statement to select the browser driver based on the input string
			switch (browser.toLowerCase()) {
			// Initialize ChromeDriver if "chrome" is specified
			case "chrome":
				webDriver.set(new ChromeDriver());
				break;
			// Initialize EdgeDriver if "edge" is specified
			case "edge":
				webDriver.set(new EdgeDriver());
				break;
			case "firefox":
				webDriver.set(new FirefoxDriver());
				break;
			default:
				throw new RuntimeException("Specified browser is not supported");
			}
		}

		driver = getDriver();
		// Delete all cookies to ensure a clean state before starting the tests
		driver.manage().deleteAllCookies();
		// Set an implicit wait time of 5 seconds for elements to be located
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Maximize the browser window
		driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver getDriver() throws IOException {
		return webDriver.get();
	}

	public static void quitDriver() {
		if (webDriver.get() != null) {
			webDriver.get().quit();
			webDriver.remove();
		}
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

	public static String captureScreenshot(String name) {
		// Create a timestamp using the current date and time in the format
		// "yyyyMMddhhmmss"
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		// Cast the WebDriver instance to TakesScreenshot to access the screenshot
		// //functionality

		TakesScreenshot ts = (TakesScreenshot) driver;

		// Capture the screenshot and store it in a temporary file (srcFile)
		File srcFile = ts.getScreenshotAs(OutputType.FILE);

		// Define the target file path using the project directory, a subfolder named
		// "screenshots", // the provided name, and the timestamp to ensure uniqueness

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + name + "_" + timeStamp + ".png";

		/*
		 * // Capture the full page screenshot using Shutterbug and save it to the
		 * specified path Shutterbug.shootPage(driver, Capture.FULL, true).withName(name
		 * + "_" + timeStamp) .save(System.getProperty("user.dir") + "\\screenshots");
		 */

		// Create a new file object for the target file path
		File targetFile = new File(targetFilePath);

		// Rename the temporary screenshot file to the target file path
		srcFile.renameTo(targetFile);

		// Return the path of the saved screenshot file
		return targetFilePath;
	}

	public static void waitForPageToLoad() throws IOException {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

		// Wait for JavaScript to load
		wait.until(
				driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
	}

	public static void waitforvisibilityOfElement(WebElement element) throws IOException {
		try {
			// Wait for the login page to be visible (example with WebDriverWait)
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(element));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void handleMultipleWindows() {
		Set<String> allWindows = driver.getWindowHandles();
	}

	public static void waitforElementToBeClicable(WebElement element) throws IOException {
		try {
			wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void clearInputFieldValueUsingBackspaceKeys(WebElement element) {
		String existingText = element.getAttribute("value");
		for (int i = 0; i < existingText.length(); i++) {
			element.sendKeys("\u0008"); // Unicode for backspace
		}
	}

	public static void scrollIntoViewElement(WebElement element) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
}
