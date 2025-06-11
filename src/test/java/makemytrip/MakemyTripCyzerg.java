package makemytrip;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MakemyTripCyzerg {

	public static void main(String[] args) throws InterruptedException {

		// setup
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.makemytrip.com/");

		// closing popup
		if (driver.findElements(By.cssSelector("span[class = 'commonModal__close']")).size() > 0) {
			driver.findElement(By.cssSelector("span[class = 'commonModal__close']")).click();
		}

		// from
		driver.findElement(By.cssSelector("input[id =  'fromCity']")).click();
		driver.findElement(By.cssSelector("input:has( +div[id = 'react-autowhatever-1'])")).sendKeys("Bangalore");
		Thread.sleep(5000L);
		driver.findElement(By.cssSelector("li[role = 'option']:nth-child(1)")).click();

		// to
		driver.findElement(By.cssSelector("input[id = 'toCity']")).click();
		driver.findElement(By.cssSelector("input:has(+ div[id = 'react-autowhatever-1'])")).sendKeys("Dubai");
		driver.findElement(By.xpath("//span[text() = 'Dubai']")).click();

	}
}
