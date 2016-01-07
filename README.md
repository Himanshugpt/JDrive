# JDrive

This wraps the Google Drive API to insert and delete files in Google Drive. It also eases access to spreaadsheets. This API removes the boiler plate code. In addition you can use single service to access multiple Google Drive and Spreadsheets. 

This API also needs secret auth key to communicate with Google Drive Server. Make sure you follow the instructions at https://developers.google.com/drive/v2/web/quickstart/java and place the file in the resources folder. A manual authentication of access will be required at the first time to validate the access. 

###Drive API
```
public File uploadFile(String fileName, String description ,String parentId);
public boolean deleteFile (String fileId);
```

You can also upload multiple files from a given path. This API uses FileFilter so you can make use of regex to filter files. 
```
public void uploadAllFiles(String path, String parentId) throws IOException;
public void uploadAllFilesParallel(String path, String parentId, FileFilter filter);
```
###SpreadSheet API
You can list, find spreadsheets and add or delete worksheets. 
```
public List<SpreadsheetEntry> getAllSpreadSheets();
public List<SpreadsheetEntry> findSpreadSheet(String name);
public boolean addWorksheet(SpreadsheetEntry spredsheet, WorksheetEntry worksheet);
public boolean deleteWorksheet(WorksheetEntry worksheet);
```
You can also edit the content of the worksheet simply using a single API call. 
```
public void updateFileContent(WorksheetEntry worksheet, int row, int column, String content)
			throws ServiceException, IOException;
```
Sample Code:
```
public static void chnageWorksheetContent() {
		try{
			JDrive jdrive = new JDriveImp();
			SpreadsheetService service = new Auth().getSpreadsheetService();
			List<SpreadsheetEntry> entries =	jdrive.findSpreadSheet("name");
			WorksheetFeed worksheetFeed = service.getFeed(entries.get(0).getWorksheetFeedUrl(), WorksheetFeed.class);
			List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
			WorksheetEntry worksheet = worksheets.get(0);
			jdrive.updateFileContent(worksheet, 1,1,"TestData");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
```
## How to build
You can download the source and go to project root directory. This project needs [Gradle](http://gradle.org/getting-started-gradle-java/). More help for installing gradle is provided [here](https://docs.gradle.org/current/userguide/installation.html). 
From the command line run 
```
gradle build
```
This will compile the project and create a jar file named *'JDrive.jar'* in the build/libs/ folder. 

### Test Cases 
In this project jUnit test cases depends on hard coded values which are specific for the current user. I have not made it configurable yet, so if you want to use it then please go through it and fix it for yourself.  

For sanity checks you can use the test cases. Run 
```
gradle test
```
This will generate a test report at build/test/index.html .  

If you have any suggestions then open a issue and I will try working on it. 
