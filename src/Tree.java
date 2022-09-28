import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
	
	HashMap<String, String> files = new HashMap<String, String>();
	
	public Tree(ArrayList<String> pairs) {
		
//		for (String pair : pairs) {
//			
//			int colon = pair.indexOf(':');
//			
//			String key = pair.substring(0, colon);
//			
//			String value = pair.substring(colon + 2);
//			
//			files.put(key, value);
//			
//		}
		
		String content = "";
		
		for (String pair : pairs) {
			
			
			
			content =content + pair +"\n";
			
		}
		
		content = content.substring(0, content.length()-1);
		String contentHash = Blob.encrypt(content);
		
		// write new file with sha1 as the name - KONNIE FIXED - DIDN'T WORK BEFORE
		File file = new File ("objects/"+contentHash);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Path np = Paths.get("objects/"+contentHash);
		try {
			Files.writeString(np, content, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}


