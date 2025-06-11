package amazon.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class AamzonRandomTest {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.amazon.in/");
		Actions actions = new Actions(driver, Duration.ofMillis(200L));

		if (driver.findElements(By.cssSelector("div:has(+input[id = 'captchacharacters']) a")).size() > 0) {
			driver.findElement(By.cssSelector("div:has(+input[id = 'captchacharacters']) a")).click();
		}

		List<WebElement> headerMenus = driver
				.findElements(By.cssSelector("div[id = 'nav-xshop-container'] a[class *= 'nav-a']"));
		for (WebElement ele : headerMenus) {
			if (ele.getText().equals("Best Sellers")) {
				ele.click();
				break;
			}
		}

		List<WebElement> leftCol = driver.findElements(By.cssSelector("div[role = 'treeitem']"));
		for (WebElement ele : leftCol) {
			if (ele.getText().contains("Sports, Fitness")) {
				ele.click();
				break;
			}
		}

		int index = 46;
		List<WebElement> products = driver.findElements(By.cssSelector("div[id = 'gridItemRoot']"));
		while (index > products.size()) {
			actions.scrollToElement(products.get(products.size() - 1)).build().perform();
			products = driver.findElements(By.cssSelector("div[id = 'gridItemRoot']"));
		}

		actions.click(products.get(index - 1)).build().perform();
		
		// adding to cart
		String crtlEnter = Keys.chord(Keys.CONTROL, Keys.ENTER);
		driver.findElement(By.cssSelector("input[id = 'add-to-cart-button']")).sendKeys(crtlEnter);

		// Frequently brought
		List<WebElement> freqs = driver.findElements(By.cssSelector("div[aria-labelledby ^= 'Product ']"));
		int indexfreq = 3;
		if (indexfreq > freqs.size()) {
			indexfreq = 1;
		}
		freqs.get(indexfreq - 1).click();
		driver.findElement(By.cssSelector("input[id = 'add-to-cart-button']")).click();
		
		if (driver.findElements(By.cssSelector("span[id = 'attachSiNoCoverage']")).size() > 0) {
			driver.findElement(By.cssSelector("span[id = 'attachSiNoCoverage']"));
		}

		driver.findElement(By.cssSelector("span[id = 'sw-gtc'] a[href *= 'cart']")).click();

		// Prices
		List<WebElement> prices = driver
				.findElements(By.cssSelector("div[class *= 'sc-badge-price-to-pay'] span:nth-of-type(2)"));
		for (WebElement ele : prices) {
			System.out.println("Price : " + ele.getText());
		}
	}
}
