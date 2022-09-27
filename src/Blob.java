import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob {
	public final String SHA1_HASH;
	
	/** 
		* Take a file on disk and... 
		Creates a SHA1 String given the whole file data (hint: you can lookup and copy code to generate a SHA1 String)
		Writes a new file to disk inside an 'objects' folder
		The new filename is only the SHA1 Hash
		The file contains the same contents of the original file
		** Stretch Goal:  Saves and reads the data as zip-compressed data instead of a raw String
		Create some functionality in code to get / return the generated SHA1 for later use
	 */
	
	public Blob(String filename) {
		// read contents of original file into a string named "content"
		Path p = Paths.get(filename);
		String content = "";
		try {
			byte[] fileBytes = Files.readAllBytes(p);
			content = new String(fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// make sha1 hash
		this.SHA1_HASH = encrypt(content);
		
		// write new file with sha1 as the name - KONNIE FIXED - WASN'T CREATING FILE BEFORE
		File np = new File ("objects/" + SHA1_HASH);
		try {
			np.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Path p2 = Paths.get ("objects/" + SHA1_HASH);
		try {
			Files.writeString(p2, content, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// stole from https://www.geeksforgeeks.org/sha-1-hash-in-java/ blame them if its wrong not me 
	public static String encrypt(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger num = new BigInteger(1, messageDigest);
			String hex = num.toString(16);
			while (hex.length() < 32) {
				hex = "0" + hex;
			}
			return hex;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
