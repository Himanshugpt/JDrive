package jdrive.gdrive.wrapper;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.services.drive.Drive;

import junit.framework.AssertionFailedError;

public class AuthTest {

	private Auth auth= null;
	
	@Before
	public void setUp() throws Exception {
		try{
			auth = new Auth();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@After
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
