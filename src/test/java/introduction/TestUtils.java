package introduction;

import java.io.IOException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestUtils {

	public WebDriver initializeDriver() {
		this.killProcess("chromedriver");
//		this.killProcess("chrome");
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("start-maximized");
//		options.setAcceptInsecureCerts(true);
		WebDriver driver = new ChromeDriver(options);
//		FirefoxOptions optionsFire = new FirefoxOptions();
//		optionsFire.addPreference("dom.webnotifications.enabled", false);
//		this.driver = new FirefoxDriver();

//		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		return driver;
	}

	public void quitDriver(WebDriver driver) {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}

	public void killProcess(String processName) {
		String os = System.getProperty("os.name").toLowerCase();
		String command = "";

		try {
			if (os.contains("win")) {
				command = "taskkill /F /IM " + processName + ".exe";
			} else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
				command = "pkill" + processName;
			}

			Runtime.getRuntime().exec(command);
//			System.out.println("Killed Process : " + processName);
		} catch (IOException e) {
			System.err.println("Error killing process : " + processName + e.getMessage());
		}
	}
}



