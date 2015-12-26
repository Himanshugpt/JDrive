# JDrive

This wraps the Google Drive API to insert and delete files in Google Drive. This API let you use simple API like 
```
public File uploadFile(String fileName, String description ,String parentId);
```
```
public boolean deleteFile (String fileId);
```
```
public void uploadAllFiles(String path, String parentId) throws IOException;
public void uploadAllFilesParallel(String path, String parentId, FileFilter filter);
```
and removing the boiler plate code. 

This API also needs secret auth key to communicate with Google Drive Server. Make sure you follow the instructions at https://developers.google.com/drive/v2/web/quickstart/java and place the file in the resources folder. A manual authentication of access will be required at the first time to validate the access. 

For sanity checks you can use the test cases. Run 
```
gradle test
```
This will generate a test report at build/test/index.html .  

## Test Cases 
In this project jUnit test cases depends on hard coded values which are specific for the current user. I have not made it configurable yet, so if you want to use it then please go through it and fix it for yourself.  

## How to build
You can download the source and go to project root directory. This project needs Grade(http://gradle.org/getting-started-gradle-java/). More help for installing gradle is provided here(https://docs.gradle.org/current/userguide/installation.html). 
From the command line run 
```
gradle build
```
This will compile the project and create a jar file named 'JDrive.jar' in the build/libs/ folder. 
