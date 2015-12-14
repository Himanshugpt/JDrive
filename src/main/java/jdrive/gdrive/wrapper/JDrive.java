/**
 * 
 */
package jdrive.gdrive.wrapper;

import java.util.Arrays;
import java.util.List;


import com.google.api.services.drive.model.File;

/**
 * @author hgupta
 *
 */
public interface JDrive {

	/**
	 * 
	 * @param fileName
	 * @param description
	 * @param parentId
	 * @return
	 */
	public File insertFile(String fileName, String description ,String parentId);
	
	public void deleteFile(String fileId);
	
}