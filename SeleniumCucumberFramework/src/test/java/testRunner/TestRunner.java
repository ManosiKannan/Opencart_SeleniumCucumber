package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { ".//feature/" },
		// features = {"@target/rerun.txt"},
		glue = { "stepDefinitions", "hooks" }, plugin = { "pretty", "html:reports/myreport.html",
				"rerun:target/rerun.txt" }, dryRun = false, // checks mapping between scenario steps and step definition
															// methods
		monochrome = true, // to avoid junk characters in output
		publish = true, // to publish report in cucumber server
		tags="@Login"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
