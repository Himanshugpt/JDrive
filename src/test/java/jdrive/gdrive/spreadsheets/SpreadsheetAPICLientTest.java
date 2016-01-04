package jdrive.gdrive.spreadsheets;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import jdrive.gdrive.wrapper.JDrive;
import jdrive.gdrive.wrapper.JDriveImp;
import junit.framework.Assert;


public class SpreadsheetAPICLientTest {
	
	private JDrive jDrive = null;
	
	@BeforeTest
	public void setUp() throws Exception {
		try{
			jDrive = new JDriveImp();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

  @Test
  public void findSpreadSheet() {
	  Assert.assertNotNull(jDrive);
	 jDrive.getAllSpreadSheets();
  }

  /*
  @Test
  public void updateFileContent() {
    throw new RuntimeException("Test not implemented");
  }
  */
}
