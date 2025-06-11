package makemytrip;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelBooking {
	static int adults = 3;

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.makemytrip.com/");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));

		// 1st page
		driver.findElement(By.cssSelector("span.commonModal__close")).click();
		driver.findElement(By.cssSelector("li.menu_Hotels")).click();
		driver.findElement(By.cssSelector("input#city")).click();
		driver.findElement(By.cssSelector("div.hw__searchInputWrapper > input")).sendKeys("Chandigarh");
		Thread.sleep(1000L);
		driver.findElement(By.cssSelector("li[id *= 'item-0']")).click();

		driver.findElement(By.xpath("(//div[text() = '17'])[2]")).click();
		driver.findElement(By.xpath("(//div[text() = '25'])[3]")).click();
		driver.findElement(By.xpath("(//div[@class = 'gstSlctCont'])[2]")).click();
		driver.findElement(By.xpath("//ul[@class = 'gstSlct__list'] /li[" + adults + "]")).click();
		driver.findElement(By.cssSelector("button.btnApplyNew")).click();
		driver.findElement(By.cssSelector("button#hsw_search_button")).click();

		// 2nd page
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div.priceBucketFilter li label"))))
				.click();
		driver.findElement(By.xpath("(//div[contains(@id, 'Listing_hotel')])[6] //div[@class = 'makeRelative']"))
				.click();

		// 3rd page

	}
}
