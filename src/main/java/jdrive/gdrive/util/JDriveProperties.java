package jdrive.gdrive.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JDriveProperties {

	private Map<String,String> result = new HashMap<String, String>();
	private InputStream inputStream;
	
	public static final String APPLICATION_NAME = "appName";
	public static final String CLIENT_SECRET_FILE_PATH = "secretFilePath";
	public static final String DATA_STORE_DIR = "dataDirPath";
 
	public Map<String,String> getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			String appName = prop.getProperty("appName");
			String secretFilePath = prop.getProperty("secretFilePath");
			String dataDirPath = prop.getProperty("dataDirPath");
			
			result.put(APPLICATION_NAME, appName);
			result.put(CLIENT_SECRET_FILE_PATH, secretFilePath);
			result.put(DATA_STORE_DIR, dataDirPath);
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
	
}
