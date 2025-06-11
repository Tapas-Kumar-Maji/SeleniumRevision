package introduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Section14 extends TestUtils {

	@Test
	public void stream() {
		List<String> ls = new ArrayList<>();
		ls.add("Ranjit");
		ls.add("Ava");
		ls.add("Adams");
		ls.add("Mia");
		ls.add("Alovera");

		long count = ls.stream().filter(str -> str.startsWith("A")).count();
		System.out.println("1. Number of names that starts with A : " + count);

		// Not defining a collection explicitly.
		count = Stream.of("Andy", "Gabriel", "Anna", "Ames", "Gucci", "Armani").filter(str -> str.startsWith("A"))
				.count();
		System.out.println("2. Number of names that starts with A : " + count);

		// Print names ending with 'a', in upper case.
		ls.stream().filter(str -> str.endsWith("a")).map(str -> str.toUpperCase())
				.forEach(str -> System.out.println(str));

		// Sorting array with streams.
		String[] arr = { "Happy", "Sad", "Disturbing" };
		System.out.println("\nAfter sorting : ");
		Stream.of(arr).sorted().forEach(str -> System.out.println(str));

		// Merging two streams.
		System.out.println("\nAfter merging : ");
		Stream<String> stream = Stream.concat(Stream.of(arr), ls.stream());
		stream.forEach(str -> System.out.println(str));

		// Using assertion with stream.
		boolean match = ls.stream().anyMatch(str -> str.equals("Ava"));
		System.out.println("Match : " + match);
		Assert.assertTrue(match);

		// Converting back to list/set from stream.
		System.out.println("\nFrom Stream to List : ");
		List<String> listOfElements = Stream.concat(Stream.of(arr), ls.stream()).collect(Collectors.toList());
		System.out.println(listOfElements);

		// Print unique sorted numbers
		int[] arr1 = { 3, 2, 2, 7, 5, 1, 9, 7 };
		int[] result = Arrays.stream(arr1).distinct().sorted().toArray();
		System.out.println("\nResult : " + Arrays.toString(result));
	}

	@Test
	public void checkOrder() {
		WebDriver driver = this.initializeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

		driver.findElement(By.cssSelector("th[aria-label *= 'Veg/fruit']")).click();
		List<WebElement> fruitNames = driver.findElements(By.cssSelector("tr td:nth-child(1)"));
		
		// checking web is sorted or not.
		List<String> originalNamesList = fruitNames.stream().map(str -> str.getText()).collect(Collectors.toList());
		List<String> sortedNamesList = fruitNames.stream().map(str -> str.getText()).sorted().collect(Collectors.toList());
		Assert.assertTrue(originalNamesList.equals(sortedNamesList));
		System.out.println("The web table is sorted");

		// get price of 'Beans'
		WebElement nextButton = driver.findElement(By.cssSelector("a[aria-label='Next']"));
		while (true) {
			
			fruitNames = driver.findElements(By.cssSelector("tr td:nth-child(1)"));
			long count = fruitNames.stream().filter(ele -> ele.getText().equalsIgnoreCase("Riced")).count();

			if(count == 0) {
				try {
				nextButton.click();
				} catch (Exception e) {
					System.out.println("Fruit is probably not present");
					break;
				}
				continue;
			}
			fruitNames.stream().filter(ele -> ele.getText().equalsIgnoreCase("Rice"))
					.forEach(ele -> System.out.println(this.getPrice(ele)));
			break;
			
		}
		this.quitDriver(driver);
	}

	private String getPrice(WebElement ele) {
		return ele.findElement(By.xpath("following-sibling::td[1]")).getText();
	}

	@Test
	public void chechSearch() {
		String search = "e";
		WebDriver driver = this.initializeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

		driver.findElement(By.cssSelector("input[id='search-field']")).sendKeys(search);
		List<String> defect = new ArrayList<>();
		
		while (true) {
			List<WebElement> fruitNames = driver.findElements(By.xpath("//tr/td[1]"));
			defect.addAll(fruitNames.stream().filter(ele -> !ele.getText().toLowerCase().contains(search))
					.map(ele -> ele.getText()).collect(Collectors.toList()));

			try {
				driver.findElement(By.cssSelector("a[aria-label='Next']")).click();
			} catch (Exception e) {
				break;
			}
		}

		System.out.println("Defect list : " + defect);
		Assert.assertTrue(defect.isEmpty());
		this.quitDriver(driver);
	}
}