package xalts;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class XaltsWebsite {
	WebDriver driver = null;
	WebDriverWait wait = null;
	String email = "tapasmaji127334@gmail.com";
	String password = "$3Harley";

	@BeforeMethod(alwaysRun = true)
	public void setup() {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
		wait = new WebDriverWait(driver, Duration.ofSeconds(5L));
		driver.get("https://xaltsocnportal.web.app/");
	}

	@Test(groups = { "Regression" })
	public void signup() {
		driver.findElement(By.cssSelector("div[class = 'logo-container'] + button")).click();
		driver.findElement(By.xpath("(//input[@id= 'outlined-basic'])[1]")).sendKeys(email);
		driver.findElement(By.xpath("(//input[@id= 'outlined-basic'])[2]")).sendKeys(password);
		driver.findElement(By.xpath("(//input[@id= 'outlined-basic'])[3]")).sendKeys(password);
		driver.findElement(By.cssSelector("button.css-1hw9j7s")).click();

		try {
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert text : " + alert.getText());
			Assert.assertEquals(alert.getText(), "Provided E-Mail is already in use");
			alert.accept();
			return;
		} catch (TimeoutException | NoAlertPresentException e) {
			System.out.println(e.getRawMessage());
		}

		// checking sign out button is present
		if (driver.findElements(By.cssSelector("div[class = 'logo-container'] + button")).size() > 0) {
			String signOutText = driver.findElement(By.cssSelector("div[class = 'logo-container'] + button")).getText();
			Assert.assertEquals(signOutText, "SIGN OUT");
			return;
		}

	}

	@Test(dependsOnMethods = { "signup" }, groups = { "Regression" })
	public void signin() throws InterruptedException, IOException {
		driver.findElement(By.cssSelector("div[class = 'logo-container'] + button")).click();
		driver.findElement(By.xpath("//button[contains(@class,'signin-alt-button')]")).click();
		driver.findElement(By.xpath("(//input[@id = 'outlined-basic'])[1]")).sendKeys(email);
		driver.findElement(By.xpath("(//input[@id = 'outlined-basic'])[2]")).sendKeys(password);
		driver.findElement(By.cssSelector("button.css-1hw9j7s:has(span)")).click();

		try {
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			System.err.println("Error message : " + alertText);
			alert.accept();
			return;
		} catch (TimeoutException | NoAlertPresentException | UnhandledAlertException e) {
			System.out.println(e.getRawMessage());
		}


		// checking sign out button is present
		driver.findElement(By.xpath("//h1[text() = 'Open Capital Network']"));
		String signOutText = driver.findElement(By.cssSelector("div[class = 'logo-container'] + button")).getText();
		File source = ((TakesScreenshot) driver.findElement(By.cssSelector("div[class = 'logo-container'] + button")))
				.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File(System.getProperty("user.dir") + "//screenshots/SignoutBtn.png"));
		Assert.assertEquals(signOutText, "SIGN OUT");
		return;

	}

	@Test(dependsOnMethods = { "signin" })
	public void signout() throws InterruptedException, IOException {
		signin();
		WebElement button = driver.findElement(By.cssSelector("div[class = 'logo-container'] + button"));
		String buttonText = button.getText();
		Assert.assertEquals(buttonText, "SIGN OUT");

		button.click();
		Assert.assertFalse(driver.findElement(By.cssSelector("div[class = 'logo-container'] + button")).getText()
				.equalsIgnoreCase("SIGN OUT"));

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		System.out.println("Executed only once after entire class is executed");
	}


	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000L);
		driver.quit();
	}

}
