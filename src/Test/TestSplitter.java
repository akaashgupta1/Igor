//@author A0088617R
package Test;

import static org.junit.Assert.*;

import java.util.Vector;
import logic.LogicSplitter;
import logic.PatternLib;

import org.junit.Before;
import org.junit.Test;

public class TestSplitter {
	private static String input = "add dfojnvojern1 7:00 9 sep #wnwf r-1min";
	private static String input1 = "add anvfnw 5pm to 6pm tmr r-1min";
	private static String input2 = "add erjogneorgn 5pm tmr r-1min";
	private static String input3 = "add eojgneotng 17:00 12-12-12 r-2hr";
	private static String input4 = "add rjngojerng 1pm next Monday to 5pm 09-12-12 r-3min";
	private static String input5 = "add wjrfn wring";
	private static String input6 = "update 2 erjogneorgn 5pm tmr r-1min";
	private static String input7 = "delete jegnerg";
	private static String input8 = "delete 2";
	private static String input9 = "done fijngerg";
	private static String input10 = "done 2";
	private static String input11 = "undone 2";
	private static String input12 = "search eofnwrnf #jsfngirn";
	private static String input13 = "undo";
	private static String input14 = "undone ojrwngowrng";
	private static String input15 = "add rwofnerong #high #gnng 5pm to 6pm tmr r-1min";
	private static String input16 = "update 5";
	private static String input17 = "update 2 eotngetjgn";
	private static String input18 = "add engetonetongsngogrn 7am this monday";
	private static String input19 = "addenrgonw";
	private static String input20 = "add rowngowrngu jnwufnwng 5pm 7 sep r-1min";

	@Before
	public void setUp() {
		PatternLib.getInstance();
	}

	@Test
	public void testSplitInput() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input);
		assertEquals("testSplitInput",
				"[add, dfojnvojern1#wnwf, r-1min, 7:00 9/sep]", temp.toString());

	}

	@Test
	public void testSplitInput1() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input1);
		assertEquals(
				"testSplitInput",
				"[add, anvfnw, r-1min, 2012-11-11T17:00:00.000+08:00, 2012-11-12T18:00:00.000+08:00]",
				temp.toString());

	}

	@Test
	public void testSplitInput2() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input2);
		assertEquals("testSplitInput",
				"[add, erjogneorgn, r-1min, 5:00pm tmr]", temp.toString());

	}

	@Test
	public void testSplitInput3() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input3);
		assertEquals("testSplitInput",
				"[add, eojgneotng, r-2h, 17:00 12/12/12]", temp.toString());

	}

	@Test
	public void testSplitInput4() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input4);
		assertEquals(
				"testSplitInput",
				"[add, rjngojerng, r-3min, 2012-11-12T13:00:00.000+08:00, 2012-12-09T05:00:00.000+08:00]",
				temp.toString());

	}

	@Test
	public void testSplitInput5() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input5);
		assertEquals("testSplitInput", "[add, wjrfn wring]", temp.toString());

	}

	@Test
	public void testSplitInput6() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input6);
		assertEquals("testSplitInput",
				"[update, erjogneorgn, r-1min, 5:00pm tmr, -1, 2]",
				temp.toString());

	}

	@Test
	public void testSplitInput7() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input7);
		assertEquals("testSplitInput", "[delete, jegnerg]", temp.toString());

	}

	@Test
	public void testSplitInput8() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input8);
		assertEquals("testSplitInput", "[delete, 2]", temp.toString());

	}

	@Test
	public void testSplitInput9() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input9);
		assertEquals("testSplitInput", "[done, fijngerg]", temp.toString());

	}

	@Test
	public void testSplitInput10() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input10);
		assertEquals("testSplitInput", "[done, 2]", temp.toString());

	}

	@Test
	public void testSplitInput11() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input11);
		assertEquals("testSplitInput", "[undone, 2]", temp.toString());

	}

	@Test
	public void testSplitInput12() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input12);
		assertEquals("testSplitInput", "[search, eofnwrnf#jsfngirn]",
				temp.toString());

	}

	@Test
	public void testSplitInput13() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input13);
		assertEquals("testSplitInput", "[undo]", temp.toString());

	}

	@Test
	public void testSplitInput14() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input14);
		assertEquals("testSplitInput", "[undone, ojrwngowrng]", temp.toString());

	}

	@Test
	public void testSplitInput15() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input15);
		assertEquals(
				"testSplitInput",
				"[add, rwofnerong#high #gnng, r-1min, 2012-11-11T17:00:00.000+08:00, 2012-11-12T18:00:00.000+08:00]",
				temp.toString());

	}

	@Test
	public void testSplitInput16() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input16);
		assertEquals("testSplitInput", "[update, , -1, -1, -1, 5]", temp.toString());

	}

	@Test
	public void testSplitInput17() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input17);
		assertEquals("testSplitInput", "[update, eotngetjgn, -1, -1, -1, 2]",
				temp.toString());

	}

	@Test
	public void testSplitInput18() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input18);
		assertEquals("testSplitInput",
				"[add, engetonetongsngogrn, 7:00am this mon]", temp.toString());

	}

	@Test
	public void testSplitInput19() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input19);

		assertEquals("testSplitInput", "[addenrgonw]", temp.toString());

	}

	@Test
	public void testSplitInput20() throws Exception {
		LogicSplitter.setUp();
		Vector<String> temp = LogicSplitter.splitInput(input20);
		assertEquals("testSplitInput",
				"[add, rowngowrngu jnwufnwng, r-1min, 5:00pm 7/sep]",
				temp.toString());

	}
}
