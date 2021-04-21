package it.uniroma2.damianodeorazi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitCommitMsg {
	
	public HashMap<String, Integer> retrieveCommitMessage(ArrayList<String> ticketsID) 
			throws IOException, NoHeadException, GitAPIException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repo = builder.setGitDir(new File("C:/Users/Damiano/Downloads/daffodil/.git")).setMustExist(true).build();
		Git git = new Git(repo);
		HashMap<String, Integer> numberOfBugsFixed = new HashMap<String, Integer>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		
		for (int i = 0; i < ticketsID.size(); i++) {
			Date date = null;
			Iterable<RevCommit> log = git.log().call();
			Iterator<RevCommit> iterator = log.iterator();
			while (iterator.hasNext()) {
				RevCommit rev = iterator.next();
				String commitMsg = rev.getFullMessage();
				if (isContain(commitMsg, ticketsID.get(i))) {
					PersonIdent authorIdent = rev.getAuthorIdent();
					Date authorDate = authorIdent.getWhen();
					//Confronta le date e prende quella più recente, ignorando tutto tranne il mese e l'anno
					if (date == null || authorDate.after(date)) {
						date = authorDate;
					}
				}
			}
			//Se trovo una data con stesso mese e anno, aggiorno il valore, altrimenti aggiungo la data
			if (date != null) {
				String parsDate = dateFormat.format(date);
				if (numberOfBugsFixed.containsKey(parsDate)) {
					Integer n = numberOfBugsFixed.get(parsDate);
					n = n + 1;
					numberOfBugsFixed.put(parsDate, n);

				} else {
					numberOfBugsFixed.put(parsDate, 1);
				}
			}
		}
		git.close();
		return numberOfBugsFixed;
	}
	
	/*Metodo utile a trovare il ticket ID nel commit message*/
	private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }

}
