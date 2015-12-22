package jdrive.gdrive.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JDrivePropertiesTest {

	JDriveProperties prop = null;
	
	
	@Before
	public void setUp() throws Exception {
		prop = new JDriveProperties();
	}

	@After
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
