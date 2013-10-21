//@author A0091565Y
package alarm;
  
import org.joda.time.DateTime;

public class AlarmType {

	private static final int CHANGE_TO_SECONDS = 1000;
	String eventName;
	DateTime reminderTime;
	
	public AlarmType(String event, DateTime time) {
		eventName = event;
		reminderTime = time; 
	}
	
	public String getEventName() {
		return eventName;
	}
	
	public boolean isAlarmTime() {
		return (reminderTime.getMillis()/CHANGE_TO_SECONDS == System.currentTimeMillis()/CHANGE_TO_SECONDS);
	}
}
