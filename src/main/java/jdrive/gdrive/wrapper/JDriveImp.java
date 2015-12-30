/**
 * 
 */
package jdrive.gdrive.wrapper;

import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;

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
	public File uploadFile(String fileName, String description, String parentId) {
		File body = new File();

		String fName = "";

		//for linux and windows 
		int startIndex = fileName.lastIndexOf('\\');
		if(startIndex >= 0){
			fName = fileName.substring(startIndex+1, fileName.length());
		}else {
			startIndex = fileName.lastIndexOf('/');
			fName = fileName.substring(startIndex+1, fileName.length());
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
	
	public void uploadAllFilesParallel(String path, String parentId, FileFilter filter){
		
		class UploadFile implements Runnable{
			String fileName, parentId;
			
			UploadFile(String fileName, String parentId){
				this.fileName = fileName;
				this.parentId = parentId;
			}
			
			@Override
			public void run() {
				File f = uploadFile(fileName, "", parentId);
				if(f == null)
					System.out.println("FIle cant be uploaded");
			}
		}
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		if(parentId== null) parentId = "";
		java.io.File folder = new java.io.File(path);
		java.io.File[] files = folder.listFiles(filter);
		long startTime = System.nanoTime();
		
		for (int i = 0; i <files.length; i++) {
			if(files[i].isFile()){
				executorService.execute(new UploadFile(files[i].getAbsolutePath(), parentId));
			}
		}
		executorService.shutdown();
		
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime) + " ns"); 
	}

	public void uploadAllFiles(String path, String parentId, FileFilter filter) throws IOException {
		if(parentId== null) parentId = "";
		java.io.File folder = new java.io.File(path);
		java.io.File[] files = folder.listFiles(filter);
		long startTime = System.nanoTime();
		for (int i = 0; i <files.length; i++) {
			if(files[i].isFile()){
				System.out.println("uploading " + files[i].getName());
				File f = this.uploadFile(files[i].getAbsolutePath(),"", parentId);
			}
		}
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime) + " ns"); 

	}

	@Override
	public void getAllSpreadSheets() {
		SpreadsheetService service = new Auth().getSpreadsheetService();
		// TODO Auto-generated method stub
		URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

		// Make a request to the API and get all spreadsheets.
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		spreadsheets_entries = feed.getEntries();
		
		if (spreadsheets_entries.size() == 0) {
			System.out.println("No files found. Exiting");
		}
		
		for (SpreadsheetEntry ent : spreadsheets_entries) {
			System.out.println(ent.getTitle().getPlainText() + " --> " + ent.getId());
		}
	}
	
}
