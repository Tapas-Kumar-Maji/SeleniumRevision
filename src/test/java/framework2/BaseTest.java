package framework2;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import framework2.utilities.Utilities;

public class BaseTest extends Utilities {
	private WebDriver driver = null;

	@BeforeMethod(alwaysRun = true)
	public WebDriver initializeDriver() throws IOException {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Config.properties");
		properties.load(fileInputStream);
		fileInputStream.close();

		String browserName = properties.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
			driver = new ChromeDriver(options);
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("start-maximized");
			driver = new FirefoxDriver(firefoxOptions);
		} 
		else if (browserName.equalsIgnoreCase("edge")) {

		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2L));
		driver.get("https://rahulshettyacademy.com/client/");
		return driver;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() throws InterruptedException {
//		Thread.sleep(5000L);
		driver.quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

}