package introduction;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GreenKart {
	private WebDriver driver;
	private WebDriverWait wait;

	@BeforeClass
	public void before() {
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		this.driver = new ChromeDriver(options);

//		FirefoxOptions optionsFire = new FirefoxOptions();
//		optionsFire.addPreference("dom.webnotifications.enabled", false);
//		this.driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500L));
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5L));
	}

	@AfterClass
	public void after() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	@Test(dataProvider = "productsToAdd")
	public void addProduct(List<String> productsToBeAdded) {
		List<WebElement> productsAvailable = driver.findElements(By.cssSelector("div[class='product']"));
		for (int i = 0; i < productsAvailable.size() && !productsToBeAdded.isEmpty(); i++) {
			String name = productsAvailable.get(i).findElement(By.tagName("h4")).getText();
			name = name.split(" - ")[0].trim();
			if (productsToBeAdded.contains(name)) {
				productsAvailable.get(i).findElement(By.tagName("button")).click();
				productsToBeAdded.remove(name);
			}
		}
		System.out.println("Products not added : " + productsToBeAdded);
	}

	@DataProvider
	public Object[][] productsToAdd() {
		String[] products = new String[] { "coffee", "Cucumber", "Musk Melon", "Nuts Mixture", "Water Melon", "bike",
				"Walnuts" };
		return new Object[][] { { new ArrayList<>(Arrays.asList(products)) } };
	}

	@Test(dependsOnMethods = { "addProduct" })
	public void checkout() {
		driver.findElement(By.cssSelector("a[class='cart-icon']")).click();

		// check if cart is not empty
		if(driver.findElements(By.cssSelector("div[class='cart-preview active'] div[class='empty-cart']")).size() != 0) {
			System.out.println("Cart is empty");
			return;
		}
		
		// check if checkout button is enabled
		WebElement checkoutBtn = driver
				.findElement(By.cssSelector("div[class='cart-preview active'] div[class='action-block'] > button"));
		if (checkoutBtn.getDomAttribute("class").equals("disabled")) {
			System.out.println("Checkout button is disabled");
			return;
		}

		driver.findElement(By.cssSelector("div[class='cart-preview active'] div[class='action-block'] > button")).click();
		driver.findElement(By.cssSelector("input[class='promoCode']")).sendKeys("rahulshettyacademy");
		driver.findElement(By.cssSelector("button[class='promoBtn']")).click();
		
		// print promo code text
		String promoText = this.wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class = 'promoInfo']")))
				.getText();
		System.out.println("Promo Code message : " + promoText);
		
		driver.findElement(By.xpath("//div[@class='promoWrapper']/following-sibling::button")).click();
	}
}
