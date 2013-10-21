//@author A00077245B

package Test; 

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import event.Event;
import global.Clock;


public class TestEvent {
	String[] id = {"1", "2", "3", "4", "5"};
	String[] name = {"assignment", "project", "homework", "tutorial", "facebook time"};
	String[] priority = {"high", "low", "normal", "low", "high"};
	String[] hashTag = {"#ma3264", "#cs2103", "#ma3233 #ma3236", "#all", "#play"};
	
	ArrayList<DateTime> start = new ArrayList<DateTime>();
	ArrayList<DateTime> end = new ArrayList<DateTime>();
	ArrayList<DateTime> reminder = new ArrayList<DateTime>();
	ArrayList<DateTime> completed = new ArrayList<DateTime>();
	
	Event floating, deadline, timed, clashTimed1, clashTimed2, clashTimed3, clashTimed4;
	
	
	@Before
	public void setUp() {
		start.add(new DateTime(2000, 9, 7, 10, 10));
		end.add(start.get(0).plusHours(1));
		reminder.add(start.get(0).minusMinutes(5));
		completed.add(start.get(0));
		
		start.add(new DateTime(2012, 11, 7, 22, 14));
		end.add(start.get(1).plusHours(2));
		reminder.add(start.get(0).minusHours(10));
		completed.add(new DateTime(1970, 1, 1, 0, 0));
		
		start.add(new DateTime(2012, 11, 7, 22, 10));
		end.add(start.get(2).plusHours(3));
		reminder.add(start.get(0).minusMinutes(15));
		completed.add(new DateTime(1970, 1, 1, 0, 0));
		
		start.add(new DateTime(2012, 11, 7, 21, 15));
		end.add(start.get(3).plusHours(2));
		reminder.add(start.get(0).minusMinutes(20));
		completed.add(start.get(3));
		
		start.add(new DateTime(2012, 11, 7, 22, 15));
		end.add(start.get(4).plusHours(1));
		reminder.add(start.get(0).minusMinutes(25));
		completed.add(new DateTime(1970, 1, 1, 0, 0));
		
		start.add(new DateTime(2012, 11, 7, 22, 10));
		end.add(start.get(1).plusHours(2));
		reminder.add(start.get(0).minusMinutes(10));
		completed.add(new DateTime(1970, 1, 1, 0, 0));
		
		floating = new Event("01", "floating", Event.PRIORITY_TYPE.HIGH, "#floating", Clock.getBigBangTime(), Clock.getBigBangTime(), Clock.getBigBangTime());
		deadline = new Event("02", "deadline", Event.PRIORITY_TYPE.NORMAL, "#deadline", reminder.get(0), start.get(0), Clock.getBigBangTime());
		timed = new Event("03", "timed", Event.PRIORITY_TYPE.LOW, "#timed", reminder.get(1), start.get(1), end.get(1));
		clashTimed1 = new Event ("04", "clashed timed1", Event.PRIORITY_TYPE.LOW, "#timed", reminder.get(1), start.get(2), end.get(2));
		clashTimed2 = new Event ("05", "clashed timed2", Event.PRIORITY_TYPE.LOW, "#timed", reminder.get(1), start.get(3), end.get(3));
		clashTimed3 = new Event ("06", "clashed timed3", Event.PRIORITY_TYPE.LOW, "#timed", reminder.get(1), start.get(4), end.get(4));

	}
	
	@Test
	public void testParseFromString() {
		String event = "01..project..HIGH..#work #impt..false..00:05 01/01/1970..05:00 15/10/2012..invalid..invalid";
		
		Event newEvent = new Event();
		newEvent.parse(event.split("\\.."));
		assertEquals("test parse()", event, newEvent.toString());
		
		
		event = "02..project..NORMAL..#work #impt..false..00:05 01/01/1970..05:00 15/10/2013..19:00 15/10/2013..invalid";
		newEvent.parse(event.split("\\.."));
		assertEquals("test parse()", event, newEvent.toString());
		
		event = "03..project..LOW..#work #impt..false..invalid..invalid..invalid..invalid";
		newEvent.parse(event.split("\\.."));
		assertEquals("test parse()", event, newEvent.toString());
		
		event = "";
		newEvent.parse(event.split("\\.."));
	}
	
	@Test
	public void testToString() {
		assertEquals("test toString()", "01..floating..HIGH..#floating..false..invalid..invalid..invalid..invalid", floating.toString());
		assertEquals("test toString()", "02..deadline..NORMAL..#deadline..false..10:05 07/09/2000..10:10 07/09/2000..invalid..invalid", deadline.toString());
		assertEquals("test toString()", "03..timed..LOW..#timed..false..00:10 07/09/2000..22:14 07/11/2012..00:14 08/11/2012..invalid", timed.toString());
	
	}
	
	@Test
	public void testComposeContentToDisplay() {
		assertEquals("test toString()", "floating..HIGH..#floating..false........", floating.composeContentToDisplayInString());
		assertEquals("test toString()", "deadline..NORMAL..#deadline..false..10:10 07/09/2000....r-5 minutes..", deadline.composeContentToDisplayInString());
		assertEquals("test toString()", "timed..LOW..#timed..false..22:14 07/11/2012..00:14 08/11/2012..r-1777 days..", timed.composeContentToDisplayInString());	
	}
	
	@Test
	public void testCompare() {
		
	}
	
	@Test
	public void testIsClashed() {		
		assertEquals("test isClash()1", false, floating.isClashedWith(deadline));
		assertEquals("test isClash()2", false, floating.isClashedWith(timed));
		assertEquals("test isClash()3", false, deadline.isClashedWith(timed));
		assertEquals("test isClash()4", true, timed.isClashedWith(clashTimed1));
		assertEquals("test isClash()5", true, timed.isClashedWith(clashTimed2));
		assertEquals("test isClash()6", true, timed.isClashedWith(clashTimed3));
	}
	
	@Test
	public void testIsBefore() {
		assertEquals("test isBefore()1", true, floating.isBefore(deadline));
		assertEquals("test isBefore()2", true, deadline.isBefore(timed));
		assertEquals("test isBefore()3", false, timed.isBefore(deadline));
		assertEquals("test isBefore()4", false, timed.isBefore(clashTimed1));
		assertEquals("test isBefore()5", true, clashTimed2.isBefore(clashTimed3));
		assertEquals("test isBefore()6", false, clashTimed3.isBefore(floating));
	}
}



