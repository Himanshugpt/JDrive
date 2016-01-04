package jdrive.gdrive.util;

import org.testng.Assert;
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
		Assert.assertNotNull(prop);
		try {
			Assert.assertNotNull(prop.getPropValues());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	 
}
