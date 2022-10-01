package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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
		Index index = new Index();

		index.addBlob("secondTest.txt");
		index.addBlob("thirdTest.txt");
		index.addBlob("firstTest.txt");
		
		Commit second = new Commit ("second commit", "konnie", null);
		index.clearHashMap();
		index.addBlob("funContent.txt");
		Commit first = new Commit ("first commit", "konnie", second);
		index.clearHashMap();
		
		
		index.addBlob("bar.txt");
		index.addBlob("foo.txt");
		index.addBlob("foobar.txt");
		index.addBlob("something.txt");
		index.removeBlob("firstTest.txt");
		Commit third = new Commit ("third commit", "konnie", first);
		index.clearHashMap();
//		index.addBlob ("firstTest.txt");
//		Commit fourth = new Commit ("fourth commit", "konnie", third);
//		index.clearHashMap();


		first.writeFile();
		second.writeFile();
		third.writeFile();
//		fourth.writeFile();	
	}

}
