package introduction2;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Section5 {

	public static void main(String[] args) throws InterruptedException {
		// supress logging for CDP version
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);
		
		// setup code
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("start-maximized");

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(chromeOptions);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		driver.get("https://rahulshettyacademy.com/locatorspractice/");

		// normal code.
		driver.findElement(By.id("inputUsername")).sendKeys("tapas the sailor man");
		driver.findElement(By.name("inputPassword")).sendKeys("randomPass");
		driver.findElement(By.className("signInBtn")).click();
		String errorMsg = driver.findElement(By.cssSelector("p[class = 'error']")).getText();
		System.out.println("Error message : " + errorMsg);
		Thread.sleep(1000L);
		driver.findElement(By.linkText("Forgot your password?")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000L));
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.cssSelector("div[class = 'overlay-panel overlay-right']")));
		driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("Lizzy Lettuce");
		driver.findElement(By.cssSelector("*[placeholder='Email']")).sendKeys("Liz@gmail.com");
		driver.findElement(By.cssSelector("*[placeholder='Email']")).clear();
		driver.findElement(By.cssSelector("input[type = 'text']:nth-child(3)")).sendKeys("tapas@gmail.com");
		driver.findElement(By.xpath("//form/input[3]")).sendKeys("85857229271");
		driver.findElement(By.cssSelector("div button[class *= 'reset-pwd']")).click();
		String temporaryPassword = driver.findElement(By.xpath("//p[contains(@class, 'infoM')]")).getText();
		temporaryPassword = temporaryPassword.substring(temporaryPassword.indexOf('\'') + 1,
				temporaryPassword.lastIndexOf('\''));
		System.out.println("Temp password : " + temporaryPassword);
		driver.findElement(By.xpath("//button[contains(@class, 'go-to-login')]")).click();

//		System.out.println(driver.findElement(By.cssSelector("div[class *= 'overlay-left']")).isDisplayed());
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class *= 'overlay-left']")));
//		System.out.println(driver.findElement(By.cssSelector("div[class *= 'overlay-left']")).isDisplayed());
		driver.findElement(By.cssSelector("input#inputUsername")).sendKeys("Ulltra Marine");
		driver.findElement(By.cssSelector("input[name *= 'Password']")).sendKeys(temporaryPassword);
		driver.findElement(By.id("chkboxOne")).click();
		driver.findElement(By.cssSelector("div button[class *= 'signInBtn']")).click();

		String isLoggedIn = driver.findElement(By.xpath("//h2/following-sibling::p")).getText();
		System.out.println(isLoggedIn);
		driver.findElement(By.xpath("//button[normalize-space() = 'Log Out']")).click();

	}
}
