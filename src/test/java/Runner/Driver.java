package Runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
/*features = "src/test/java/Features"
,glue= {"seleniumgluecode"}
,plugin = { "pretty", "html:target/htmlreports" }*/
        features ="src/test/java/features"
        ,glue= "glueCode",
        //plugin = { "com.cucumber.listener.ExtentCucumberFormatter:Reports/Execution Report.html"}, 
        tags = {"@PWAPOC"},
        monochrome = true
)

public class Driver {
	@AfterClass
    public static void writeExtentReport() {
       // Reporter.loadXMLConfig(new File("config/report.xml"));
	}
}