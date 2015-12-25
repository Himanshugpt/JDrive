package jdrive.gdrive.wrapper;

import static org.testng.AssertJUnit.*;

import java.io.IOException;

import org.testng.annotations.*;

import com.google.api.services.drive.Drive;

import junit.framework.AssertionFailedError;

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
			assertNotNull(drive);
		}catch (IOException e){
			assertNotNull(drive);
		}
		
	}
}
