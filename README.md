# JDrive

This wraps the Google Drive API to insert and delete files in Google Drive. This API let you use simple API like 
```
public File insertFile(String fileName, String description ,String parentId);
```
and removing the boiler plate code. 

This API also needs secret auth key to communicate with Google Drive Server. Make sure you follow the instructions at https://developers.google.com/drive/v2/web/quickstart/java and place the file in the resources folder. A manual authtication of access will be required at the first time to validate the access. 

For sanity checks you can use the test cases. Run 
```
gradle test
```
This will generate a test report at build/test/index.html .  
