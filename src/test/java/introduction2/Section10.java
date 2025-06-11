package introduction2;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Section10 {
	WebDriver driver = null;
	Actions actions = null;
	WebDriverWait wait = null;

	@BeforeClass
	public void initializeDriver() throws InterruptedException {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10L));
		actions = new Actions(driver, Duration.ofMillis(100L));
	}

	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	@Test
	public void amouseHover() throws InterruptedException {
		driver.get("https://www.amazon.in/");
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("span[id ^= 'nav-link-account']"),
				"sign in"));
		actions.moveToElement(
				driver.findElement(By.cssSelector("a[id = 'nav-link-accountList']:has(span[class = 'nav-line-2 '])")))
				.perform();
		Thread.sleep(2000L);
		actions.contextClick().perform();
		Thread.sleep(2000L);
	}

	@Test
	public void enterTextInUpperCase() {
		actions.click(driver.findElement(By.cssSelector("input[id = 'twotabsearchtextbox']")))
				.keyDown(Keys.SHIFT).sendKeys("janet").keyUp(Keys.SHIFT).doubleClick().perform();
	}

	@Test
	public void zchildWindow() {
		driver.get("https://rahulshettyacademy.com/loginpagePractise/#");
		String originalWindowHandle = driver.getWindowHandle();
		driver.findElement(By.cssSelector("div[class = 'float-right'] > a[href *= 'documents']")).click();

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalWindowHandle)) {
				driver.switchTo().window(handle);
			}
		}

		String email = driver.findElement(By.cssSelector("p[class $= 'red'] a")).getText();
		System.out.println("email : " + email);
		driver.switchTo().window(originalWindowHandle);
		driver.findElement(By.cssSelector("input#username")).sendKeys(email);
	}

	@Test
	public void frames() throws InterruptedException {
		driver.get("https://jqueryui.com/droppable/");
		driver.switchTo().frame(driver.findElement(By.cssSelector("div[class = 'demo-list']  + iframe")));
		actions.dragAndDrop(driver.findElement(By.cssSelector("div#draggable")),
				driver.findElement(By.cssSelector("div#droppable")));
		driver.switchTo().defaultContent();
		driver.findElement(By.cssSelector("div[class = 'demo-list'] a[href *= 'accept']")).click();
		Thread.sleep(2000L);
	}
}