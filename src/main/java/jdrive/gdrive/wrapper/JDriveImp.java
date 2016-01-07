/**
*
*/
package jdrive.gdrive.wrapper;

import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.client.Query;
import com.google.gdata.util.*;

/**
* @author hgupta
*
*/
public class JDriveImp implements JDrive {

	private Drive drive = null;
	private URL SPREADSHEET_FEED_URL = null;
	private SpreadsheetService spradsheet_service = null;

	public JDriveImp() {
		try {
			Auth auth = new Auth();
			this.drive = auth.getDriveService();
			this.spradsheet_service = auth.getSpreadsheetService();
			SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		} catch (Exception e) {
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
			File file = drive.files().insert(body, mediaContent).setConvert(true).execute();
			return file;
		} catch (IOException e) {
			System.out.println("An error occured>>>>>: " + e);
			return null;
		}
	}

	@Override
	public boolean deleteFile(String fileId) {
		try{
			drive.files().delete(fileId).execute();
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
	public List<SpreadsheetEntry> getAllSpreadSheets() {
		List<SpreadsheetEntry> spreadsheets_entries = null;
		try{
			// Make a request to the API and get all spreadsheets.
			SpreadsheetFeed feed = spradsheet_service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
			spreadsheets_entries  = feed.getEntries();

			if (spreadsheets_entries.size() == 0) {
				System.out.println("No files found. Exiting");
			}

			for (SpreadsheetEntry ent : spreadsheets_entries) {
				System.out.println(ent.getTitle().getPlainText() + " --> " + ent.getId());
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return spreadsheets_entries;
	}

	@Override
	public List<SpreadsheetEntry> findSpreadSheet(String name){
		List<SpreadsheetEntry> spreadsheets_entries = null;
		List<SpreadsheetEntry> spreadsheets_entries_ = new ArrayList<SpreadsheetEntry>();
		try{
			//Query query = new Query(SPREADSHEET_FEED_URL);
			//query.setFullTextQuery(name);

			// Make a request to the API and get all spreadsheets.
			SpreadsheetFeed feed = spradsheet_service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
			spreadsheets_entries  = feed.getEntries();

			if (spreadsheets_entries.size() == 0) {
				System.out.println("No files found. Exiting");
			}

			for (SpreadsheetEntry ent : spreadsheets_entries) {
				if(ent.getTitle().getPlainText().toLowerCase().contains(name.toLowerCase())){
					spreadsheets_entries_.add(ent);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return spreadsheets_entries;
	}

	public void updateFileContent(WorksheetEntry worksheet, int row, int column, String content)
				throws ServiceException, IOException {
					try{
						URL cellFeedUrl = worksheet.getCellFeedUrl();
						CellFeed cellFeed = spradsheet_service.getFeed(cellFeedUrl, CellFeed.class);
						CellEntry cell = new CellEntry(row, column, content);
						cellFeed.insert(cell);
					}catch(Exception e){
						e.printStackTrace();
					}

		}

  public boolean addWorksheet(SpreadsheetEntry spreadsheet, WorksheetEntry worksheet){
		try {
			URL worksheetFeedUrl = spreadsheet.getWorksheetFeedUrl();
			this.spradsheet_service.insert(worksheetFeedUrl, worksheet);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	// public static void main(String[] args) {
	// 	try{
	// 		JDrive jdrive = new JDriveImp();
	// 		List<SpreadsheetEntry> entries =	jdrive.findSpreadSheet("name");
	// 		WorksheetFeed worksheetFeed = spradsheet_service.getFeed(entries.get(0).getWorksheetFeedUrl(), WorksheetFeed.class);
	// 		List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
	// 		WorksheetEntry worksheet = worksheets.get(0);
	// 		jdrive.updateFileContent(worksheet, 1,1,"TestData");
	// 	}catch(Exception e){
	// 		e.printStackTrace();
	// 	}
	//
	//
	// }

}
