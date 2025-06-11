package introduction2;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Section7 {
	WebDriver driver = null;

	@BeforeClass
	public void initializeDriver() {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("start-maximized");
//		chromeOptions.addArguments("--disable-notifications");
		chromeOptions.setImplicitWaitTimeout(Duration.ofMillis(2000L));
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		this.driver = new ChromeDriver(chromeOptions);
		// this.driver.manage().window().maximize();
		// this.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
	}

	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		this.driver.quit();
	}

	@Test(enabled = false)
	public void navigation() {
		this.driver.get("https://google.com");
		driver.navigate().to("https://rahulshettyacademy.com/AutomationPractice/");
		driver.navigate().back();
		driver.navigate().forward();
	}

	@Test
	public void astaticDropdown() { // given 'a' at the beginning of the method name so that it executes first.
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		Select select = new Select(driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency")));
		select.selectByIndex(2);
		System.out.println("Currently selected option : " + select.getFirstSelectedOption().getText());
		select.selectByVisibleText("INR");
		System.out.println("Currently selected option : " + select.getFirstSelectedOption().getText());
		select.selectByValue("USD");
		System.out.println("Currently selected option : " + select.getFirstSelectedOption().getText());
	}

	@Test
	public void dynamicDropdown() {
		driver.findElement(By.cssSelector("label ~ div[id $= 'paxinfo']")).click();
		for (int i = 1; i < 5; i++) {
			driver.findElement(By.cssSelector("div[class $= 'row'][id *= 'Adult'] span[id $= 'Audlt'] + span")).click();
		}
		driver.findElement(By.xpath("//div[starts-with(@class, 'ad')]/child::input")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("div[id = divpaxinfo]")).getText(), "5 Adult");
	}

	@Test
	public void dynamicDropdown2() throws InterruptedException {
		driver.findElement(By.xpath(
				"//div[@class = 'left'][label[normalize-space() = 'FROM']]/following-sibling::input[contains(@id, 'originStation1_CTXT')]"))
				.click();
		driver.findElement(By.cssSelector("div.left1 a[text *= 'DEL']")).click();
		driver.findElement(By.cssSelector("div.right1 a[text *= 'CCU']")).click();
		driver.findElement(By.cssSelector("a[class *= 'ui-state-active']")).click();

		WebElement returnDate = driver
				.findElement(By.cssSelector("div[id = 'Div1']:has(input[id $= 'ctl00_mainContent_view_date2'])"));
		System.out.println(returnDate.getDomAttribute("style"));
		driver.findElement(By.cssSelector("input[id $= 'Trip_1']")).click();
		Assert.assertTrue(returnDate.getDomAttribute("style").contains("1"));
	}

	@Test
	public void autoSuggestiveDropdown() throws InterruptedException {
		driver.findElement(By.cssSelector("legend:has(b) + span + input")).sendKeys("Uni");
		List<WebElement> dropdownitems = driver.findElements(By.cssSelector("a[class ^= 'ui-corner']"));
		for (WebElement ele : dropdownitems) {
			if (ele.getText().contains("USA")) {
				ele.click();
				break;
			}
		}
	}

	@Test
	public void checkbox() {
		WebElement seniorCitizenCheckbox = driver
				.findElement(By.xpath("//label[contains(@for, 'SeniorCitizenDiscount')]/preceding-sibling::input"));
		Assert.assertFalse(seniorCitizenCheckbox.isSelected());
		seniorCitizenCheckbox.click();
		Assert.assertTrue(seniorCitizenCheckbox.isSelected());

		// count number of checkboxes
		int number = driver.findElements(By.cssSelector("[type=\"checkbox\"]")).size();
		System.out.println("Number of checkboxes : " + number);
	}

	@Test
	public void findFlights() {
		driver.findElement(By.cssSelector("input[id $= 'FindFlights']")).click();
	}

	@Test(priority = -1)
	public void alerts() throws InterruptedException {
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		driver.findElement(By.cssSelector("fieldset[class = 'pull-right'] > input:has(~ input + input)"))
				.sendKeys("Alexis Texas");
		driver.findElement(By.cssSelector("fieldset[class = 'pull-right'] > input:has(+ input)")).click();
		Alert alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		alert.dismiss();

		driver.findElement(By.id("confirmbtn")).click();
		System.out.println(alert.getText());
		alert.accept();
		Thread.sleep(2000L);
	}

}