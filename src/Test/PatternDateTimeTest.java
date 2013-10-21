//@author A00077245B
package Test;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;
import logic.PatternDateTime;
import logic.PatternLib;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
 
 

public class PatternDateTimeTest {

	@Before
	public void setUp() {
		PatternLib.getInstance();	
	}
	
	@Test
	public void testRemoveFirst() {
		PatternDateTime pat = new PatternDateTime(Pattern.compile("0000"), "0000");
		assertEquals("test removeFirst()", "this mon", pat.removeFirst("aaaa this mon"));
	}
	
	@Test 
	public void testGetFirst() {
		PatternDateTime pat = new PatternDateTime(Pattern.compile("0000"), "0000");
		assertEquals("test removeFirst()", "7:00am", pat.getFirst("7:00am this mon"));
	}
	
	@Test
	public void testGetTimek() {
		PatternDateTime pat = new PatternDateTime(Pattern.compile("0000"), "0000");
		DateTime dt = pat.getDateTimeSpecial("7:00am");
		assertEquals("test getTime()", 7, dt.getHourOfDay());
	}
	
	@Test
	public void testGetDate() {
		PatternDateTime pat = new PatternDateTime(Pattern.compile("0000"), "0000");
		DateTime dt = pat.getDate("tmr");
		assertEquals("test getTime()", 14, dt.getDayOfMonth());
	}

	@Test 
	public void testGetDateTimeSpecial() {
		DateTime dt = PatternLib.getInstance().getDateTime("7:00am this mon",PatternLib.getInstance().isMatchDateTime("7:00am this mon"));
		assertEquals("test getDateTimeSpecial()", 7, dt.getHourOfDay());
	}
}
