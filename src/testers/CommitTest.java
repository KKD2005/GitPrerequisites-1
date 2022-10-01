package testers;
import static org.junit.jupiter.api.Assertions.*;
import git.Index;
import git.Commit;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommitTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() throws IOException {
		Index index = new Index();
		index.addBlob("funContent.txt");
		Commit first = new Commit ("first commit", "konnie", null);
		index.clearHashMap();
		
		index.addBlob("secondTest.txt");
		index.addBlob("thirdTest.txt");
		
		Commit second = new Commit ("second commit", "konnie", first);
		index.clearHashMap();
		index.addBlob("bar.txt");
		index.addBlob("foo.txt");
		index.addBlob("foobar.txt");
		index.addBlob("something.txt");
		Commit third = new Commit ("third commit", "konnie", second);
		index.clearHashMap();
		index.addBlob ("firstTest.txt");
		Commit fourth = new Commit ("fourth commit", "konnie", third);
		index.clearHashMap();


		first.writeFile();
		second.writeFile();
		third.writeFile();
		fourth.writeFile();
	}

}
