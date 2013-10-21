//@author A0091565Y
package alarm;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import event.ListOfEvent;
import event.ListOfEventObserver;
 


public class ListOfAlarm implements ListOfEventObserver {

	private static CopyOnWriteArrayList<AlarmType> alarmList = new CopyOnWriteArrayList<AlarmType>();
	private static ListOfAlarm _instance = new ListOfAlarm();
	
	private ListOfAlarm() {
		
	}
	
	public static ListOfAlarm getInstance() {
		return _instance;
	}
	
	public static void setListOfAlarm(ArrayList<AlarmType> newList) {
		//Collections.copy(alarmList, newList);
		alarmList.clear();
		for(int i=0;i<newList.size();i++)
		{
			alarmList.add(newList.get(i));
		}
	}
	
	
	public void updateListOfEvent() {
		setListOfAlarm(ListOfEvent.setUpListOfReminder());
	}
	
	public static void runAlarm()  throws Exception{
		if(!alarmList.isEmpty()) {
			Iterator<AlarmType> it = alarmList.iterator(); 
			while(it.hasNext()) {
				AlarmType alarmElement = it.next();
				if(alarmElement.isAlarmTime()){
					DialogRun d = new DialogRun("EVENT NAME: " + alarmElement.getEventName());
					new Thread(d).start();
					Thread.sleep(1000);
					break;
				}
					
			}
		}
	}
	
}
