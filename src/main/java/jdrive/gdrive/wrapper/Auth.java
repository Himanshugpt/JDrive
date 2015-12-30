package jdrive.gdrive.wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;
import jdrive.gdrive.util.JDriveProperties;

public class Auth {
	
	/** Application name. */
	private static final String APPLICATION_NAME = "jDrive API";
	
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;
	
	/** Directory to store user credentials for this application. */
	private static java.io.File DATA_STORE_DIR ;

	/** Global instance of the scopes */
	private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE, 
			"https://spreadsheets.google.com/feeds/");
	
	private static String CLIENT_SECRET_FILE ;
	
	private Credential credential;
	
	public Auth() {
		try {
			JDriveProperties prop = new JDriveProperties();
			Map<String, String> propertiesMap = prop.getPropValues();
			DATA_STORE_DIR = new java.io.File(propertiesMap.get(JDriveProperties.DATA_STORE_DIR)); 
			CLIENT_SECRET_FILE = propertiesMap.get(JDriveProperties.CLIENT_SECRET_FILE_PATH);
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	private static Credential authorize() throws IOException {
		InputStream in = Auth.class.getResourceAsStream(CLIENT_SECRET_FILE);
		new InputStreamReader(in);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}
	
	public Drive getDriveService() throws IOException {
		if (null == credential){
			credential = Auth.authorize();
		}
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
	}
	
	public SpreadsheetService getSpreadsheetService() throws IOException, MalformedURLException, ServiceException {
		SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration-v1");
		service.setProtocolVersion(SpreadsheetService.Versions.V3);
		if (null == credential){
			credential = Auth.authorize();
		}
		service.setOAuth2Credentials(credential);
		return service;
	}

}
