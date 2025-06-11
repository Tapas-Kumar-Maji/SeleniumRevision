package introduction;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Frames {
	private WebDriver driver;

	@BeforeClass
	private void before() {
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		this.driver = new ChromeDriver(options);
//		FirefoxOptions optionsFire = new FirefoxOptions();
//		optionsFire.addPreference("dom.webnotifications.enabled", false);
//		this.driver = new FirefoxDriver();

		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		this.driver.get("https://jqueryui.com/droppable/");
	}

	@AfterClass
	private void after() throws InterruptedException {
		Thread.sleep(5000L);
		this.driver.quit();
	}

	@Test
	public void frame() {
		int numberOfFrames = driver.findElements(By.tagName("iframe")).size();
		System.out.println("Number of frames in the web page : " + numberOfFrames);
//		driver.switchTo().frame(0);   not working

		WebElement frame = driver.findElement(By.cssSelector("iframe[class='demo-frame']"));
		driver.switchTo().frame(frame);

		WebElement source = driver.findElement(By.cssSelector("div[id='draggable']"));
		WebElement target = driver.findElement(By.cssSelector("div[id='droppable']"));
		Actions actions = new Actions(driver, Duration.ofMillis(100L));
		actions.dragAndDrop(source, target)
				.perform();

		driver.switchTo().defaultContent();
		driver.findElement(By.cssSelector("div[class='demo-list']  li:nth-child(5)")).click();
	}
}
