package it.uniroma2.damianodeorazi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.json.JSONException;

public class Main {
	//retrieve Tickets ID from JIRA
	//retrieve Dates for fixed bugs from GITHUB
	public static void main(String[] args) throws IOException, JSONException, NoHeadException, GitAPIException {
		RetrieveTicketsID retrieveTicketsID = new RetrieveTicketsID();
		GitCommitMsg gitCommitMsg = new GitCommitMsg();
		HashMap<Date, Integer> bugsFixed = null;
		ArrayList<String> ticketsID = null;
		
		ticketsID = retrieveTicketsID.getTicketsID();
		bugsFixed = gitCommitMsg.retrieveCommitMessage(ticketsID);
		
		ArrayList<Date> dates = new ArrayList<Date>(bugsFixed.keySet());
		Collections.sort(dates, new Comparator<Date>() {
			public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }	
		});
		FileWriter fileWriter = null;
		try { 
			fileWriter = new FileWriter("C:/Users/Damiano/OneDrive/Desktop/DaffodilBugsFix.csv");
			fileWriter.append("Date,# bugs fixed\n");
			for (int i = 0; i < bugsFixed.size(); i++) {
				Date data = dates.get(i);
				fileWriter.append(data.toString());
				fileWriter.append(",");
				fileWriter.append(bugsFixed.get(data).toString());
	            fileWriter.append("\n");
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

