package introduction;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Introduction {
	public static void main(String[] args) throws InterruptedException {
		// dosen't give warning for selenium version.
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		// WebDriver driver = new FirefoxDriver();

		driver.get("https://rahulshettyacademy.com/");
		System.out.println("Title : " + driver.getTitle());
		System.out.println("Url : " + driver.getCurrentUrl());

		Thread.sleep(2000L);
		driver.quit();
	}

}
