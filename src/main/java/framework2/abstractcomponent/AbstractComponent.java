package framework2.abstractcomponent;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework2.pageobjects.CartPage;
import framework2.pageobjects.OrdersPage;

public class AbstractComponent {
	WebDriverWait wait = null;
	Actions actions = null;
	WebDriver driver = null;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofMillis(5000L));
		actions = new Actions(driver, Duration.ofMillis(100L));
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "button[routerlink = '/dashboard/cart']")
	WebElement cartBtn;

	@FindBy(css = "button[routerlink =  '/dashboard/myorders']")
	WebElement ordersBtn;

	@FindBy(css = "h1.ng-star-inserted")
	WebElement yourOrdersTitle;

	public CartPage clickCartButton() throws InterruptedException {
		this.scrollToElement(cartBtn);
		cartBtn.click();
		Thread.sleep(1000L);
		return new CartPage(driver);
	}

	public OrdersPage clickOrdersButton() throws InterruptedException {
		this.scrollToElement(ordersBtn);
		ordersBtn.click();
		Thread.sleep(1000L);
		return new OrdersPage(driver);
	}

	public void waitUntilVisibilityOfAllElementsLocatedBy(By locator) {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public void waitUntilVisibilityOfAllElements(List<WebElement> elements) {
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public void waitUntilVisibilityOfElementLocated(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitUntilVisibilityOf(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	// Selenium issue, with invisibilityOfElementLocated(By) method it was taking too much time.
	public void waitUntilInvisibilityOf(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element)); 
	}

	public void waitUntilTextToBe(By locator, String text) {
		wait.until(ExpectedConditions.textToBe(locator, text));
	}

	public void waitUntilTextToBePresentInElementLocated(By locator, String text) {
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	public void scrollToElement(WebElement element) {
		actions.scrollToElement(element).build().perform();
	}

	public void moveByOffset(int x, int y) {
		actions.moveByOffset(x, y).build().perform();
	}
}
