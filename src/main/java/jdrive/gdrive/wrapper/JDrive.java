/**
 *
 */
package jdrive.gdrive.wrapper;

import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import com.google.api.services.drive.model.File;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

/**
 * @author hgupta
 *
 */
public interface JDrive {

	/**
	 * Inserts a file to the Google Drive of the associated
	 * account. It returns the File Object which has other
	 * details like id and path of the file.
	 * @param fileName
	 * @param description
	 * @param parentId
	 * @return
	 */
	public File uploadFile(String fileName, String description ,String parentId);

	/**
	 * This Method deleted the file corresponding to the
	 * provided fileId. If no file is found to corresponding fileId
	 * then function returns false otherwise it returns true.
	 * @param fileId
	 * @return
	 */
	public boolean deleteFile(String fileId);

	/**
	 * Uploads all the file in the provided path
	 * @param path
	 * @param jsonFilePath
	 * @throws IOException
	 */
	public void uploadAllFiles(String path, String parentId, FileFilter filter) throws IOException;

	/**
	 * This method uploads all the files in the directory.
	 * It uses insertFile method and uploads files using a thread pool.
	 * @param path
	 * @param parentId
	 * @param filter
	 */
	public void uploadAllFilesParallel(String path, String parentId, FileFilter filter);

	/**
	 * [getAllSpreadSheets description]
	 */
	public List<SpreadsheetEntry> getAllSpreadSheets();

 /**
  * [findSpreadSheet description]
  * @param  name [description]
  * @return      [description]
  */
	public List<SpreadsheetEntry> findSpreadSheet(String name);


 /**
  * updateFileContent
	*/
  public void updateFileContent(WorksheetEntry worksheet, int row, int column, String content)
				throws ServiceException, IOException;


}
