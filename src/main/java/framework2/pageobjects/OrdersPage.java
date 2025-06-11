package framework2.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework2.abstractcomponent.AbstractComponent;

public class OrdersPage extends AbstractComponent {
	WebDriver driver = null;

	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "tr[class = 'ng-star-inserted'] > th + td + td")
	List<WebElement> orderedProducts;

	public boolean isProductPresentInOrders(String productName) {
		return orderedProducts.stream().anyMatch(s -> s.getText().trim().equalsIgnoreCase(productName));
	}
}