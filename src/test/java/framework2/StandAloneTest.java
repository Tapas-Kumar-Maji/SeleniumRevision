package framework2;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2L));
		driver.get("https://rahulshettyacademy.com/client/");

		// Login Page
		driver.findElement(By.id("userEmail")).sendKeys("tapasmaji908@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("#1Tapasmaji");
		Thread.sleep(1000L);
		driver.findElement(By.cssSelector("input[id = 'login']")).click();

		// Products Page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000L));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[id = 'sidebar'] > p")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("toast-container"))));

		String product = "Banarsi Saree";
		List<WebElement> cards = driver.findElements(By.xpath("//div[@class = 'card']"));
		WebElement card = cards.stream()
				.filter(s -> s.findElement(By.tagName("h5")).getText().equalsIgnoreCase(product)).findFirst().orElse(null);
		Actions actions = new Actions(driver, Duration.ofMillis(100L));
		actions.scrollToElement(card.findElement(By.cssSelector("button:has(i[class *= 'fa-shopping-cart'])"))).build().perform();
		card.findElement(By.cssSelector("button:has(i[class *= 'fa-shopping-cart'])")).click();

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); // Selenium issue, with invisibilityOfElementLocated(By) method it was taking too much time.
		actions.moveByOffset(5, 5).build().perform();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("toast-container"))));
		WebElement cartBtn = driver.findElement(By.cssSelector("button[routerlink = '/dashboard/cart']"));
		actions.scrollToElement(cartBtn).build().perform();
		cartBtn.click();
		Thread.sleep(1000L);

		// Cart Page
		wait.until(ExpectedConditions.textToBe(By.cssSelector("div[class ^= 'heading'] h1"), "My Cart"));
		List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class = 'infoWrap'] //h3"));
		cartItems.stream().forEach(s -> System.out.println(s.getText()));
		Assert.assertTrue(cartItems.stream().anyMatch(s -> s.getText().equalsIgnoreCase(product)));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("toast-container")));
		driver.findElement(By.xpath("//li[@class = 'totalRow']/button")).click();
		Thread.sleep(1000L);
		
		// Payment Page
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div[class = 'payment'] > div[class = 'payment__title']"),
				"Payment Method"));
		// scroll to email. Fill email and country.Scroll to place order.
		actions.scrollToElement(driver.findElement(By.xpath("//div[@class = 'payment__shipping'] //a"))).build().perform();
		WebElement email = driver.findElement(By.cssSelector("div[class = 'user__name mt-5'] > input"));
		email.clear();
		email.sendKeys("ChapterMaster@gmail.com");
		driver.findElement(By.cssSelector("div[class = 'form-group'] > input")).sendKeys("Uni"); // Uni
		wait.until(ExpectedConditions.visibilityOfAllElements(
				driver.findElements(By.cssSelector("section[class *= 'ta-results'] > button"))));
		List<WebElement> countries = driver.findElements(By.cssSelector("section[class *= 'ta-results'] > button"));
		countries.stream().filter(country -> country.getText().trim().equalsIgnoreCase("United Arab Emirates")).limit(1)
				.forEach(country -> country.click());
		// click at the bottom of the element.
//		WebElement placeOrderBtn = driver.findElement(By.xpath("//div[@class = 'payment__shipping'] //a"));
//		int placeOrderX = placeOrderBtn.getRect().getX() + (placeOrderBtn.getRect().getWidth() / 2);
//		int placeOrderY = placeOrderBtn.getRect().getY() + placeOrderBtn.getRect().getHeight() - 100;
//		actions.moveToLocation(placeOrderX, placeOrderY).click().build().perform();
		driver.findElement(By.xpath("//div[@class = 'payment__shipping'] //a")).click();

		// Confirmation Page
		WebElement thankyou = driver.findElement(By.tagName("h1"));
		wait.until(ExpectedConditions.visibilityOf(thankyou));
		actions.scrollToElement(thankyou).build().perform();
		Assert.assertEquals(thankyou.getText().trim(), "Thankyou for the order.".toUpperCase());

		Thread.sleep(5000L);
		driver.quit();

	}
}
