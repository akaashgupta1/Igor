//@author A00077245B

package Test; 

import static org.junit.Assert.assertEquals;
import global.Clock;
import org.joda.time.DateTime;
import org.junit.Test;

public class TestClock {
	@Test
	public void testToString() {		
		DateTime date = new DateTime(2007, 7, 7, 7, 7);
		assertEquals("test toString()", "07:07 07/07/2007", Clock.toString(date));
	}
	
	@Test
	public void testChangeToDate() {
		DateTime date = new DateTime(2007, 7, 7, 7, 7);
		DateTime anotherDate = new DateTime(2007, 7, 10, 10, 10);
		assertEquals("test changeToDate()", new DateTime(2007, 7, 10, 7, 7), Clock.changeToDate(date, anotherDate));
	}
	 
	@Test
	public void testParseFromString() {
		String dateInString = "00:00 07/07/2007";
		assertEquals("test ToDate()", new DateTime(2007, 7, 7, 0, 0), Clock.parseTimeFromString(dateInString));
	}
}
