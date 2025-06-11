package bigbasket.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import bigbasket.utilities.Utilities;

public class BaseTest extends Utilities {
	WebDriver driver = null;
	
	@BeforeClass
	public void initializeDriver() throws IOException {
		Logger.getLogger("").setLevel(Level.SEVERE);
		Properties properties = getConfigProperty();
		String browser = properties.getProperty("browser");

		if(browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
			driver = new ChromeDriver(options);
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("start-maximized");
			driver = new FirefoxDriver(firefoxOptions);
		}
		else if (browser.equalsIgnoreCase("edge")) {
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("start-maximized");
			driver = new EdgeDriver(edgeOptions);
		}
		
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
	}
	
	@AfterClass
	public void tearDown() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}
}
