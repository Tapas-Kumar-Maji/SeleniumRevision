package framework2.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework2.abstractcomponent.AbstractComponent;

public class LoginPage extends AbstractComponent {

	WebDriver driver = null;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userEmail")
	WebElement email;

	@FindBy(id = "userPassword")
	WebElement password;

	@FindBy(css = "input[id = 'login']")
	WebElement loginBtn;

	@FindBy(css = "div[id = 'toast-container'] > div > div")
	WebElement errorToast;

	public ProductsPage loginApplication(String em, String pass) throws InterruptedException {
		email.sendKeys(em);
		password.sendKeys(pass);
		Thread.sleep(1000L);
		loginBtn.click();
		return new ProductsPage(driver);
	}

	public void loginWithIncorrectCredentials(String em, String pass) throws InterruptedException {
		email.sendKeys(em);
		password.sendKeys(pass);
		Thread.sleep(1000L);
		loginBtn.click();
	}

	public String getToastErrorMsg() {
		waitUntilVisibilityOf(errorToast);
		return errorToast.getText();
	}
}
