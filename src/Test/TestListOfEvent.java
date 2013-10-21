//@author A00077245B

package Test; 

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import event.Event;
import event.CompareEventByTime;
import event.Event.PRIORITY_TYPE;
import event.ListOfEvent;
import global.Clock;


public class TestListOfEvent {
	String[] id = {"1", "2", "3", "4", "5"};
	String[] name = {"assignment", "project", "homework", "tutorial", "facebook time"};
	String[] priority = {"high", "low", "normal", "low", "high"};
	String[] hashTag = {"#ma3264", "#cs2103", "#ma3233 #ma3236", "#all", "#play"};
	
	ArrayList<DateTime> start = new ArrayList<DateTime>();
	ArrayList<DateTime> end = new ArrayList<DateTime>();
	ArrayList<DateTime> reminder = new ArrayList<DateTime>();
	ArrayList<DateTime> completed = new ArrayList<DateTime>();
	
	Event floating, deadline, timed, clashTimed1, clashTimed2, clashTimed3;
	
	String floatingInString, deadlineInString, timedInString, 
		invalidReminder, swapTime, invalidPriority, invalidIsDone, invalidEventID, invalidTime, floatingDone;
	
	@Before
	public void setUp() {
		start.add(new DateTime(2000, 9, 7, 10, 10));
		end.add(start.get(0).plusHours(1));
		reminder.add(start.get(0).minusMinutes(5));
		completed.add(start.get(0));
		
		start.add(new DateTime(2013, 7, 7, 22, 14));
		end.add(start.get(1).plusHours(2));
		reminder.add(start.get(0).minusMinutes(10));
		completed.add(new DateTime(1970, 1, 1, 0, 0));
		
		start.add(new DateTime(2013, 11, 7, 22, 10));
		end.add(start.get(2).plusHours(3));
		reminder.add(start.get(0).minusMinutes(15));
		completed.add(new DateTime(1970, 1, 1, 0, 0));
		
		start.add(new DateTime(2013, 11, 7, 21, 15));
		end.add(start.get(3).plusHours(2));
		reminder.add(start.get(0).minusMinutes(20));
		completed.add(start.get(3));
		
		start.add(new DateTime(2013, 11, 7, 22, 15));
		end.add(start.get(4).plusHours(1));
		reminder.add(start.get(0).minusMinutes(25));
		completed.add(new DateTime(1970, 1, 1, 0, 0));
		
		start.add(new DateTime(2013, 11, 7, 22, 10));
		end.add(start.get(1).plusHours(2));
		reminder.add(start.get(0).minusMinutes(10));
		completed.add(new DateTime(1970, 1, 1, 0, 0));
		
		floating = new Event("01", "floating", PRIORITY_TYPE.HIGH, "#floating", Clock.getBigBangTime(), Clock.getBigBangTime(), Clock.getBigBangTime());
		deadline = new Event("02", "deadline", PRIORITY_TYPE.NORMAL, "#deadline", reminder.get(0), start.get(0), Clock.getBigBangTime());
		timed = new Event("03", "timed", PRIORITY_TYPE.LOW, "#timed", reminder.get(1), start.get(1), end.get(1));
		clashTimed1 = new Event ("04", "clashed timed1", PRIORITY_TYPE.LOW, "#timed", reminder.get(1), start.get(2), end.get(2));
		clashTimed2 = new Event ("05", "clashed timed2", PRIORITY_TYPE.LOW, "#timed", reminder.get(1), start.get(3), end.get(3));
		clashTimed3 = new Event ("06", "clashed timed3", PRIORITY_TYPE.LOW, "#timed", reminder.get(1), start.get(4), end.get(4));
		
		floatingInString = "01..floating..LOW..#work #impt..false..invalid..invalid..invalid..invalid";
		floatingDone = "07..floating..LOW..#work #impt..true..invalid..invalid..invalid..invalid";
		deadlineInString = "02..deadline..HIGH..#work #impt..false..00:05 01/01/1970..05:00 15/10/2012..invalid..invalid";
		timedInString = "03..timed..NORMAL..#work #impt..false..00:05 01/01/1970..05:00 15/10/2012..19:00 15/10/2012..invalid";
		invalidReminder = "04..project..LOW..#work #impt..false..00:05 01/01/1970..invalid..invalid..invalid";
		swapTime = "07..timed..NORMAL..#work #impt..false..00:05 01/01/1970..19:00 15/10/2012..05:00 15/10/2012..invalid";
		invalidPriority = "05..project..high LOW..#work #impt..false..invalid..invalid..invalid..invalid";
		invalidIsDone = "05..project..high LOW..#work #impt..falses ..invalid..invalid..invalid..invalid";
		invalidEventID = "01..lalala..high LOW..#work #impt..false..invalid..invalid..invalid..invalid";
		invalidTime = "05..project..high LOW..#work #impt..false..invalid..invalid..kaka..invalid";
	}
	
	@After
	public void clearUp() {
		ListOfEvent.clearListOfEvent();
	}
	
	@Test
	public void testAdd() {
		
		ListOfEvent.add(floatingInString);
		ListOfEvent.add(deadlineInString);
		ListOfEvent.add(timedInString);

		String invalidString = "..";
		ListOfEvent.add(invalidReminder);
		ListOfEvent.add(swapTime);
		ListOfEvent.add(invalidPriority);
		ListOfEvent.add(invalidIsDone);
		ListOfEvent.add(invalidEventID);
		ListOfEvent.add(invalidTime);
		ListOfEvent.add(invalidString);
		
		assertEquals("test add()1", 5, ListOfEvent.size());
		assertEquals("test add()2", floatingInString, ListOfEvent.get(0).toString());
		assertEquals("test add()3", deadlineInString, ListOfEvent.get(1).toString());
		assertEquals("test add()4", timedInString, ListOfEvent.get(2).toString());
		assertEquals("test add()5", "04..project..LOW..#work #impt.." +
				"false..invalid..invalid..invalid..invalid", ListOfEvent.get(3).toString());
		assertEquals("test add()6", "07..timed..NORMAL..#work #impt..false.." +
				"00:05 01/01/1970..05:00 15/10/2012..19:00 15/10/2012..invalid", ListOfEvent.get(4).toString());
	}
	
	@Test
	public void testSearch() {
		
	}
	
	@Test
	public void testCompare() {
		Comparator<Event> cmp = new CompareEventByTime();
		assertEquals("test compare()", -1, cmp.compare(timed, deadline));
		assertEquals("test compare()", -1, cmp.compare(floating, clashTimed1));
		floating.markDone();
		assertEquals("test compare()", 1, cmp.compare(floating, timed));
	}
	
	@Test
	public void testGetDisplay() {
		ListOfEvent.add(floating);
		ListOfEvent.add(deadline);
		ListOfEvent.add(timed);
		ListOfEvent.add(clashTimed1);
		ListOfEvent.add(clashTimed2);
		ListOfEvent.add(clashTimed3);
		Event eventFloatingDone = new Event();
		eventFloatingDone.parse(floatingDone.split("\\.."));
		ListOfEvent.add(eventFloatingDone);
		assertEquals("test getDisplay()1", 7, ListOfEvent.size());
		ArrayList<String> floating = ListOfEvent.getListOfFloatingEventToDisplayInString();
		ArrayList<String> upcoming = ListOfEvent.getListOfEventToDisplayInString();
		assertEquals("test getDisplay", 1, floating.size());
		assertEquals("test getDisplay", "1..floating..HIGH..#floating..false........", floating.get(0).toString());
		assertEquals(4, upcoming.size());
		assertEquals("test getDisplay", "2..timed..LOW..#timed.." +
				"false..22:14 07/07/2013..00:14 08/07/2013..r-1874 days..", upcoming.get(0));
		assertEquals("test getDisplay()", "3..clashed timed2..LOW..#timed..false..21:15 07/11/2013..23:15 07/11/2013..r-1923 days..", upcoming.get(1));
		assertEquals("test getDisplay()", "4..clashed timed1..LOW..#timed..false..22:10 07/11/2013..01:10 08/11/2013..r-1923 days..", upcoming.get(2));
		assertEquals("test getDisplay()", "5..clashed timed3..LOW..#timed..false..22:15 07/11/2013..23:15 07/11/2013..r-1923 days..", upcoming.get(3));
	}
	
	@Test
	public void testRemove() {
		ListOfEvent.add(floating);
		ListOfEvent.add(deadline);
		ListOfEvent.add(timed);
		ListOfEvent.add(clashTimed1);
		ListOfEvent.add(clashTimed2);
		ListOfEvent.add(clashTimed3);
		ListOfEvent.removeList(0);
		ListOfEvent.removeList(0);
		assertEquals("test remove", 4, ListOfEvent.size());
	}
	
	@Test
	public void testUpdate() {
		ListOfEvent.add(timed);
		ListOfEvent.add(clashTimed1);
		ListOfEvent.add(clashTimed2);
		ListOfEvent.add(clashTimed3);
		ListOfEvent.add(deadline);
		ListOfEvent.add(floating);
		ListOfEvent.updateList(0, floatingDone);
		assertEquals("test update1", "04..clashed timed1..LOW..#timed..false..10:00 07/09/2000..22:10 07/11/2013..01:10 08/11/2013..invalid", ListOfEvent.get(0).toString());
		ListOfEvent.updateList(10, "floatingDone");
	}
}