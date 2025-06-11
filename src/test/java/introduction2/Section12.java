package introduction2;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Section12 {
	WebDriver driver = null;

	@BeforeClass
	public void initializeDriver() {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
	}
	
	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}
	
	@Test
	public void scrollWebTable() {
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		((JavascriptExecutor) driver).executeScript(
				"document.querySelector('div[class = \"tableFixHead\"] tbody tr:nth-child(6)').scrollIntoView(true)");
	}

	@Test(dependsOnMethods = { "scrollWebTable" })
	public void handleGrids() {
		List<WebElement> amountList = driver
				.findElements(By.cssSelector("div[class = 'tableFixHead'] td:nth-child(4)"));
		int actualSum = 0;
		for (WebElement ele : amountList) {
			actualSum += Integer.parseInt(ele.getText());
		}
		System.out.println("actual sum : " + actualSum);
		String expectedSum = driver.findElement(By.cssSelector("div[class = 'tableFixHead'] ~ div")).getText()
				.split(":")[1].trim();
		Assert.assertEquals(actualSum, Integer.parseInt(expectedSum));
	}

}
