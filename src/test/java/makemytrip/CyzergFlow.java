package makemytrip;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CyzergFlow {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.makemytrip.com/");

		driver.findElement(By.cssSelector("span.commonModal__close")).click();
		driver.findElement(By.cssSelector("input#fromCity")).click();
		driver.findElement(By.cssSelector("input:has(+div[id = 'react-autowhatever-1'])")).sendKeys("Bengaluru");
		Thread.sleep(1000L);
		driver.findElement(By.cssSelector("li#react-autowhatever-1-section-0-item-0")).click();
		driver.findElement(By.cssSelector("input#toCity")).click();
		driver.findElement(By.cssSelector("input:has(+div[id = 'react-autowhatever-1'])")).sendKeys("Dubai");
		Thread.sleep(1000L);
		driver.findElement(By.cssSelector("li#react-autowhatever-1-section-0-item-0")).click();
		driver.findElement(By.xpath("//div[@class = 'DayPicker-Day'] //p[text() = '3']")).click();

		driver.findElement(By.cssSelector("input#travellers + p")).click();
		driver.findElement(By.cssSelector("ul.classSelect li:last-child")).click();
		driver.findElement(By.cssSelector("button.btnApply")).click();

		Thread.sleep(5000L);
		driver.quit();
	}
}
