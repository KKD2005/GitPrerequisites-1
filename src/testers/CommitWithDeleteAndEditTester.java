package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import git.Commit;
import git.Index;

class CommitWithDeleteAndEditTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() throws IOException {
		File fun1 = new File ("funContent.txt");
		if (fun1.exists()) {
			fun1.delete();
		}
		
	        File first1 = new File ("firstTest.txt");
			if (first1.exists()) {
				fun1.delete();
			}
			File first2 = new File ("firstTest.txt");
			first2.createNewFile();
			 Path firstTest1 = Paths.get("firstTest.txt");
		        try {
		            Files.writeString(firstTest1, "First Testing File", StandardCharsets.ISO_8859_1);
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		Index index = new Index();
		index.addBlob("firstTest.txt");
		Commit first = new Commit ("first commit", "konnie", null);
		index.clearHashMap();
		
		File funStuff1 = new File ("funContent.txt");
		funStuff1.createNewFile();
		 Path p1 = Paths.get("funContent.txt");
	        try {
	            Files.writeString(p1, "some fun content", StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		index.addBlob("funContent.txt");
		Commit onePointFive = new Commit ("1.5 commit", "konnie", first);
		index.clearHashMap();

		index.addBlob("secondTest.txt");
		index.addBlob("thirdTest.txt");
		File fun2 = new File ("funContent.txt");
		if (fun2.exists()) {
			fun2.delete();
		}
		File funStuff2 = new File ("funContent.txt");
		funStuff2.createNewFile();
		 Path p2= Paths.get("funContent.txt");
	        try {
	            Files.writeString(p2, "example", StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		index.editExisting("funContent.txt");
		
		Commit second = new Commit ("second commit", "konnie", onePointFive);
		index.clearHashMap();
		index.addBlob("bar.txt");
		index.addBlob("foo.txt");
		index.addBlob("foobar.txt");
		index.addBlob("something.txt");
		
		Commit third = new Commit ("third commit", "konnie", second);
		index.clearHashMap();
		
		index.delete("secondTest.txt");
		index.delete("thirdTest.txt");
		Commit fourth = new Commit ("fourth commit", "konnie", third);
		index.clearHashMap();
		File first3 = new File ("firstTest.txt");
		if (first3.exists()) {
			first3.delete();
		}
		File first4 = new File ("firstTest.txt");
		first4.createNewFile();
		 Path firstTest2 = Paths.get("firstTest.txt");
	        try {
	            Files.writeString(firstTest2, "Testing File", StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		index.editExisting("firstTest.txt");
		index.delete("something.txt");
		Commit fifth = new Commit ("fifth commit", "konnie", fourth);

//
//
		first.writeFile();
		onePointFive.writeFile();
		second.writeFile();
		third.writeFile();
		fourth.writeFile();	
		fifth.writeFile();
	}

}
