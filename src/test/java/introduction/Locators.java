package introduction;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class Locators {
	static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		String name = "Tania";
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		driver = new ChromeDriver();
//		driver = new FirefoxDriver();
//		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		driver.manage().window().maximize();

		// Normal Code, 1st page.
		driver.get("https://rahulshettyacademy.com/locatorspractice/");
		driver.findElement(By.id("inputUsername")).sendKeys(name);
		driver.findElement(By.name("inputPassword")).sendKeys("#1Tapasmaji");
		driver.findElement(By.cssSelector("button.signInBtn")).click();

		String errorText = javaScriptGetText(driver.findElement(By.cssSelector("p[class = 'error']")));
		System.out.println("ERROR : " + errorText);
		driver.findElement(By.cssSelector("div >  a[href = '#']")).click();

		// 2nd page.
		driver.findElement(By.cssSelector("input[placeholder = 'Name']")).sendKeys(name);
		driver.findElement(By.xpath("//input[@placeholder = 'Email']")).sendKeys("tapasmaji@gmail.com");
		driver.findElement(By.xpath("//input[@placeholder = 'Email']")).clear();
		driver.findElement(By.xpath("//input[@placeholder = 'Email']")).sendKeys("tapasmaji909@gmail.com");
		driver.findElement(By.cssSelector("form > input[placeholder = 'Phone Number']")).sendKeys("92627497221");
		javaScriptClick(driver.findElement(By.cssSelector("button[class = reset-pwd-btn]")));

		String infoMsg = javaScriptGetText(driver.findElement(By.cssSelector("p[class = infoMsg]")));
		String password = infoMsg.substring(infoMsg.indexOf('\'') + 1, infoMsg.lastIndexOf('\''));
		System.out.println("Password : " + password);

		// 3rd page.
		javaScriptClick(driver.findElement(By.cssSelector("button[class = go-to-login-btn]")));
		driver.findElement(By.cssSelector("input[id = inputUsername]")).sendKeys(name);
		driver.findElement(By.cssSelector("input[type*=pass]")).sendKeys(password);
		javaScriptClick(driver.findElement(By.cssSelector("input#chkboxOne")));
		javaScriptClick(driver.findElement(By.xpath("//form/button[contains(@class, signInBtn)]")));

		// 4th page, Assertions.
		String actualLoginMsg = javaScriptGetText(
				driver.findElement(By.cssSelector("div[class = 'login-container'] > p")));
		Assert.assertEquals(actualLoginMsg, "You are successfully logged in.");
		String actualName = javaScriptGetText(
				driver.findElement(By.cssSelector("div[class = 'login-container'] > h2")));
		Assert.assertEquals(actualName, "Hello " + name + ",");

		javaScriptClick(driver.findElement(By.xpath("//button[contains(normalize-space(),'Log Out')]")));

		Thread.sleep(3000L);
		driver.quit();
	}

	private static void javaScriptClick(WebElement element) {
		/* Use when click gets intercepted */
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	private static String javaScriptGetText(WebElement element) {
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", element);
	}

//	private static void javaScriptSendKeys(WebElement element, String text) {
//		/* Doesn't simulate actual typing like sendKeys() does. */
//		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + text + "';", element);
//	}
}
