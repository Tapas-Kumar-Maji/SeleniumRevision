package amazon.tests;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AmazonNetomiFlow {

	public static void main(String[] args) {
		// Setup boilerplate.
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.amazon.in/");

		// Handling captcha
		if (driver.findElements(By.cssSelector("div[class $= 'a-text-right'] > a")).size() > 0) {
			driver.findElement(By.cssSelector("div[class $= 'a-text-right'] > a")).click();
		}
		System.out.println("Logged in successfully");

		// 1st page
		String productName = "Samsung mobiles"; // working as expected
		productName = "gifts";
		int index = 4; // index working as expected
		driver.findElement(By.cssSelector("input[id = 'twotabsearchtextbox']")).sendKeys(productName, Keys.ENTER);
		List<WebElement> searchResultProducts = driver
				.findElements(By.cssSelector("div[role = 'listitem'][data-cel-widget *= 'search_result']"));
		searchResultProducts.get(index - 1).findElement(By.tagName("h2")).click();
		System.out.println("Product found successfully");

		// 2nd page
		Iterator<String> windowHandles = driver.getWindowHandles().iterator();
		windowHandles.next();
		driver.switchTo().window(windowHandles.next());
		String productPrice = driver.findElement(By
				.xpath("//span[@id = 'taxInclusiveMessage']/preceding-sibling::span //span[@class = 'a-price-whole']"))
				.getText();
		System.out.println("Price : " + productPrice);

		// Handling add to Cart Button
		if (driver.findElement(By.xpath("(//input[@id = 'add-to-cart-button'])")).isDisplayed()) {
			driver.findElement(By.xpath("(//input[@id = 'add-to-cart-button'])")).click();
		}
		else if (driver.findElement(By.xpath("(//input[@id = 'add-to-cart-button'])[2]")).isDisplayed()) {
			driver.findElement(By.xpath("(//input[@id = 'add-to-cart-button'])[2]")).click();
		}
		else if(driver.findElements(By.cssSelector("a[title = 'Add to Cart']")).size() > 0) {
			driver.findElement(By.cssSelector("a[title = 'Add to Cart']")).click();
		}

		// Handling checkout button
		if (driver.findElements(By.cssSelector("span[id = 'attach-sidesheet-checkout-button']")).size() > 0) {
			driver.findElement(By.cssSelector("span[id = 'attach-sidesheet-checkout-button']")).click();
		} 
		else if (driver.findElements(By.cssSelector("input[value *= 'Proceed to checkout']")).size() > 0) {
			driver.findElement(By.cssSelector("input[value *= 'Proceed to checkout']")).click();
		}

		// last page
		driver.findElement(By.xpath("//input[@name = 'email' or type = 'email']")).sendKeys("tapasmaji908@gmail.com");
		System.out.println("SUCCESS HURRAY!!");
	}
}
