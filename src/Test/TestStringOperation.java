//package Test; 
// 
//
//import static org.junit.Assert.*;
//
//import global.StringOperation;
//
//import java.util.regex.Pattern;
//
//import org.junit.Test;
//
//
//public class TestStringOperation {
//
//	@Test
//	public void testExtractFirstNumber() {
//		String test = "r-15min";
//		assertEquals("test extractFirstNumber", 15, StringOperation.extractFirstNumber(test));
//	}
//	@Test
//	public void testgetFirstNumber() {
//		String test = "15min";
//		assertEquals("test extractFirstNumber", 15, StringOperation.getFirstNumber(test));
//	}
// 
//	@Test
//	public void testFirstMatch() {
//		String test = "project r- 5min #high #impt 5pm to 7pm 9Sep";
//		Pattern pat = Pattern.compile("([#@]|(r\\-))");
//		assertEquals("test firstMatch()", "r-", StringOperation.firstMatch(test, pat));
//		
//		pat = Pattern.compile("(#[a-z0-9&&[^ ]]{1,} )");	
//		assertEquals("test firstMatch()", "#high ", StringOperation.firstMatch(test, pat));
//	}
//	
//	@Test
//	public void testConcat() {
//		String test = "add project #impt r- 5min ";
//		assertEquals("test firstMatch()", "add project r- 5min ", StringOperation.concat(test, "#impt "));
//	}
//
//	@Test
//	public void testPrepareInput() {
//		String test = " 7pm this mon ";
//		assertEquals("test prepareInput()", "7:00pm this mon", StringOperation.prepareInputToAnalyzeTime(test));
//	}
//	
//	@Test
//	public void testGetFirst() {
//		String test = "7:00pm this mon";
//		assertEquals("test getFirst()", "7:00pm", StringOperation.getFirstWord(test));
//	}
//}
