package netomi_interview;

import java.time.Duration;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * This is Nethomi L2 round, 
 * Automating a flow in Amazon.in
 */
public class Interview {

	@Test
	public void test() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://amazon.in");
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
//		wait.until(ExpectedConditions
//				.visibilityOf(driver.findElement(By.cssSelector("input[id = 'twotabsearchtextbox']"))));
		Thread.sleep(10000L);
		String productName = "Samsung mobiles";
//		productName = "TP Link router";
		driver.findElement(By.cssSelector("input[id = 'twotabsearchtextbox']")).sendKeys(productName); // "Samsung mobiles"
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ENTER).build().perform();

		int index = 3;
		WebElement mobile = driver.findElement(
				By.xpath("(//div[@class = 'a-section'])[" + ++index + "] //div[@data-cy = 'title-recipe']/a"));
		//wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
		wait.until(ExpectedConditions.visibilityOf(mobile));
		//scroll
		actions.scrollToElement(mobile).build().perform();
		mobile.click();

		// span[@id = 'taxInclusiveMessage']/preceding-sibling::span
		Iterator<String> windowHandles = driver.getWindowHandles().iterator();
		windowHandles.next();
		driver.switchTo().window(windowHandles.next());
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(
				By.xpath("(//span[@id = 'taxInclusiveMessage']/preceding-sibling::span)[3]/span[2]/span[2]"))));
		WebElement ele = driver.findElement(
				By.xpath("(//span[@id = 'taxInclusiveMessage']/preceding-sibling::span)[3]/span[2]/span[2]"));
		System.out.println(ele.getText());

		WebElement addToCart = driver.findElement(By.xpath("(//input[@id = 'add-to-cart-button'])[2]"));
		actions.scrollToElement(addToCart).build().perform();
		addToCart.click();

		// wait
		Thread.sleep(5000L);
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.cssSelector("span[id = 'attach-sidesheet-checkout-button']"))))
				.click();

		// change window handle
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[id *= 'ap_email']"))))
				.sendKeys("tapasmaji908@gmail.com");

		Thread.sleep(5000L);
		driver.quit();
	}
}
