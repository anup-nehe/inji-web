package runnerfiles;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;
import org.testng.TestNG;
import utils.DependencyInjector;

import java.util.Collections;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/featurefiles/"},
		dryRun = false,
		glue = {"stepdefinitions", "utils"},
		snippets = SnippetType.CAMELCASE,
		monochrome = true,
		plugin = {"pretty",
				"html:reports",
				"html:target/cucumber.html", "json:target/cucumber.json",
				"summary", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)

public class Runner extends AbstractTestNGCucumberTests {

	public static void main(String[] args) {
		TestNG testng = new TestNG();
		testng.setTestSuites(Collections.singletonList(System.getProperty("user.dir")+"\\TestNg.xml"));

		System.out.println( "path od the xml" +System.getProperty("user.dir")+"\\TestNg.xml");
		testng.run();
	}
}
