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
import com.google.gson.Gson;
import com.mongodb.okr.Constants;
import com.mongodb.okr.Record;

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
			fName = fileName.substring(startIndex, fileName.length());
		}else {
			startIndex = fileName.lastIndexOf('/');
			fName = fileName.substring(startIndex, fileName.length());
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
		// TODO Auto-generated method stub
		try{
			service.files().delete(fileId).execute();
			return true;
		} catch (IOException e) {
			System.out.println("An error occured>>>>>: " + e);
			return false;
		}
	}

	public void uploadAllFiles(String path, String jsonFilePath) throws IOException {

		for (int i = 0; i <arr.length; i++) {// arr.length
			System.out.println("uploading File " + arr[i].manager.name);
			if(Constants.EXCLUDE_MANAGERS.containsKey(arr[i].manager.name)){
				arr[i].alternativeLink =Constants.EXCLUDE_MANAGERS.get(arr[i].manager.name);
				arr[i].fileId = Constants.EXCLUDE_MANAGERS_FILES_IDS.get(arr[i].manager.name);
				System.out.println("Link Value set from MAP for " + arr[i].manager.name);
			}else {
				File file = insertFile(service, arr[i].manager.name+Constants.FILE_NAME_SUFFIX, "", Constants.PARENT, 
						Constants.XL_MIME, path + arr[i].manager.name+ Constants.FILE_NAME_SUFFIX + Constants.FILE_EXTENSION);
				arr[i].fileId = file.getId();
				arr[i].alternativeLink = file.getAlternateLink();
			}
			
		}

	}
}
