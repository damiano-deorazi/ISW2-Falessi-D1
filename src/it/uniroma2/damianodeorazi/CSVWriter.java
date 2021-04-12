package it.uniroma2.damianodeorazi;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.json.JSONException;

public class CSVWriter {
	
	private String csvPath = null;
	private String fileName = null;
	
	public CSVWriter(String csvPath, String fileName) {
		this.csvPath = csvPath;
		this.fileName = fileName;
	}
	
	public void writeCSV(HashMap<String, Integer> bugsFixed) throws IOException, JSONException, NoHeadException, 
	GitAPIException, ParseException {		
		FileWriter fileWriter = null;
		try { 
			fileWriter = new FileWriter(csvPath + fileName);
			fileWriter.append("Date,# bugs fixed\n");
			Iterator it = bugsFixed.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
				fileWriter.append(pair.getKey().toString());
				fileWriter.append(",");
				fileWriter.append(pair.getValue().toString());
	            fileWriter.append("\n");
			    it.remove(); // avoids a ConcurrentModificationException
			}
		} catch (Exception e) {
			System.out.println("Error in csv writer");
            e.printStackTrace();
        } finally {
            try {
	               fileWriter.flush();
	               fileWriter.close();
	            } catch (IOException e) {
	               System.out.println("Error while flushing/closing fileWriter !!!");
	               e.printStackTrace();
	        }
	    }	
	}
}
