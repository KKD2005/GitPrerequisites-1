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

class CommitTestWithDeleteAndEdit {

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
		File funStuff1 = new File ("funContent.txt");
		funStuff1.createNewFile();
		 Path p1 = Paths.get("funContent.txt");
	        try {
	            Files.writeString(p1, "some fun content", StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		Index index = new Index();
		index.addBlob("firstTest.txt");
		Commit first = new Commit ("first commit", "konnie", null);
		index.clearHashMap();
//		
		
		index.addBlob("funContent.txt");
		Commit onePointFive = new Commit ("first commit", "konnie", first);
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
		index.editBlob("funContent.txt");
		
		Commit second = new Commit ("second commit", "konnie", onePointFive);
		index.clearHashMap();
		index.addBlob("bar.txt");
		index.addBlob("foo.txt");
		index.addBlob("foobar.txt");
		index.addBlob("something.txt");
		index.removeBlob("firstTest.txt");
		Commit third = new Commit ("third commit", "konnie", second);
		index.clearHashMap();
		index.addBlob ("firstTest.txt");
		index.removeBlob("secondTest.txt");
		index.removeBlob("thirdTest.txt");
		Commit fourth = new Commit ("fourth commit", "konnie", third);
		index.clearHashMap();
//
//
		first.writeFile();
		onePointFive.writeFile();
		second.writeFile();
		third.writeFile();
		fourth.writeFile();	
	}

}
