//@author A00077245B

package event;

import java.util.Comparator;
 
public class CompareEventByTime implements Comparator<Event>{
	
	@Override
	public int compare(Event a, Event b) {
		if (getDateOrder(a,b) != 0) {
			return getDateOrder(a,b);
		}
		Comparator<Event> cmp = new CompareEventByPriority();
		return cmp.compare(a, b);
	}
	
	private static int getDateOrder(Event a, Event b) {
		if((a.getEventStartTime().isBeforeNow() && a.getEventEndTime().isBeforeNow() && 
				!(a.getEventType() == Event.FLOATING_TYPE)) || 
				(a.getEventType() == Event.FLOATING_TYPE && a.isDone())) {
			return 1;
		}
		
		if((b.getEventStartTime().isBeforeNow() && b.getEventEndTime().isBeforeNow() && 
				!(b.getEventType() == Event.FLOATING_TYPE)) || 
				(b.getEventType() == Event.FLOATING_TYPE && b.isDone())) {
			return -1;
		}
		if (a.getEventStartTime().isBefore(b.getEventStartTime()))
			return -1;
		else if (a.getEventStartTime().isAfter(b.getEventStartTime()))
			return 1;
		else
			return 0;
	}
}
