package jdrive.gdrive.wrapper;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author hgupta
 *
 */
public class JDriveTest {
	
	private JDrive jDrive = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		jDrive = new JDriveImp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidjDrive(){
		assertNotNull(jDrive);
	}

	@Test
	public void testInsertFile() {
		
		try {
			List<String> lines = Arrays.asList("The first line", "The second line");
			Path file = Paths.get("testFile2.txt");
			Files.write(file, lines, Charset.forName("UTF-8"));
			com.google.api.services.drive.model.File file2 = jDrive.insertFile(file.getFileName().toUri().getRawPath() , "test File Upload by Himanshu", "");
			assertNotNull(file2);
			assertNotNull(file2.getId());
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
