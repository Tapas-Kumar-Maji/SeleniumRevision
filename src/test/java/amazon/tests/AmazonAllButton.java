package amazon.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AmazonAllButton {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		driver.get("https://www.amazon.in/");

		// handling captcha
		try {
			driver.findElement(By.cssSelector("div[class *= 'a-text-right'] > a")).click();
		} catch (Exception e) {
		}

		// 1st page
		driver.findElement(By.cssSelector("a[id = 'nav-hamburger-menu']")).click();
		String item = "Fire TV"; // "TV,"; // "Launchpad";
		WebElement leftPanelItem = driver.findElement(By.xpath("(//div[@id = 'hmenu-content'] //*[contains(text(), '" + item + "')])[1]"));
		Thread.sleep(1000L);
		leftPanelItem.click();
		Thread.sleep(1000L);
		WebElement primeVideo = driver.findElement(By.xpath("//a[contains(text(), 'Prime Video')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", primeVideo);

		// 2nd page
		int index = 3;
		List<WebElement> list = driver
				.findElements(By.cssSelector("div[id = 'sobe_d_b_ms_3-carousel-viewport-container'] li"));
		list.get(index - 1).click();
		String title = driver.findElement(By.cssSelector("div[class = 'mj4dZi'] > img")).getDomAttribute("alt");
		System.out.println("Title :" + title);
	}

}
