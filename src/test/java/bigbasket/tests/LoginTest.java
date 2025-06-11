package bigbasket.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

	@Test
	public void loginFlow() throws InterruptedException {
		driver.get("https://www.bigbasket.com/");
		driver.manage().deleteAllCookies();

		/*
		 * Go to the Application tab. Under Storage, click Cookies. Select
		 * https://www.bigbasket.com. Copy the required cookies like session_id or auth_token
		 */
		Cookie sessionCookie = new Cookie("sessionid", "8lcgs7k8df8u4n45jfg6mu2dh4m8exdz");
		Cookie authCookie = new Cookie("BBAUTHTOKEN",
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaGFmZiI6ImlWT2pwUG9zejJSYnZRIiwidGltZSI6MTczNjI4MDc4My45OTA5NjQ0LCJtaWQiOjI3MjQyNzEyLCJ2aWQiOjQ5MzQ0NTY1MDExMjg0NTA1NiwiZGV2aWNlX2lkIjoiV0VCIiwic291cmNlX2lkIjoxLCJlY19saXN0IjpbMyw0LDEwLDEyLDEzLDE0LDE1LDE2LDE3LDIwLDEwMF0sIlRETFRPS0VOIjoiMTA4MjFiOTAtNTg1Zi00ZDllLWEyNGUtMDYzOWZkMDk3Mzk2IiwicmVmcmVzaF90b2tlbiI6IjQxZDVhMzQwLTMyMWQtNGY1Ny05YzJlLTEwYzk1OGYyNjNkYiIsInRkbF9leHBpcnkiOjE3MzY4ODU1ODIsImV4cCI6MTc0OTI1OTk4NSwiaXNfc2FlIjpudWxsLCJkZXZpY2VfbW9kZWwiOiJXRUIiLCJkZXZpY2VfaXNfZGVidWciOiJmYWxzZSJ9.WV4kDC1va8Lc4cXf3ofmqNfWdTyuwpPO_uYxoxKsKbU");
		driver.manage().addCookie(sessionCookie);
		driver.manage().addCookie(authCookie);

//		driver.navigate().refresh();
		driver.get("https://www.bigbasket.com/");
		driver.get("https://www.bigbasket.com/");

		// checking if successfully logged in
		// Thread.sleep(3000L);
		int size = driver.findElements(By.cssSelector("div[class *= 'MemberDropdown']")).size();
		Assert.assertTrue(size > 0, "Login not successful");
		
		// click got it button, in popup in address field, if present.
		if (driver.findElements(By.cssSelector("button[class *= 'px-10']")).size() > 0) {
			WebElement popupGotItBtn = driver.findElement(By.cssSelector("button[class *= 'px-10']"));
			if (popupGotItBtn.isEnabled() && popupGotItBtn.isDisplayed()) {
				popupGotItBtn.click();
			}
		}
	}

	@Test(dependsOnMethods = { "loginFlow" })
	public void productSearch() {
		String product = "apples";
		Actions actions = new Actions(driver, Duration.ofMillis(200L));
		WebElement searchBox = driver.findElement(By.cssSelector("div[class *= 'QuickSearch2'] input"));
		actions.scrollToElement(searchBox).build().perform();
		searchBox.sendKeys(product);
	}
}