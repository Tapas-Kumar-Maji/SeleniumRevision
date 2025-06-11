package bigbasket.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utilities {
	// only write utilities that do not require webdriver object.

	public Properties getConfigProperty() throws IOException {
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\Config.properties");
		properties.load(fis);
		fis.close();
		return properties;
	}


}

