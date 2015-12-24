/**
 * 
 */
package jdrive.gdrive.wrapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

/**
 * @author hgupta
 *
 */
public class JDriveImp implements JDrive {

	Drive service = null;

	public JDriveImp() {
		try {
			this.service = new Auth().getDriveService();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public File insertFile(String fileName, String description, String parentId) {
		File body = new File();

		String fName = "";

		//for linux and windows 
		int startIndex = fileName.lastIndexOf('\\');
		if(startIndex >= 0){
			fName = fileName.substring(startIndex+1, fileName.length());
		}else {
			startIndex = fileName.lastIndexOf('/');
			fName = fileName.substring(startIndex+1, fileName.length()-1);
		}

		body.setTitle(fName);
		body.setDescription(description);
		String mimeType = null;
		//body.setMimeType(mimeType);

		// Set the parent folder.
		if (parentId != null && parentId.length() > 0) {
			body.setParents(Arrays.asList(new ParentReference().setId(parentId)));
		}
		// File's content.
		java.io.File fileContent = new java.io.File(fileName);
		FileContent mediaContent = new FileContent(mimeType, fileContent);
		try {
			File file = service.files().insert(body, mediaContent).setConvert(true).execute();
			return file;
		} catch (IOException e) {
			System.out.println("An error occured>>>>>: " + e);
			return null;
		}
	} 

	@Override
	public boolean deleteFile(String fileId) {
		try{
			service.files().delete(fileId).execute();
			return true;
		} catch (IOException e) {
			System.out.println("An error occured>>>>>: " + e);
			return false;
		}
	}
	
	public void uploadAllFilesMultiThreaded(String path, String parentId){
		class UploadFile implements Runnable{
			
			String fileName, parentId;
			
			UploadFile(String fileName, String parentId){
				this.fileName = fileName;
				this.parentId = parentId;
			}
			
			@Override
			public void run() {
				File f = insertFile(fileName, "", parentId);
			}
			
		}
	}

	public void uploadAllFiles(String path, String parentId) throws IOException {
		if(parentId== null) parentId = "";
		java.io.File folder = new java.io.File(path);
		java.io.File[] files = folder.listFiles();
		long startTime = System.nanoTime();
		for (int i = 0; i <files.length; i++) {
			if(files[i].isFile()){
				System.out.println("uploading " + files[i].getName());
				File f = this.insertFile(files[i].getAbsolutePath(),"", parentId);
			}
		}
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime) + " ns"); 

	}
}
