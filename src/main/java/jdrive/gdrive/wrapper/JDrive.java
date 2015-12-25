/**
 * 
 */
package jdrive.gdrive.wrapper;

import java.io.FileFilter;
import java.io.IOException;

import com.google.api.services.drive.model.File;

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
	public File insertFile(String fileName, String description ,String parentId);
	
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
	public void uploadAllFiles(String path, String parentId) throws IOException;
	
	/**
	 * This method uploades all the files in the directory. 
	 * It uses insertFile method and uploads files using a thread pool.
	 * @param path
	 * @param parentId
	 * @param filter
	 */
	public void uploadAllFilesMultiThreaded(String path, String parentId, FileFilter filter);
	
}
