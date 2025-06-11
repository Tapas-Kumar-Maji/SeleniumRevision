package amazon.tests;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class AmazonDevices {

	public static void main(String[] args) throws InterruptedException {
		String productName = "Stick Lite";
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.amazon.in/");
		Actions actions = new Actions(driver, Duration.ofMillis(500L));

		// 1st page
		driver.findElement(By.cssSelector("div.nav-search-scope")).click();
		Select searchOptions = new Select(driver.findElement(By.cssSelector("select#searchDropdownBox")));
		searchOptions.selectByValue("search-alias=amazon-devices");
		driver.findElement(By.cssSelector("span[id='nav-search-submit-text']")).click();
		Thread.sleep(1000L);

		List<WebElement> products = driver.findElements(By.cssSelector("h2 > span")); //div[class = 'a-section'] > div.puisg-row
		for (WebElement ele : products) {
			if (ele.getText().toLowerCase().contains(productName.toLowerCase())) {
				ele.click();
				break;
			}
		}

		// 2nd page
		Iterator<String> windowHandles = driver.getWindowHandles().iterator();
		windowHandles.next();
		String newWinHandle = windowHandles.next();
		driver.switchTo().window(newWinHandle);
		driver.findElement(By.cssSelector("span.a-dropdown-label")).click();

		driver.findElements(By.cssSelector("div[class = 'a-popover-wrapper'] li")).get(2).click();
//		driver.findElement(By.cssSelector("div#pav-carousel a:has(i.a-icon-next)")).click();
		actions.click(driver.findElement(By.cssSelector("div#pav-carousel a:has(i.a-icon-next)"))).build().perform();
		driver.findElement(By.xpath("(//div[@class = 'pav-title'])[5]")).click();
	}

}
