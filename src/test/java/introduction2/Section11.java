package introduction2;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Section11 {
	WebDriver driver = null;

	@BeforeClass
	public void initializeDriver() throws InterruptedException {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
	}

	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	@Test(enabled = false)
	public void countLinks() {
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		System.out.println("Total <a> tags in the webpage : " + driver.findElements(By.tagName("a")).size());
		WebElement footer = driver.findElement(By.cssSelector("div[id = 'gf-BIG']"));
		System.out.println("Total <a> tags in footer section : " + footer.findElements(By.tagName("a")).size());
		WebElement discount = footer.findElement(By.cssSelector("td:nth-child(1)"));
		System.out.println("Total <a> tags in first column : " + discount.findElements(By.tagName("a")).size());

		// click links.
		for (WebElement ele : discount.findElements(By.tagName("a"))) {
			String ctrlEnter = Keys.chord(Keys.CONTROL, Keys.ENTER);
			ele.sendKeys(ctrlEnter);
		}

		this.printTitles();
		driver.findElement(By.cssSelector("input[value = 'radio1']")).click();
	}

	private void printTitles() {
		String originalWindowHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalWindowHandle)) {
				driver.switchTo().window(handle);
				System.out.println("Title : " + driver.getTitle());
				driver.close();
			}
		}
		driver.switchTo().window(originalWindowHandle);
	}

	@Test
	public void calendarUI() throws InterruptedException {
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		int day = 13;
		String month = "12";
		String year = "2022";

		driver.findElement(By.cssSelector("button[class *= 'calendar-button'] svg")).click();
		driver.findElement(By.cssSelector("span[class *= 'navigation__label']")).click();
		driver.findElement(By.cssSelector("span[class *= 'navigation__label']")).click();
		driver.findElement(By.xpath("//div[contains(@class, 'decade-view')]/button[text() = '" + year + "']")).click();
		driver.findElement(By.cssSelector("button[class *= 'view__months']:nth-child(" + month + ")")).click();
		driver.findElements(By.cssSelector("div[class *= 'view__days'] button:not([class *= 'neighboringMonth'])"))
				.get(day - 1).click();

		Thread.sleep(3000L);
	}

}
