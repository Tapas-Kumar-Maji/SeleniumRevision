package introduction;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AmazonTest {
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
		this.driver.get("https://www.amazon.in/");
	}

	@AfterClass
	private void after() throws InterruptedException {
		Thread.sleep(5000L);
		this.driver.quit();
	}

	@Test
	public void mouseActions() throws InterruptedException {
		Actions actions = new Actions(driver, Duration.ofMillis(100L));
		actions.click(driver.findElement(By.cssSelector("input[id='twotabsearchtextbox']")))
				.keyDown(Keys.SHIFT)
				.sendKeys("hello Tapas")
				.keyUp(Keys.SHIFT)
				.perform();
		
		actions.click(driver.findElement(By.cssSelector("input[id='twotabsearchtextbox']")))
				.sendKeys(" hotweels")
				.doubleClick()
				.perform();

		Thread.sleep(500L);
		actions.moveToElement(driver.findElement(By.cssSelector("a[id='nav-link-accountList']"))).perform();
		actions.contextClick().perform();
	}

}
