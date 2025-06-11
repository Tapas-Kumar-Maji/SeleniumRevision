package introduction2;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GreenKart {

	/*
	 * data provider sends an array
	 * Convert array to HashSet for O(1) search time.
	 * Search if current element is present in hashset
	 * Click add to cart button if present.
	 * create counter and put break;
	 * use substring method in Java to extract the vegetable name.
	 * don't depend upon text when writing locators, bucause text can change.
	 */

	WebDriver driver;

	@BeforeClass
	public void initializeDriver() {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		this.driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
	}

	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		this.driver.quit();
	}

	@Test(dataProvider = "data")
	public void addToCartPage(Set<String> vegetables) throws InterruptedException {
		for (String veg : vegetables) {
			try {
			WebElement element = driver.findElement(By.xpath("//div[@class = 'product'][h4[contains(text(), '" + veg
					+ "')]]/div[@class = 'product-action']/button"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} catch (Exception e) {
				System.out.println(veg + " was not found.");
			}
		}

		driver.findElement(By.cssSelector("a[class ^= 'cart'] > img")).click();
		driver.findElement(By.cssSelector("div[class = 'cart-preview active']  button")).click();
	}

	@Test
	public void checkoutPage() throws InterruptedException {
		driver.findElement(By.cssSelector("input:has(+ button[class = 'promoBtn'])")).sendKeys("rahulshettyacademy");
		driver.findElement(By.cssSelector("button[class = 'promoBtn']")).click();
		Thread.sleep(7000L);
		String isPromoCodeApplied = driver
				.findElement(By.cssSelector("button[class = 'promoBtn'] ~ span[class ^= 'promo']")).getText();
		Assert.assertEquals(isPromoCodeApplied, "Code applied ..!");
		driver.findElement(By.cssSelector("span[class = 'discountAmt'] ~ button")).click();
	}

	@DataProvider
	public Object[] data() {
		String[] vegetables = { "Brocolli", "Beans", "Carrot", "Pumpkin", "Walnuts", "Cauliflower", "Apple", "Capsicum",
				"Musk Melon" };
		Set<String> hashSet = new HashSet<>();
		for (String str : vegetables) {
			hashSet.add(str);
		}
		return new Object[] { hashSet };
	}
}
