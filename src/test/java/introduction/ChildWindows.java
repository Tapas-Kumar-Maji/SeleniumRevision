package introduction;

import java.time.Duration;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ChildWindows {
	private WebDriver driver;

	@BeforeClass
	private void before() {
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		this.driver = new ChromeDriver(options);
//		FirefoxOptions optionsFire = new FirefoxOptions();
//		optionsFire.addPreference("dom.webnotifications.enabled", false);
//		this.driver = new FirefoxDriver();

		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		this.driver.get("https://rahulshettyacademy.com/loginpagePractise/#");
	}

	@AfterClass
	private void after() throws InterruptedException {
		Thread.sleep(5000L);
		this.driver.quit();
	}

	@Test
	public void childTab() throws InterruptedException {
		String originalWindowHandle = driver.getWindowHandle();
		driver.findElement(By.cssSelector("a[class='blinkingText']")).click();

		Set<String> windowHandles = driver.getWindowHandles();
		String newTabWindowHandle = null;
		for (String str : windowHandles) {
			if (str != originalWindowHandle) {
				newTabWindowHandle = str;
			}
		}

		driver.switchTo().window(newTabWindowHandle);
		String email = driver.findElement(By.cssSelector("p[class='im-para red'] a")).getText().trim();
		System.out.println(email);

		driver.switchTo().window(originalWindowHandle);
		driver.findElement(By.cssSelector("input[id='username']")).sendKeys(email);
	}
}
