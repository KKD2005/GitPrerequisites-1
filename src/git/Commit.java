package git;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;

public class Commit {
	private Commit nextCommit;
	private Commit parentCommit;
	
	private Tree tree;
	private String summary;
	private String author;
	private String date;
	public Commit (String two, String three, Commit parent) throws IOException {
		summary  = two;
		author = three;
		parentCommit = parent;
		if (parentCommit!=null) {
		parentCommit.setNext(this);
		}
		 File objects = new File ("objects");
	        if (objects.exists()==false) {
	        	objects.mkdir();
	        }
	        
	        date = java.time.LocalDate.now().toString();
	        ArrayList <String>entries = new ArrayList <String>();
	        BufferedReader in = new BufferedReader (new FileReader ("index"));
	        ArrayList <String>delete = new ArrayList<String >();
	        while (in.ready()) {
	        	String line = in.readLine();
	        	if (line.charAt(0)!='*') {
	        	int colon = line.indexOf(':');
	        	String filename = line.substring(0,colon-1);
	        	String Sha= line.substring(colon+2);
	        	entries.add("blob : "+Sha+" "+filename); 
	        	}else {
	        		if (line.substring(0,8).equals("*edited*")){
	        			
	        			delete.add ( line.substring(9));
	        			
	        			String contents = "";
	        			BufferedReader old = new BufferedReader (new FileReader (line.substring(9)));
	        	        while (old.ready()) {
	        	        	String nextLine = old.readLine();
	        	        	contents=contents+nextLine+"\n";
	        	        }
	        	        old.close();
	        	        if (contents.length()!=0) {
	        	        contents = contents.substring(0, contents.length()-1);
	        	        }
	        	        String Sha = generateSha1String(contents);
	        			entries.add("blob : "+Sha+" " +line.substring(9));
	        		} else {
	        			delete.add (line.substring(10));
	    	        	
	        		}
	        	}
	        }
	        in.close();
	        
	        if (delete.size()==0) {
	        if (parentCommit!=null) {
	        	entries.add("tree : "+ parentCommit.getTree().getFileName()+" ");
	        }
	        }else {
	        	deleteMain(delete, entries);
	        }
	        tree = new Tree (entries);
	        File idx = new File ("index");
	        if (idx.exists()) {
	        	idx.delete();
	        }
	        File idx2 = new File ("index");
	        idx2.createNewFile();

	        
	        	
	}
	public void setNext(Commit next) {
		
		nextCommit = next;
		
	}
	public String generateSha1String(){
		String contents = getContents();
		try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");
 
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(contents.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
 
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
 
            // return the HashText
            return hashtext;
		}
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
	}
		public String generateSha1String(String input){
			String contents = input;
			try {
	            // getInstance() method is called with algorithm SHA-1
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	 
	            // digest() method is called
	            // to calculate message digest of the input string
	            // returned as array of byte
	            byte[] messageDigest = md.digest(contents.getBytes());
	 
	            // Convert byte array into signum representation
	            BigInteger no = new BigInteger(1, messageDigest);
	 
	            // Convert message digest into hex value
	            String hashtext = no.toString(16);
	 
	            // Add preceding 0s to make it 32 bit
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	 
	            // return the HashText
	            return hashtext;
	        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
	
	
	public String getDate() {
		return date;
	}
	public String getContents() {
		String contents = new String ("");
		contents = contents+summary+ "\n";
		contents = contents+date+ "\n";
		contents = contents + author+ "\n";
		if (parentCommit ==null) {

		} else{
			contents=contents+ "objects/" + parentCommit.generateSha1String() ;
		}
		return contents;
	}
	public void writeFile() throws IOException {
		String contents = new String ("");
		contents = contents+tree.getFileName()+ "\n";
		if (parentCommit ==null) {
			contents=contents+ "\n";
		} else{
			contents=contents+ "objects/" + parentCommit.generateSha1String() + "\n";
		}
		if (nextCommit ==null) {
			contents=contents+ "\n";
		} else{
			contents=contents+"objects/"+nextCommit.generateSha1String() + "\n";
		}
		contents=contents+author + "\n";
		contents=contents+date + "\n";
		contents=contents+this.summary;
		String fileName = generateSha1String();
		File commit = new File ("objects/"+fileName);
		commit.createNewFile();
		 Path p = Paths.get("objects/"+fileName);
	        try {
	            Files.writeString(p, contents, StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	public Tree getTree() { 
		return tree;
	}
	
	
	public void deleteRecursion (String ogTree, ArrayList<String> filesToDelete, ArrayList<String> entries, int numberOfTimes) throws IOException {
		String treeSha = new String ("");
		BufferedReader r = new BufferedReader (new FileReader ("objects/"+ogTree));
        while (r.ready()) {
        	String line = r.readLine();
        	if (line.substring(0,4).equals ("tree")) {
        		treeSha = line.substring(7);
        		treeSha = treeSha.substring(0,treeSha.length()-1);
        	}else {
        		String blobName = line.substring(48);
        		if (filesToDelete.contains(blobName)) {
        			
        		}else {
        			entries.add(line);
        		}
        	}
        }
        r.close();
        if (treeSha.length()==0) {
        	
        } else if (numberOfTimes==0) {
        	entries.add("tree : "+treeSha + " ");
        	
        } else {
        	numberOfTimes--;
        	deleteRecursion (treeSha, filesToDelete, entries,numberOfTimes);
        }
	}
	
	public void deleteMain(ArrayList<String> filesToDelete, ArrayList<String> entries) throws IOException {
		int numberOfTimes = getFirstFile(filesToDelete, parentCommit.getTree().getFileName(), 0, 0);
		 deleteRecursion (parentCommit.getTree().getFileName(), filesToDelete,entries,numberOfTimes);
	}
	
	public int getFirstFile (ArrayList<String>filesToDelete,String ogTree, int counterFiles, int counterTrees) throws IOException {
		int total = filesToDelete.size();
		String treeSha="";
		
			BufferedReader r = new BufferedReader (new FileReader ("objects/"+ogTree));
	        while (r.ready()) {
	        	String line = r.readLine();
	        	if (line.substring(0,4).equals ("tree")) {
	        		treeSha = line.substring(7);
	        		treeSha = treeSha.substring(0,treeSha.length()-1);
	        	}else {
	        		String blobName = line.substring(48);
	        		if (filesToDelete.contains(blobName)) {
	        			counterFiles++;
	        			
	        		}
	        	}
	        }
	        r.close();
			
		
		if (treeSha.length()==0 || counterFiles==total) {
		return counterTrees;
		} else {
			counterTrees++;
			return getFirstFile(filesToDelete, treeSha,counterFiles, counterTrees);
		}
	}
}