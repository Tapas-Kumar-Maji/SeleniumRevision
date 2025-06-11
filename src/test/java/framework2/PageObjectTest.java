package framework2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework2.pageobjects.CartPage;
import framework2.pageobjects.ConfirmationPage;
import framework2.pageobjects.LoginPage;
import framework2.pageobjects.OrdersPage;
import framework2.pageobjects.PaymentPage;
import framework2.pageobjects.ProductsPage;

public class PageObjectTest extends BaseTest {
	String product = "Banarsi Saree";

	@Test(dataProvider = "getDataFromJson", groups = { "Purchase" })
	public void endToEndTest(Map<String, String> map) throws IOException, InterruptedException {
		WebDriver driver = getDriver();
		this.product = map.get("product");

		// Login Page
		LoginPage loginPage = new LoginPage(driver);
		ProductsPage productsPage = loginPage.loginApplication(map.get("email"), map.get("password"));

		// Products Page
		productsPage.addProductToCart(product);
		CartPage cartPage = productsPage.clickCartButton();

		// Cart Page
		Assert.assertTrue(cartPage.isProductAddedPresent(product));
		PaymentPage paymentPage = cartPage.clickCheckoutButton();
		
		// Payment Page
		paymentPage.fillShippingInfo("LordCalgar@gmail.com", "Uni", "United Arab Emirates");
		ConfirmationPage confirmationPage = paymentPage.clickPlaceOrderBtn();

		// Confirmation Page
		Assert.assertEquals(confirmationPage.getConfirmationMessage(), "Thankyou for the order.".toUpperCase());

		tearDown();
	}

	@Test(dependsOnMethods = { "endToEndTest" }, dataProvider = "getDataFromJson")
	public void orderHistory(Map<String, String> map) throws InterruptedException {
		WebDriver driver = getDriver();
		this.product = map.get("product");

		// Login Page
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginApplication(map.get("email"), map.get("password"));

		// Orders Page
		OrdersPage ordersPage = loginPage.clickOrdersButton();
		Assert.assertTrue(ordersPage.isProductPresentInOrders(product));
	}

	@DataProvider
	public Object[] getDataFromJson() {
		return getJsonData(System.getProperty("user.dir") + "\\src\\test\\resources\\PurchaseOrder.json")
				.toArray();
	}

	@DataProvider
	public Object[][] getData() {
		return new Object[][] { { "tapasmaji908@gmail.com", "#1Tapasmaji", "Banarsi Saree" },
				{ "tapasmaji909@gmail.com", "#1Tapasmaji", "LG Refrigerator" } };
	}

	@DataProvider
	public Object[] getMapData() {
		Map<String, String> map = new HashMap<>();
		map.put("email", "tapasmaji908@gmail.com");
		map.put("password", "#1Tapasmaji");
		map.put("product", "Banarsi Saree");

		Map<String, String> map1 = new HashMap<>();
		map1.put("email", "tapasmaji909@gmail.com");
		map1.put("password", "#1Tapasmaji");
		map1.put("product", "LG Refrigerator");

		return new Object[] { map, map1 };
	}
}
