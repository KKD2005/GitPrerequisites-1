import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e : map.entrySet()) {
			sb.append(e.getKey() + " : " + e.getValue() + "\n");
		}
		
		// overwrite contents of index file
		FileWriter f = new FileWriter(".\\index", false);
		f.write(sb.toString());
		f.close();
	}
	
	public void addBlob(String filename) {
		Blob b = new Blob(filename);
		this.map.put(filename, b.SHA1_HASH);
		try {
			updateIndex();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeBlob(String filename) {
		// delete file in objects folder
		try {
			Files.deleteIfExists(Paths.get(".\\objects\\" + this.map.get(filename)));
		} catch (NoSuchFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// remove from hashmap
		this.map.remove(filename);
		
		// remove from index file
		try {
			updateIndex();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
