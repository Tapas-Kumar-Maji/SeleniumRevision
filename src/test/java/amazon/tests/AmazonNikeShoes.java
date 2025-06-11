package amazon.tests;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AmazonNikeShoes {

	public static void main(String[] args) throws InterruptedException {
		/*
		 * search nike shoes in search box
		 * click on nike shoe for men
		 * click on to ith shoe
		 * type email on buy page  
		 */
		int index = 8;

		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.amazon.in/");
		
		// 1st page

		if (driver.findElements(By.cssSelector("div:has(+input#captchacharacters) div + div")).size() > 0) {
			driver.findElement(By.cssSelector("div:has(+input#captchacharacters) div + div")).click();
		}
		driver.findElement(By.cssSelector("input[id = 'twotabsearchtextbox']")).sendKeys("nike shoes");
		driver.findElement(By.xpath("//div[contains(@id, 'sac-suggestion-row') ]//span[text() = ' for woman']"))
				.click();
//		Thread.sleep(3000L);

		// 2nd page
		driver.findElements(
				By.cssSelector("div[role = 'listitem']:not([class *= 'a-box']) div[class *= 'product-image']"))
				.get(index - 1).click();
		int i = 0;
		for (String handle : driver.getWindowHandles()) {
			if (i != 0) {
				driver.switchTo().window(handle);
			}
			i++;
		}

		driver.findElement(By.cssSelector("input#buy-now-button")).click();
		driver.findElement(By.cssSelector("input[id *= 'email']")).sendKeys("tapasmaji908@gmail.com");

		Thread.sleep(5000L);
		driver.quit();
	}
}
