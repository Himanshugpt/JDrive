package jdrive.gdrive.wrapper;

import static org.testng.AssertJUnit.*;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.*;

import com.google.api.services.drive.Drive;
import com.google.gdata.client.spreadsheet.*;
public class AuthTest {

	private Auth auth= null;
	
	@BeforeTest
	public void setUp() throws Exception {
		try{
			auth = new Auth();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() throws Exception {
		auth = null;
	}

	
	@Test
	public void testAuth() {
		Drive drive = null;
		try{
			drive = auth.getDriveService();
			Assert.assertNotNull(drive);
		}catch (IOException e){
			assertNotNull(drive);
		}
		
	}
	
	@Test
	public void testSpreadsheetService(){
		SpreadsheetService spreadsheetService = null;
		try {
			spreadsheetService = auth.getSpreadsheetService();
		}catch(Exception e){
			Assert.assertNotNull(spreadsheetService);
		}
	}
}
