package introduction;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.JavaScriptActions;

public class Section11 {

	private WebDriver getDriver() {
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
//		FirefoxOptions optionsFire = new FirefoxOptions();
//		optionsFire.addPreference("dom.webnotifications.enabled", false);
//		this.driver = new FirefoxDriver();

		driver.manage().window().maximize();
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

	@Test
	public void countLinks() {
		WebDriver driver = this.getDriver();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		System.out.println("Number of links in the webpage : " + driver.findElements(By.tagName("a")).size());
		System.out.println("Number of links in footer : "
				+ driver.findElements(By.cssSelector("div[id='gf-BIG'] tbody a")).size());
		System.out.println("\n\n");

		List<WebElement> linksColumn1 = driver.findElements(By.xpath("(//div[@id='gf-BIG'] //ul)[1] //a"));
		String controlEnter = Keys.chord(Keys.CONTROL, Keys.ENTER);
//		Actions actions = new Actions(driver, Duration.ofMillis(100L));
		for (int i = 1; i < linksColumn1.size(); i++) {
			linksColumn1.get(i).sendKeys(controlEnter);
//			actions.keyDown(Keys.LEFT_CONTROL).click(linksColumn1.get(i)).keyUp(Keys.LEFT_CONTROL).perform();			
		}
		
		// Get titles of the child windows.
		String originalWindowHandle = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		for (String handle : windowHandles) {
			if (!handle.equals(originalWindowHandle)) {
				driver.switchTo().window(handle);
				System.out.println(driver.getTitle());
			}
		}

		this.quitDriver(driver);
	}

	@Test
	public void calendar() {
		WebDriver driver = this.getDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		driver.findElement(By.cssSelector("button[class *= 'react-date-picker__calendar-button']")).click();

		// given
		String year = "2026", month = "10", day = "29";
		String expected = year + "-" + month + "-" + day;

		// year.
		driver.findElement(By.cssSelector("button[class='react-calendar__navigation__label']")).click();
		driver.findElement(By.cssSelector("button[class='react-calendar__navigation__label']")).click();
		List<WebElement> years = driver
				.findElements(By.cssSelector("div[class='react-calendar__decade-view__years'] > button"));
		for (WebElement yr : years) {
			if (yr.getText().trim().equals(year)) {
				yr.click();
				break;
			}
		}

		// month.
		List<WebElement> months = driver
				.findElements(By.cssSelector("div[class='react-calendar__year-view__months'] > button"));
		months.get(Integer.parseInt(month) - 1).click();

		// day.
		List<WebElement> days = driver.findElements(By.xpath(
				"//div[@class='react-calendar__month-view__days']  //button[not(contains(@class, 'neighboringMonth'))]"));
		for (WebElement d : days) {
			if (d.getText().trim().equals(day)) {
				d.click();
				break;
			}
		}

		String actual = driver
				.findElement(By.cssSelector("div[class='react-date-picker__inputGroup'] > input[name = 'date']"))
				.getDomAttribute("value");
		Assert.assertEquals(actual, expected);

		this.quitDriver(driver);
	}

	@Test
	public void scrolling() {
		WebDriver driver = this.getDriver();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		JavaScriptActions jsActions = new JavaScriptActions(driver);

		// scrolling.
		WebElement table = driver.findElement(By.cssSelector("div[class='tableFixHead']"));
		jsActions.scrollIntoView(table);
		jsActions.scrollIntoView(table.findElement(By.xpath("//td[contains(text(), 'Smith')]")));

		// validating amount.
		List<WebElement> amounts = driver.findElements(By.xpath("//div[@class='tableFixHead'] //td[4]"));
		int sum = 0;
		for (WebElement ele : amounts) {
			sum += Integer.parseInt(ele.getText().trim());
		}
		
		System.out.println(sum);
		String expected = driver.findElement(By.cssSelector("div[class='totalAmount']")).getText().split(":")[1].trim();
		Assert.assertEquals(sum, Integer.parseInt(expected));

		this.quitDriver(driver);
	}
}
