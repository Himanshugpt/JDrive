package jdrive.gdrive.wrapper;

import static org.testng.AssertJUnit.*;

import java.io.File;
import java.io.FileFilter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.*;

/**
 * @author hgupta
 *
 */
public class JDriveTest {
	
	private JDrive jDrive = null;
	private com.google.api.services.drive.model.File file2 = null;
	private String parentId = "0B_8p-AccPUmcdHNKRDMxZVVyQ1E";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeTest
	public void setUp() throws Exception {
		jDrive = new JDriveImp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterTest
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidjDrive(){
		assertNotNull(jDrive);
	}

	@Test
	public void testUploadAllFilesMultiThreaded(){
		try {
			FileFilter filter = new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					if (pathname.getName().endsWith("pdf"))
						return true;
					return false;
				}
			};
			
			jDrive.uploadAllFilesParallel("/Users/hgupta/Desktop/TestFolder2", parentId, filter);
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail("uploadAllFiles Failed");
		}
	}
	
	@Test
	public void testInsertFile() {
		try {
			List<String> lines = Arrays.asList("The first line", "The second line");
			Path file = Paths.get("testFile2.txt");
			Files.write(file, lines, Charset.forName("UTF-8"));
			file2 = jDrive.uploadFile(file.getFileName().toUri().getRawPath() , "test File Upload by Himanshu", "");
			assertNotNull(file2);
			assertNotNull(file2.getId());
			assertTrue(jDrive.deleteFile(file2.getId()));
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDeleteFile(){
		assertFalse(jDrive.deleteFile("randomId")); 
	}
	
	@Test
	public void testUploadAllFiles(){
		System.out.println("test Upload All Files");
		try {
			jDrive.uploadAllFiles("/Users/hgupta/Desktop/TestFolder", parentId);
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail("uploadAllFiles Failed");
		}
	}
	
	

}
