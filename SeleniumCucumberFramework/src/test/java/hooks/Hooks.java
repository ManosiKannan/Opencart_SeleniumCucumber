package hooks;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import annotations.StepMethod;
import baseClass.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.StepNameTracker;

public class Hooks {

	private WebDriver driver;
	private Properties prop;
	private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentSparkReporter sparkReporter;
	private String reportName;
	private static Map<String, String> stepDefinitionMap = new HashMap<>();

	@Before
	public void setup(Scenario scenario) throws IOException {
		try {
			if (extent == null) {
				populateStepDefinitionMap();
				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				reportName = "Test-Report_" + timeStamp + ".html";

				// Initialize the ExtentReports and SparkReporter
				sparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName);
				sparkReporter.config().setDocumentTitle("Automation Report");
				sparkReporter.config().setReportName("Automation Testing");
				sparkReporter.config().setTheme(Theme.DARK);

				extent = new ExtentReports();
				extent.attachReporter(sparkReporter);	
				extent.setSystemInfo("Environment", "QA");
				extent.setSystemInfo("User", System.getProperty("user.name"));
			}

			driver = BaseClass.initializeBrowser();
			//driver = BaseClass.getDriver();
			prop = BaseClass.getProperties();
			driver.get(prop.getProperty("appURL"));

			// Create a test entry for the scenario
			test = extent.createTest(scenario.getName());

		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize WebDriver", e);
		}
	}

	// Capture Screenshots for all the steps

	@AfterStep
	public void addScreenshot(Scenario scenario) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());

			String stepName = StepNameTracker.getStepName(); // Retrieve the step name
			String methodName = getStepMethodName(stepName);

			if (methodName != null) {
				String screenshotPath = BaseClass.captureScreenshot(scenario.getName());
				test.addScreenCaptureFromPath(screenshotPath);

				switch (scenario.getStatus()) {

				case FAILED:
					test.log(Status.FAIL, "Step failed: " + methodName);
					break;

				case PASSED:
					test.log(Status.PASS, "Step passed: " + methodName);
					break;

				case SKIPPED:
					test.log(Status.SKIP, "Step skipped: " + methodName);
					break;
				default:
					break;
				}
			} else {
				test.log(Status.WARNING, "No matching step definition found for step: " + stepName);
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "An error occurred while adding a screenshot: " + e.getMessage());
		}
	}

	// Capture Screenshot Only on Failure
	/*
	 * @AfterStep public void addScreenshot(Scenario scenario) { try { String
	 * stepName = StepNameTracker.getStepName(); // Retrieve the step name String
	 * methodName = getStepMethodName(stepName);
	 * 
	 * // Log step status switch (scenario.getStatus()) { case PASSED:
	 * test.log(Status.PASS, "Step passed: " + methodName); break; case FAILED: //
	 * Capture screenshot if the step fails TakesScreenshot ts = (TakesScreenshot)
	 * driver; byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
	 * scenario.attach(screenshot, "image/png", scenario.getName());
	 * 
	 * // Capture screenshot path for Extent report String screenshotPath =
	 * BaseClass.captureScreenshot(scenario.getName());
	 * test.addScreenCaptureFromPath(screenshotPath);
	 * 
	 * test.log(Status.FAIL, "Step failed: " + methodName); break; case SKIPPED:
	 * test.log(Status.SKIP, "Step skipped: " + methodName); break; default: break;
	 * } } catch (Exception e) { test.log(Status.FAIL,
	 * "An error occurred while processing step: " + e.getMessage()); } }
	 */

	private void populateStepDefinitionMap() {
		Reflections reflections = new Reflections("stepDefinitions", new MethodAnnotationsScanner());
		Set<Method> methods = reflections.getMethodsAnnotatedWith(StepMethod.class);

		for (Method method : methods) {
			StepMethod annotation = method.getAnnotation(StepMethod.class);
			stepDefinitionMap.put(annotation.value(), method.getName());
		}
	}

	private String getStepMethodName(String stepName) {
		String methodName = stepDefinitionMap.get(stepName);
		return methodName;
	}

	@After
	public void tearDown(Scenario scenario) {
		BaseClass.quitDriver();

		switch (scenario.getStatus()) {
		case PASSED:
			test.log(Status.PASS, "Scenario passed: " + scenario.getName());
			break;
		case FAILED:
			test.log(Status.FAIL, "Scenario failed: " + scenario.getName());
			test.log(Status.INFO, "Error: " + scenario.getStatus().toString());
			break;
		case SKIPPED:
			test.log(Status.SKIP, "Scenario skipped: " + scenario.getName());
			test.log(Status.INFO, "Status: " + scenario.getStatus().toString());
			break;
		default:
			break;
		}

		extent.flush();

		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + reportName;
		File extentReport = new File(pathOfExtentReport);

		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(extentReport.toURI());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
