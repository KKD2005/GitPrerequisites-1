package git;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Index {
	/**
	 * Can "initialize" a project which
		creates an empty file named 'index'
		creates a directory named 'objects'
		Can add Blobs given a filename
		Creates a blob for the given filename
		Saves the filename and Blob SHA1 as key/value pairs
		Appends that pair to a list in a file named 'index'
		Can remove Blobs given a filename
		Removes the filename and Blob SHA1 from the key/value pair
		Deletes the blob saved in the 'objects' folder
	 */
	
	HashMap<String, String> map;
	
	public Index() { 
		this.map = new HashMap<String, String>();
	}

	public void initProject()  {
		// make objects folder - KONNIE EDITED - PREVIOUSLY WAS NOT CREATING FOLDER WHEN TESTING
		File obj = new File ("objects");

			obj.mkdir();
			
			for (File file: Objects.requireNonNull(obj.listFiles())) {
	        	if (!file.isDirectory()) {
	        		file.delete();
	        	}
	        }
		
		// make index file - KONNIE EDITED - PREVIOUSLY WAS NOT MAKING AN INDEX FILE PROPERLY
		File idx = new File ("index");
		if (idx.exists()) {
	        idx.delete();
	        }
	        File idx2=new File ("index");
	        try {
				idx2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void updateIndex() throws IOException {
		// get contents of map in a string
		String s = new String("");
		for (Map.Entry<String, String> e : map.entrySet()) {
			s = s+(e.getKey() + " : " + e.getValue()) + "\n";
		}
		s=s.substring(0,s.length()-1);
		// overwrite contents of index file - KONNIE FIXED - IT DIDN'T WORK EARLIER
		File idx = new File("index"); 
        if (idx.exists()) {
        idx.delete();
        }
        File idx2=new File ("index");
        idx2.createNewFile();
        Path p = Paths.get("index");
      
        try {
            Files.writeString(p, s, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	}
	
	public void addBlob(String filename) {
		Blob b = new Blob(filename);
		this.map.put(filename, b.getShaOne());
		try {
			updateIndex();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void delete(String filename) throws IOException {
		
			File index = new File ("index");
			if (!index.exists()) {
				index.createNewFile();
			}
			String contents = "";
			BufferedReader in = new BufferedReader (new FileReader ("index"));
	        while (in.ready()) {
	        	String line = in.readLine();
	        	contents=contents+line+"\n";
	        }
	        in.close();
	        
	        contents+="*deleted* "+filename;
	        File idx = new File ("index");
			if (idx.exists()) {
				idx.delete();
			}
			 File idx2 = new File ("index");
			 idx2.createNewFile();
	        Path p = Paths.get("index");
	        
	        try {
	            Files.writeString(p, contents, StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
		}
	public void editExisting(String filename) throws IOException {
		
		File index = new File ("index");
		if (!index.exists()) {
			index.createNewFile();
		}
		String contents = "";
		BufferedReader in = new BufferedReader (new FileReader ("index"));
        while (in.ready()) {
        	String line = in.readLine();
        	contents=contents+line+"\n";
        }
        in.close();
        
        contents+="*edited* "+filename;
        File idx = new File ("index");
		if (idx.exists()) {
			idx.delete();
		}
		 File idx2 = new File ("index");
		 idx2.createNewFile();
        Path p = Paths.get("index");
        
        try {
            Files.writeString(p, contents, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	}
	
	
	public void clearHashMap(){
		map = new HashMap <String,String>();
	}
	
}
