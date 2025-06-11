package framework2.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework2.abstractcomponent.AbstractComponent;

public class ProductsPage extends AbstractComponent {
	WebDriver driver = null;

	public ProductsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.waitUntilVisibilityOfElementLocated(By.cssSelector("section[id = 'sidebar'] > p"));
		this.waitUntilInvisibilityOf(toastContainer);
		this.waitUntilVisibilityOfAllElementsLocatedBy(By.xpath("//div[@class = 'card'] //h5"));
	}

	@FindBy(xpath = "//div[@class = 'card']")
	List<WebElement> cards;
	
	@FindBy(css = ".ng-animating")
	WebElement overlap;

	@FindBy(id = "toast-container")
	WebElement toastContainer;

	public void addProductToCart(String product) {
		WebElement card = cards.stream()
				.filter(s -> s.findElement(By.tagName("h5")).getText().equalsIgnoreCase(product)).findFirst().orElse(null);
		this.scrollToElement(card.findElement(By.cssSelector("button:has(i[class *= 'fa-shopping-cart'])")));
		card.findElement(By.cssSelector("button:has(i[class *= 'fa-shopping-cart'])")).click();
		this.waitUntilInvisibilityOf(overlap); // Selenium issue, with invisibilityOfElementLocated(By) method it was taking too much time.
		this.moveByOffset(5, 5);
		this.waitUntilInvisibilityOf(toastContainer);
	}

}
