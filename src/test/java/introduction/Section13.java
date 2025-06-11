package introduction;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Section13 {

	private WebDriver getDriver() {
		this.killProcess("chromedriver");
//		this.killProcess("chrome");
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("start-maximized");
		options.setAcceptInsecureCerts(true);
		WebDriver driver = new ChromeDriver(options);
//		FirefoxOptions optionsFire = new FirefoxOptions();
//		optionsFire.addPreference("dom.webnotifications.enabled", false);
//		this.driver = new FirefoxDriver();

//		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		return driver;
	}

	private void quitDriver(WebDriver driver) {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}

	public void killProcess(String processName) {
		String os = System.getProperty("os.name").toLowerCase();
		String command = "";
		
		try {
			if(os.contains("win")) {
				command = "taskkill /F /IM " + processName + ".exe";
			}else if(os.contains("nix") || os.contains("nux") || os.contains("mac")){
				command = "pkill" + processName;
			}
			
			Runtime.getRuntime().exec(command);
			System.out.println("Killed Process : " + processName);
		} catch (IOException e) {
			System.err.println("Error killing process : " + processName + e.getMessage());
		}
	}

	private void printLinkStatus(String linkUrl, SoftAssert softAssert) {
		int responseStatusCode = 0;
		try {
			URL url = new URL(linkUrl);
			HttpURLConnection httpUrlConnection = null;
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setRequestMethod("HEAD");
			httpUrlConnection.connect();
			responseStatusCode = httpUrlConnection.getResponseCode();
		} catch (IOException e) {
		}

		if (responseStatusCode >= 200 && responseStatusCode < 400) {
			System.out.println("Link : " + linkUrl + " with status code : " + responseStatusCode + " is valid.");
		} else {
			System.out.println("Link : " + linkUrl + " with status code : " + responseStatusCode + " is broken.");
		}

		softAssert.assertTrue(responseStatusCode >= 200 && responseStatusCode < 400,
				"Link : " + linkUrl + " with status code : " + responseStatusCode + " is broken.");
	}

	@Test
	public void ssl() {
		WebDriver driver = this.getDriver();
		driver.get("https://expired.badssl.com/");
		System.out.println("Title : " + driver.getTitle());

		this.quitDriver(driver);
	}

	@Test
	public void screenShot() throws IOException {
		WebDriver driver = this.getDriver();
		driver.get("https://expired.badssl.com/");
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destination = new File("screenshots\\" + "badssl.png");
		FileHandler.copy(source, destination);

		this.quitDriver(driver);
	}

	@Test
	public void findBrokenLinks() {
		WebDriver driver = this.getDriver();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");

		SoftAssert softAssert = new SoftAssert();
		List<WebElement> links = driver.findElements(By.tagName("a"));
		for (WebElement link : links) {
			String url = link.getDomAttribute("href");
			this.printLinkStatus(url, softAssert);
		}
		softAssert.assertAll();

		this.quitDriver(driver);
	}

}
