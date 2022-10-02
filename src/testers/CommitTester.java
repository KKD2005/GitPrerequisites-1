package testers;
import static org.junit.jupiter.api.Assertions.*;
import git.Index;
import git.Commit;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommitTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		File headDelete = new File("head");
		if (headDelete.exists()) {
			headDelete.delete();
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() throws IOException {
		Index index = new Index();
		index.addBlob("funContent.txt");
		Commit first = new Commit ("first commit", "konnie");
		index.clearHashMap();
		
		index.addBlob("secondTest.txt");
		index.addBlob("thirdTest.txt");
		
		Commit second = new Commit ("second commit", "konnie");
		index.clearHashMap();
		index.addBlob("bar.txt");
		index.addBlob("foo.txt");
		index.addBlob("foobar.txt");
		index.addBlob("something.txt");
		Commit third = new Commit ("third commit", "konnie");
		index.clearHashMap();
		index.addBlob ("firstTest.txt");
		Commit fourth = new Commit ("fourth commit", "konnie");
		index.clearHashMap();


		
	}

}
