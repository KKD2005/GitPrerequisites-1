import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IrisBlobTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Path filePathToWrite = Paths.get("test.txt");
		try {
			// hash: ff9f6ae1c8d4ca16e67f15f4ae1d10e9bc69beaa
			Files.writeString(filePathToWrite, "First testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite2 = Paths.get("secondTest.txt");
		try {
			// hash: 4a4e0e220c01d6170a3e057cc39c322c3bdd0755
			Files.writeString(filePathToWrite2, "Second testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite3 = Paths.get("thirdTest.txt");
		try {
			// hash: 4a4e0e220c01d6170a3e057cc39c322c3bdd0755
			Files.writeString(filePathToWrite3, "Third testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
		// delete the index file. we will rewrite soon
	    File myObj = new File("test.txt"); 
	    if (myObj.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
		
	}
	
	Index index = new Index();

	@Test
	void testInit() {
		
		index.initProject();
		
		File file = new File ("index");
		
		assertTrue(file.exists());
		
		Path path = Paths.get("objects");
		assertTrue(Files.exists(path));
	}
	
	@Test
	void testBlob() throws IOException {
		Blob b = new Blob ("test.txt");
		
		File file = new File("./objects/ff9f6ae1c8d4ca16e67f15f4ae1d10e9bc69beaa");
		assertTrue(file.exists());
		
		String content = "";
		
		BufferedReader reader = new BufferedReader(new FileReader("./objects/ff9f6ae1c8d4ca16e67f15f4ae1d10e9bc69beaa"));

		while (reader.ready()) {
			content += (char)reader.read();
		}
		
		assertTrue(content.equals("First testing file"));
	}
	
	@Test
	void testIndexAdd() throws IOException {
		index.addBlob("secondTest.txt");
		
		File file = new File ("./objects/4a4e0e220c01d6170a3e057cc39c322c3bdd0755");
		assertTrue(file.exists());
		
		String content = "";
		
		BufferedReader reader = new BufferedReader(new FileReader("index"));

		while (reader.ready()) {
			content += (char)reader.read();
		}
		
		assertTrue(content.equals("secondTest.txt : 4a4e0e220c01d6170a3e057cc39c322c3bdd0755\n"));
		
		index.addBlob("thirdTest.txt");
		
		String newContent = "";
		
		BufferedReader secondReader = new BufferedReader(new FileReader("index"));

		while (secondReader.ready()) {
			newContent += (char)secondReader.read();
		}
		
		assertTrue(newContent.contains("secondTest.txt : 4a4e0e220c01d6170a3e057cc39c322c3bdd0755\n") && newContent.contains("thirdTest.txt : a4e554c577ef9ab5e4b71e9197ec70c95f715b02\n"));
	}
	
	

}
