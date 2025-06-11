package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptActions {
	WebDriver driver;

	public JavaScriptActions(WebDriver driver) {
		this.driver = driver;
	}

	/*
	 * Use when click gets intercepted
	 */
	public void click(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public String getText(WebElement element) {
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", element);
	}

	/*
	 * Doesn't simulate actual typing like sendKeys() does.
	 * Instead inserts the text in WebElements value field directly.
	 */
	public void sendKeys(WebElement element, String text) {
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + text + "';", element);
	}

	public void scrollIntoView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
}

