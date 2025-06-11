package introduction2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Section14 {
	WebDriver driver = null;
	List<String> ls = null;
	String[] arr = new String[] { "Arjun", "Payel", "Khalifa", "Ass", "Alex" };

	@BeforeClass
	public void initializeDriver() {
		Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\tapos\\chromedriver.exe");
		driver = new ChromeDriver(options);
		// driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2L));
		ls = Arrays.asList(arr);
	}

	@AfterClass
	public void quitDriver() throws InterruptedException {
		Thread.sleep(5000L);
		driver.quit();
	}

	@Test(enabled = false)
	public void filterStream() {
		List<String> names = new ArrayList<>();
		names.add("Arjun");
		names.add("Payel");
		names.add("Khalifa");
		names.add("Ass");
		names.add("Alex");

		Long count = names.stream().filter(s -> s.startsWith("A")).count();
		// System.out.println("names that start with A : " + count);
		
		count = Stream.of("Arjun", "Payel", "Khalifa", "Ass", "Alex").filter(s -> s.startsWith("A")).count();
		// System.out.println("names that start with A : " + count);
		
		//terminal operation will work if filter returns true.
		count = Stream.of("Arjun", "Payel", "Khalifa", "Ass", "Alex").filter(s -> {
			s.startsWith("A");
			return true;
		}).count();
		// System.out.println("names that start with A : " + count);

		Stream.of("Arjun", "Payel", "Khalifa", "Ass", "Alex").filter(s -> s.length() > 4)
				.forEach(s -> System.out.println(s));
		Stream.of("Arjun", "Payel", "Khalifa", "Ass", "Alex").filter(s -> s.length() > 4)
				.limit(2).forEach(s -> System.out.println(s));
	}

	@Test(enabled = false)
	public void mapStreams() {
		Stream.of("Arjun", "Payel", "Khalifa", "Ass", "Alex").filter(s -> s.length() > 4).map(s -> s.toUpperCase())
				.forEach(s -> System.out.println(s));
	}

	@Test(enabled = false)
	public void ArrayDotAsListBehaviour() {
		arr[2] = "Mia";
		arr = new String[3];
		arr[0] = "Ava";
		System.out.println("list : " + ls);
		System.out.println("array : " + arr[0] + "," + arr[1] + "," + arr[2]);
	}

	@Test(enabled = false)
	public void streamSorted() {
		List<String> list = Arrays.asList("Abrjun", "Payel", "Khalifa", "Ass", "Aalex");
		list.stream().filter(s -> s.startsWith("A")).sorted().forEach(s -> System.out.println(s.toUpperCase()));
	}

	@Test(enabled = false)
	public void streamConcat() {
		List<String> list = Arrays.asList("Abhi", "Payel", "Khalifa", "Ass", "Janet");
		List<int[]> lsInt = Arrays.asList(new int[] { 1, 2, 8, 6 });
		Stream<String> newStream = Stream.concat(list.stream(), ls.stream());
		newStream.sorted().forEach(s -> System.out.println(s));
	}

	@Test(enabled = false)
	public void streamAnyMatch() {
		List<String> list = Arrays.asList("Abhi", "Payel", "Khalifa", "Ass", "Janet");
		boolean flag = list.stream().anyMatch(s -> s.equalsIgnoreCase("khalifa"));
		Assert.assertTrue(flag);
	}

	@Test(enabled = false)
	public void streamCollect() {
		List<String> list = Stream.of("Abhi", "Payel", "Khalifa", "Asa", "Janet").filter(s -> s.endsWith("a")).sorted()
				.collect(Collectors.toList());
		System.out.println("List : " + list);
	}

	@Test(enabled = false)
	public void streamUnique() {
		List<Integer> list = Arrays.asList(3, 2, 2, 1, 5, 6, 5, 3, 1);
//		list.stream().distinct().sorted().forEach(s -> System.out.println(s));

		int num = list.stream().distinct().sorted().limit(3).collect(Collectors.toList()).get(2);
		System.out.println(num);
	}

	@Test(enabled = true)
	public void checkOrder() {
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		driver.findElement(By.cssSelector("th[aria-label ^= 'Veg/fruit']")).click();
		List<WebElement> elements = driver.findElements(By.cssSelector("tr > td:has(+td+td)"));

		List<String> namesOri = elements.stream().map(s -> s.getText()).toList();
		List<String> namesSor = namesOri.stream().sorted().toList();
		Assert.assertEquals(namesOri, namesSor);
	}

	@Test
	public void findPrice() {
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		String price = null;
		while(true) {
			List<WebElement> list = driver.findElements(By.cssSelector("td:has(+td+td)"));
			List<WebElement> names = list.stream().filter(s -> s.getText().equalsIgnoreCase("Apple")).toList();
			if (names.size() > 0) {
				System.out.println(names.get(0).getText());
				price = names.get(0).findElement(By.xpath("following-sibling::td[1]")).getText();
				// price = names.get(0).findElement(By.cssSelector("+td")).getText();  throws InvalidSelectorException
				break;
			}
			driver.findElement(By.cssSelector("a[aria-label = 'Next']")).click();
		}
		System.out.println("Price  : " + price);
	}

	@Test
	public void checkSearchFunctionality() {
		String veg = "p";
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");
		driver.findElement(By.xpath("//input[@id = 'search-field']")).sendKeys(veg);
		List<WebElement> filteredList = driver
				.findElements(By.xpath("//tbody/tr/td[following-sibling::td [following-sibling::td]]"));
		long count = filteredList.stream().filter(s -> {
			if (s.getText().toLowerCase().contains(veg.toLowerCase())) {
				return false;
			}
			return true;
		}).count();
		Assert.assertFalse(count > 0);
	}
}
