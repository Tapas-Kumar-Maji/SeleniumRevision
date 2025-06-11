package introduction2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class Section13 {
	WebDriver driver = null;

	@BeforeClass
	public void initializeDriver() {
		this.killProcess("chromedriver");
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--disable-notifications");
		options.setAcceptInsecureCerts(true);
		// options.addExtensions(null);

//		Proxy proxy = new Proxy();
//		proxy.setHttpProxy("ipaddress:port");
//		options.setProxy(proxy);

//		Map<String, Object> prefs = new HashMap<>();
//		prefs.put("download.default_directory", "C:\\Users\\tapos\\Documents");
//		options.setExperimentalOption("prefs", prefs);

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2L));
//		driver.manage().deleteAllCookies();
//		Cookie cookie = new Cookie("cookiename", "cookievalue");
//		driver.manage().deleteCookie(cookie);
	}

	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	private void killProcess(String processName) {
		String os = System.getProperty("os.name").toLowerCase();
		String command = null;
		
		try {
			if(os.contains("win")) {
				command = "taskkill /F /IM " + processName + ".exe";
			}
			else if(os.contains("nix") || os.contains("nux") || os.contains("mac")) {
				command = "pkill " + processName;
			}
			Runtime.getRuntime().exec(command);
			System.out.println("Killed process : " + processName);
		} catch (IOException e) {
			System.err.println("Error killing process : " + processName + " : " + e.getMessage());
		}
	}

	@Test(enabled = true)
	public void openBrowserInHttp() {
		driver.get("https://expired.badssl.com/");
		System.out.println(driver.findElement(By.tagName("h1")).getText());
	}

	@Test(enabled = false)
	public void takeScreenShot() throws IOException {

		/*
		 * screenshot of entire page, stored as a temporary file only available till JVM
		 * does not exit, copy the temporary file to another location to store permanently
		 */
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File(System.getProperty("user.dir") + "\\reports\\screenshot.png"));

		/*
		 * screenshot of entire page, initially stored as a base64 String. Decoded the
		 * Base64 string into bytes, and the bytes are written to a file.
		 */
		String base64String = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		try {
			// Decode the Base64 string into bytes
			byte[] decodedBytes = Base64.getDecoder().decode(base64String);
			// Create a file and write the decoded bytes
			File file = new File(System.getProperty("user.dir") + "\\reports\\screenshot.png");
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(decodedBytes);
			}
			System.out.println("File created successfully at: " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * screenshot of a webelement.
		 */
		WebElement element = driver.findElement(By.tagName("h1"));
		source = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File(System.getProperty("user.dir") + "\\reports\\screenshot.png"));
	}

	@Test
	public void brokenLinks() {
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		List<WebElement> links = driver.findElements(By.tagName("a"));
		SoftAssert softAssert = new SoftAssert();

		String reportsFolderPath = System.getProperty("user.dir") + "\\reports";
		File reportsFolder = new File(reportsFolderPath);
		if (reportsFolder.exists()) {
			for(File file : reportsFolder.listFiles()) {
				file.delete();
			}
		} else {
			reportsFolder.mkdir();
		}

		int i = 1;
		for (WebElement ele : links) {
			String url = ele.getDomAttribute("href");
			if (url == null || url.isEmpty()) {
				System.out.println("Invalid url : " + url);
				continue;
			}

			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
				connection.setRequestMethod("HEAD"); // getting only header of response, not the body
				connection.connect();
				int responseCode = connection.getResponseCode();
				softAssert.assertTrue(responseCode < 400, "Broken link :" + url + "/n status code : " + responseCode);

				if (responseCode >= 400) {
					File source = ((TakesScreenshot) ele).getScreenshotAs(OutputType.FILE);
					FileHandler.copy(source,
							new File(System.getProperty("user.dir") + "\\reports\\screenshot" + i++ + ".png"));
				}
			} catch (IOException e) {

			}
		}
		softAssert.assertAll();
	}
}
