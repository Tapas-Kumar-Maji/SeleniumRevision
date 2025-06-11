package framework2.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework2.abstractcomponent.AbstractComponent;

public class PaymentPage extends AbstractComponent {
	WebDriver driver = null;

	public PaymentPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.waitUntilTextToBePresentInElementLocated(By.cssSelector("div[class = 'payment'] > div[class = 'payment__title']"),
				"Payment Method");
	}

	@FindBy(xpath = "//div[@class = 'payment__shipping'] //a")
	WebElement placeOrderBtn;

	@FindBy(css = "div[class = 'user__name mt-5'] > input")
	WebElement email;

	@FindBy(css = "div[class = 'form-group'] > input")
	WebElement country;

	@FindBy(css = "section[class *= 'ta-results'] > button")
	List<WebElement> countryList;


	public void fillShippingInfo(String emailName, String countryName, String fullCountryName) {
		this.scrollToElement(placeOrderBtn);
		email.clear();
		email.sendKeys(emailName);
		country.sendKeys(countryName);
		this.waitUntilVisibilityOfAllElements(countryList);
		countryList.stream().filter(country -> country.getText().trim().equalsIgnoreCase(fullCountryName)).limit(1)
				.forEach(country -> country.click());
	}

	public ConfirmationPage clickPlaceOrderBtn() {
		placeOrderBtn.click();
		return new ConfirmationPage(driver);
	}
}
