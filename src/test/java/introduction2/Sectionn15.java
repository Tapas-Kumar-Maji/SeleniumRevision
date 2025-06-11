package introduction2;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Sectionn15 {
	WebDriver driver = null;

	@BeforeClass
	public void initializeDriver() {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2L));
	}

	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	@Test
	public void relativeLocators() {
		driver.get("https://rahulshettyacademy.com/angularpractice/");

		// above
		String text = driver.findElement(with(By.tagName("label")).above(By.cssSelector("div > input[name = 'name']")))
				.getText();
		System.out.println(text);

		// relative locators cannot identify flex webelement,
		// so rather than clicking on date field, submit button is clicked.
		// below
		WebElement dateOfBirth = driver.findElement(By.cssSelector("label[for = 'dateofBirth']"));
		driver.findElement(with(By.tagName("input")).below(dateOfBirth)).click();

		// toLeftOf
		driver.findElement(with(By.tagName("input")).toLeftOf(By.cssSelector("label[for = 'exampleCheck1']"))).click();

		// toRightOf
		text = driver.findElement(with(By.tagName("label")).toRightOf(By.cssSelector("input[id = 'inlineRadio1']")))
				.getText();
		System.out.println(text);

	}

	@Test
	public void littleAssessment() throws InterruptedException, IOException {
		driver.get("https://rahulshettyacademy.com/");
		String firstCourseName = driver
				.findElement(By.cssSelector("div[id = 'courses-block'] > div:nth-child(1) h2 > a")).getText();
		driver.switchTo().newWindow(WindowType.TAB);

		driver.get("https://rahulshettyacademy.com/angularpractice/");
		WebElement nameField = driver.findElement(By.cssSelector("div > input[name = 'name']"));
		nameField.sendKeys(firstCourseName);
		File source = ((TakesScreenshot) nameField).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File(System.getProperty("user.dir") + "\\reports\\nameField.png"));

		int height = nameField.getSize().getHeight();
		int width = nameField.getSize().getWidth();
		System.out.println(height + " " + width);
		System.out.println(nameField.getSize().toString());
		Thread.sleep(3000L);

	}

}
