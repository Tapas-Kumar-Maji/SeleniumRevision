package framework2;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import framework2.pageobjects.CartPage;
import framework2.pageobjects.LoginPage;
import framework2.pageobjects.ProductsPage;

public class ErrorValidationTest extends BaseTest{

	@Test(groups = { "ErrorHandling" })
	public void loginErrorToastMsg() throws IOException, InterruptedException {
		WebDriver driver = getDriver();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithIncorrectCredentials("tapasmaji908@gmail.com", "@WrongPass");
		String errorToastMsg = loginPage.getToastErrorMsg();
		System.out.println("Error :" + errorToastMsg);
		Assert.assertEquals(errorToastMsg, "Incorrect email or password.");
	}

	@Test
	public void wrongProductIsNotAdded() throws IOException, InterruptedException {
		WebDriver driver = getDriver();

		// Login Page
		LoginPage loginPage = new LoginPage(driver);
		ProductsPage productsPage = loginPage.loginApplication("tapasmaji909@gmail.com", "#1Tapasmaji");

		// Products Page
		String product = "LG Refrigerator";
		productsPage.addProductToCart(product);
		CartPage cartPage = productsPage.clickCartButton();

		// Cart Page
		Assert.assertFalse(cartPage.isProductAddedPresent("Wrong Product Name"));
	}
}