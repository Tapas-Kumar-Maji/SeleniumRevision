package framework2.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework2.abstractcomponent.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {
	WebDriver driver = null;

	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(tagName = "h1")
	WebElement thankyou;

	public String getConfirmationMessage() {
		this.waitUntilVisibilityOf(thankyou);
		this.scrollToElement(thankyou);
		return thankyou.getText().toUpperCase();
	}
}
