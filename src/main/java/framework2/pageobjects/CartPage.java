package framework2.pageobjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework2.abstractcomponent.AbstractComponent;

public class CartPage extends AbstractComponent {
	WebDriver driver = null;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.waitUntilTextToBe(By.cssSelector("div[class ^= 'heading'] h1"), "My Cart");
	}

	@FindBy(xpath = "//div[@class = 'infoWrap'] //h3")
	List<WebElement> cartItems;

	@FindBy(xpath = "//li[@class = 'totalRow']/button")
	WebElement checkoutBtn;

	public List<String> getAllCartItems() {
		return cartItems.stream().map(s -> s.getText()).collect(Collectors.toList());
	}

	public boolean isProductAddedPresent(String product) {
		return cartItems.stream().anyMatch(s -> s.getText().equalsIgnoreCase(product));
	}

	public PaymentPage clickCheckoutButton() throws InterruptedException {
		this.scrollToElement(checkoutBtn);
		checkoutBtn.click();
		Thread.sleep(1000L);
		return new PaymentPage(driver);
	}
}