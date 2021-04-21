package it.uniroma2.damianodeorazi;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.json.JSONException;

public class Main {
	//retrieve Tickets ID from JIRA
	//retrieve Dates for fixed bugs from GITHUB
	public static void main(String[] args) throws IOException, JSONException, NoHeadException, GitAPIException, ParseException {
		RetrieveTicketsID retrieveTicketsID = new RetrieveTicketsID();
		GitCommitMsg commitMsg = new GitCommitMsg();
		CSVWriter csvWriter = new CSVWriter("C:/Users/Damiano/OneDrive/Desktop/", "Daffodiltest.csv");
		ArrayList<String> bugTickets;
		HashMap<String, Integer> bugsFixed;
		
		bugTickets = retrieveTicketsID.getTicketsID();
		bugsFixed = commitMsg.retrieveCommitMessage(bugTickets);
		csvWriter.writeCSV(bugsFixed);
		
	}
}

