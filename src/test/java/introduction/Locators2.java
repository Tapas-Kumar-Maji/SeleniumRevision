package introduction;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.JavaScriptActions;

public class Locators2 {

	public WebDriver before() {
		Logger logger = Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder");
		logger.setLevel(Level.SEVERE);

		WebDriver driver = new ChromeDriver();
		// WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000L));
		return driver;
	}

	@Test
	public void traverse() throws InterruptedException {
		WebDriver driver = before();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		JavaScriptActions jsActions = new JavaScriptActions(driver);

		String text = jsActions.getText(driver.findElement(By.xpath("//header/div/button[1]/following-sibling::button[1]")));
		// parent to child traverse
		// driver.findElement(By.xpath("//button/ancestor::header/div/button[2]"));
		System.out.println("Button text : " + text);
		driver.quit();
	}

	@Test
	public void windowActivities() {
		WebDriver driver = before();
		JavaScriptActions jsActions = new JavaScriptActions(driver);

		driver.get("https://www.google.com/");
		driver.navigate().to("https://rahulshettyacademy.com/");
		System.out.println("Current webpage title : " + driver.getTitle());
		System.out.println("Button text : " + jsActions.getText(driver.findElement(By.cssSelector("a.btn-theme"))));
		driver.navigate().back();

		driver.quit();
	}

	@Test
	public void dropDowns() throws InterruptedException {
		WebDriver driver = before();
		JavaScriptActions jsActions = new JavaScriptActions(driver);

		// Dropdown for currency.
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
		WebElement staticDropDown = driver.findElement(By.cssSelector("select#ctl00_mainContent_DropDownListCurrency"));
		Select select = new Select(staticDropDown);
		select.selectByIndex(3);
		System.out.println("Selected static dropdown text :" + select.getFirstSelectedOption().getText());
		select.selectByValue("AED");
		System.out.println("Selected static dropdown text :" + select.getFirstSelectedOption().getText());

		// Dropdown for passengers.
		jsActions.click(driver.findElement(By.cssSelector("div[id = divpaxinfo]")));
		for (int i = 0; i < 4; i++) {
			jsActions.click(driver.findElement(By.cssSelector("span[id = hrefIncAdt]")));
		}
		jsActions.click(driver.findElement(By.cssSelector("input[class = buttonN][value = Done]")));
		System.out.println("Passengers dropdown : " + driver.findElement(By.cssSelector("div[id = divpaxinfo]")).getText());

		// Dropdown for FROM.
		jsActions.click(driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXTaction")));
		jsActions.click(driver.findElement(By.cssSelector("a[value = 'DED']")));
		jsActions.click(driver.findElement(By.cssSelector("div[class = 'right1'] a[value = 'BHO']")));
		jsActions.click(driver.findElement(By.cssSelector("a.ui-state-highlight")));

		// Dropdown for country.
		driver.findElement(By.cssSelector("input[id = 'autosuggest']")).sendKeys("Ind");
		List<WebElement> dropList = driver.findElements(By.cssSelector("a[class = 'ui-corner-all']"));
		for (WebElement element : dropList) {
			if (jsActions.getText(element).equalsIgnoreCase("India")) {
				System.out.println("Country : " + jsActions.getText(element));
				jsActions.click(element);
				break;
			}
		}

		driver.quit();
	}

	@Test
	public void spiceJet() throws InterruptedException {
		WebDriver driver = before();

		// Select checkbox
		driver.get("https://www.spicejet.com/");
		WebElement checkbox = driver.findElement(
				By.xpath("(//div[@class = 'css-1dbjc4n r-18u37iz r-1w6e6rj'])[2]/div[1]"));
		checkbox.click();
		boolean selected = driver
				.findElements(By.cssSelector("div[class= 'css-1dbjc4n'] > svg > g > circle:nth-child(2)")).size() > 0;
		Assert.assertTrue(selected);
		if (selected) {
			System.out.println(checkbox.getText() + " is selected");
		}
		
		// Find number of checkboxes.
		int noOfCheckboxes = driver.findElements(By.xpath("(//div[@class = 'css-1dbjc4n r-18u37iz r-1w6e6rj'])[2]/div")).size();
		System.out.println("Number of checboxes : " + noOfCheckboxes);

		// Calendar
		driver.findElement(By.xpath("//div[@data-testid='departure-date-dropdown-label-test-id']")).click();
		LocalDate currentDate = LocalDate.now();
		int dayOfMonth = currentDate.getDayOfMonth();
		WebElement actualDate = driver.findElement(By.xpath(
				"(//div[@class=\"css-1dbjc4n r-1awozwy r-14lw9ot r-1loqt21 r-eu3ka r-1otgn73 r-1aockid\"] //div[@class = 'css-76zvg2 r-homxoj r-ubezar r-16dba41'])[1]"));
		Assert.assertEquals(actualDate.getText(), dayOfMonth + "");
		actualDate.click();
		
		// Return Date
		driver.findElement(By.xpath("(//div[@class='css-1dbjc4n r-zso239'])[2]")).click();
		WebElement returnDate = driver.findElement(By.xpath("//div[@data-testid='return-date-dropdown-label-test-id']/div[1]"));
		Assert.assertTrue(returnDate.getDomAttribute("style").contains("opacity: 1"));

		Thread.sleep(5000L);
		driver.quit();
	}
}
