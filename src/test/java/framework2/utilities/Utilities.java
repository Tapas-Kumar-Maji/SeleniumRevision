package framework2.utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilities {

	/**
	 * Parses JSON data
	 * 
	 * @param jsonFilePath
	 * @return List<HashMap<String, String>>
	 */
	public List<HashMap<String, String>> getJsonData(String jsonFilePath) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<HashMap<String, String>>>() {
			});
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Takes screenshot of entire web page and stores as a file.
	 * 
	 * @param driver
	 * @param screenshotFilePath
	 */
	public void getScreenshot(WebDriver driver, String screenshotFilePath) throws IOException {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File(screenshotFilePath));
	}

}
