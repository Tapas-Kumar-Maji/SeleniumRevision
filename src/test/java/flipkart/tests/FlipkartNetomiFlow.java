package flipkart.tests;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FlipkartNetomiFlow {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.flipkart.com/");

		// handling popup
//		if (driver.findElements(By.cssSelector("span[class = '_30XB9F']")).size() > 0) {
//			driver.findElement(By.cssSelector("span[class = '_30XB9F']")).click();
//		}

		// 1st page
		String productName = "apples food";
		int index = 9;
		driver.findElement(By.cssSelector("input[class = 'Pke_EE']")).sendKeys(productName, Keys.ENTER);
		List<WebElement> products = driver.findElements(By.cssSelector("div[class = 'KzDlHZ']"));
		if (products.isEmpty() || products.size() == 0) {
			products = driver.findElements(By.cssSelector("div[class = 'Nx9bqj']"));
		}
		products.get(index - 1).click();

		// 2nd page
		Iterator<String> windowHandles = driver.getWindowHandles().iterator();
		windowHandles.next();
		driver.switchTo().window(windowHandles.next());
		Thread.sleep(5000L);
		String price = driver.findElement(By.cssSelector("div[class *= 'Nx9bqj CxhGGd']")).getText();
		System.out.println("Price : " + price);
		try {
			driver.findElement(By.cssSelector("ul[class = 'row'] > li > button")).click();
		} catch (NoSuchElementException e) {
			driver.findElement(By.cssSelector("button[class = 'QqFHMw AMnSvF v6sqKe']"));
			System.out.println("Element is sold out");
			System.exit(1);
		}

		// 3rd page
		String cartPrice = driver.findElement(By.cssSelector("span[class = 'LAlF6k re6bBo']")).getText();
		System.out.println("Cart price : " + cartPrice);
		driver.findElement(By.cssSelector("button[class = 'QqFHMw zA2EfJ _7Pd1Fp']")).click();
		driver.findElement(By.cssSelector("input[class = 'r4vIwl Jr-g+f']")).sendKeys("tapasmaji908@gmail.com");
	}
}
