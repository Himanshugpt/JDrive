package jdrive.gdrive.spreadsheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.*;


public class SpreadsheetIntegration {
	
	static List<SpreadsheetEntry> spreadsheets_entries = null;
	
	private boolean findSpreadSheet(String name){
		for(SpreadsheetEntry s : spreadsheets_entries){
			if(s.getTitle().getPlainText().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	private static void updateFileContent(CellFeed cellFeed, SpreadsheetService service)
			throws ServiceException, IOException {
		
	}
	
}
