package introduction;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SpiceJetTest {
	private WebDriver driver;

	@BeforeClass
	public void before() {
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		this.driver = new ChromeDriver(options);

//		FirefoxOptions optionsFire = new FirefoxOptions();
//		optionsFire.addPreference("dom.webnotifications.enabled", false);
//		this.driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		driver.get("https://www.spicejet.com/");
	}

	@AfterClass
	public void after() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	@Test
	public void destination() throws InterruptedException {
		driver.findElement(By.cssSelector("div[data-testid='to-testID-origin']")).click();
		List<WebElement> fromList = driver.findElements(By.cssSelector("div[class='css-76zvg2 r-1xedbs3 r-ubezar']"));
		for (WebElement ele : fromList) {
			if (ele.getText().equalsIgnoreCase("RDP")) {
				ele.click();
				break;
			}
		}

		List<WebElement> toList = driver.findElements(By.cssSelector("div[class='css-76zvg2 r-1xedbs3 r-ubezar']"));
		for (WebElement ele : toList) {
			if (ele.getText().equalsIgnoreCase("GOI")) {
				ele.click();
				break;
			}
		}
	}

	@Test(dependsOnMethods = { "destination" })
	public void date() {
		LocalDate currentDate = LocalDate.now();
		int day = currentDate.getDayOfMonth();
		WebElement date = driver.findElement(By.xpath("(//div[@data-testid='undefined-calendar-day-" + day + "'])[1]"));
		Assert.assertEquals(date.getText(), day + "");
		date.click();
	}

	@Test(dependsOnMethods = { "date" })
	public void others() throws InterruptedException {
		// Passengers.
		driver.findElement(By.cssSelector("div[data-testid='home-page-travellers']")).click();
		for (int i = 0; i < 2; i++) {
			driver.findElement(By.cssSelector("div[data-testid='Children-testID-plus-one-cta']")).click();
		}
		for (int i = 0; i < 1; i++) {
			driver.findElement(By.cssSelector("div[data-testid=\"Infant-testID-plus-one-cta\"]")).click();
		}
		driver.findElement(By.cssSelector("div[data-testid=\"home-page-travellers-done-cta\"]")).click();
		Thread.sleep(1000L);

		// Currency
		driver.findElement(By.xpath("//div[@class='css-1dbjc4n r-18u37iz r-19h5ruw r-184en5c']/div[2]")).click();
		List<WebElement> currency = driver.findElements(
				By.xpath("//div[@class='css-1dbjc4n']/div/div[@class='css-76zvg2 r-homxoj r-ubezar']"));
		for (WebElement ele : currency) {
			if (ele.getText().equalsIgnoreCase("LKR")) {
				ele.click();
				break;
			}
		}

		// Options
		List<WebElement> options = driver.findElements(
				By.xpath("//div[@class='css-1dbjc4n r-18u37iz r-1w6e6rj'] //div[contains(@class, 'r-cqee49')]"));
		options.get(1).click();
		driver.findElement(By.cssSelector("div[data-testid='home-page-flight-cta']")).click();
	}

}
