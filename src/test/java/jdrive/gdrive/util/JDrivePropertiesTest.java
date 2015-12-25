package jdrive.gdrive.util;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.*;

public class JDrivePropertiesTest {

	JDriveProperties prop = null;
	
	@BeforeSuite
	public void setUp() throws Exception {
		prop = new JDriveProperties();
	}

	@AfterTest
	public void tearDown() throws Exception {
		prop = null;
	}

	@Test
	public void testGetPropValues() {
		assertNotNull(prop);
		try {
			assertNotNull(prop.getPropValues());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	 
}
