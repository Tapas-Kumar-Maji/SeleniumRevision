package introduction2;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Practice {
	WebDriver driver = null;

	@BeforeClass
	public void initializeDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2000L));
	}

	@AfterClass
	public void tearDown() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	@Test
	public void section7() {
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		WebElement dropDown = driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency"));
		Select select = new Select(dropDown);
		select.selectByValue("value");
		select.selectByVisibleText("visible text");

		String currentWindowHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(currentWindowHandle)) {
				driver.switchTo().window(handle);
			}
		}

		driver.switchTo().newWindow(WindowType.TAB);
		WebElement frame = null;
		driver.switchTo().frame(frame);
		driver.switchTo().defaultContent();

		driver.manage().deleteAllCookies();
		driver.manage().deleteCookieNamed("login");
	}

	public void checkBrokenLinks() throws IOException {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		for (WebElement ele : links) {
			String url = ele.getDomAttribute("href");
			if (url == null || url.isEmpty()) {
				System.out.println("Invalid url : " + url);
			}

			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("HEAD");
			connection.connect();
			int responseCode = connection.getResponseCode();
			if (responseCode >= 400) {
				System.out.println(url + " is broken");
			} else {
				System.out.println(url + " is valid");
			}
		}
	}

	public void dropdowns() {
		// Dropdowns contains Select tag. We use Select class to handle dropdowns.
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		Select select = new Select(driver.findElement(By.tagName("select")));
		select.selectByIndex(1);
		select.selectByVisibleText("text");
		select.selectByContainsVisibleText("text");
		select.selectByValue("value");
	}

	public void actions() {
		Actions actions = new Actions(driver, Duration.ofMillis(200L));
		actions.moveToElement(null).clickAndHold().build().perform();
		actions.dragAndDrop(null, null).build().perform();
		String ctrlEnter = Keys.chord(Keys.CONTROL, Keys.ENTER);
		actions.moveToElement(null).click().sendKeys("mobiles", ctrlEnter).contextClick(null).build().perform();
		actions.scrollToElement(null);
	}

	public void switchTomethod() {
		driver.switchTo().window("window handle");
		driver.switchTo().newWindow(WindowType.WINDOW);
		driver.switchTo().frame(0);
		driver.switchTo().frame("");
		driver.switchTo().defaultContent();
	}

	public void screenshot() throws IOException {
		// entire webpage
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File(System.getProperty("user.dir") + "\\reports\\screenshot.png"));

		// only webelement
		WebElement ele = null;
		String base64String = ((TakesScreenshot) ele).getScreenshotAs(OutputType.BASE64);
	}

}
