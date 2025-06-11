package introduction2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This is assessment for a company.
 * Later I converted this Java code to Python using chatGPT.
 */
public class Assessment_Python {
	WebDriver driver = null;

	@BeforeClass
	public void initializeDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		driver.get("https://www.lambdatest.com/selenium-playground/table-sort-search-demo");
	}

	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	@Test
	public void searchBoxFunctionality() throws InterruptedException {
		// checking total entries
		int total_entries = 0;
		for (int i = 1; i <= 3; i++) {
			driver.findElement(By.cssSelector("span > a[class *= 'paginate_button']:nth-child(" + i + ")")).click();
			total_entries += driver.findElements(By.cssSelector("table[id='example'] tbody tr")).size();
			System.out.println("total entries : " + total_entries);
		}

		// checking 5 entries for new york
		driver.findElement(By.cssSelector("div[id = 'example_filter'] input")).sendKeys("New York");
		int entries = driver.findElements(By.cssSelector("table[id = 'example'] tbody tr")).size();
		System.out.println(entries);
		Assert.assertEquals(entries, 5);

		String totalEntries = driver.findElement(By.cssSelector("div[id = 'example_info']")).getText();
		totalEntries = totalEntries.substring(totalEntries.indexOf("from") + 4, totalEntries.indexOf("total"))
				.trim();
		System.out.println(totalEntries);
		Assert.assertEquals(Integer.parseInt(totalEntries), 24);
	}
}

