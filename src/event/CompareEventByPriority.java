//@author A00077245B

package event;
import java.util.Comparator;
import event.Event;
  
public class CompareEventByPriority implements Comparator<Event>{
	
	public int compare(Event firstEvent, Event secondEvent) {
		int firstPriority = getPriorityInInt(firstEvent);
		int secondPriority = getPriorityInInt(secondEvent);
		return firstPriority - secondPriority;
	}
	
	private static int getPriorityInInt(Event event) {
		Event.PRIORITY_TYPE priority = event.getPriority();
		if (priority == Event.PRIORITY_TYPE.HIGH)
			return 0;
		else if (priority == Event.PRIORITY_TYPE.NORMAL)
			return 1;
		else if (priority == Event.PRIORITY_TYPE.LOW)
			return 2;
		return 1;
	}
}