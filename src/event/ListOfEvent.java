//@author A00077245B

package event;
 
import executor.Executor;
import fileIO.FileIO;
import global.Clock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import org.joda.time.DateTime;
import alarm.AlarmType;
import alarm.ListOfAlarm;

public class ListOfEvent {
	private static ArrayList<Event> listOfEvent = new ArrayList<Event>();
	private static ArrayList<Event> searchResults = new ArrayList<Event>();
	private static ArrayList<ListOfEventObserver> listOfObserver= new ArrayList<ListOfEventObserver>();
	private static final String fileName = "What2Do.txt";
	private static final String displayFormat = "%1$d..%2$s";
	private static ArrayList<String> feedback = new ArrayList<String>();
	private static final String STRING_NULL = "";
	
	public static void addObserver(ListOfEventObserver observer) {
		listOfObserver.add(observer);
	}
	
	public static void notifyObservers() {
		int numberOfObserver = listOfObserver.size();
		for(int index = 0; index < numberOfObserver; index++) {
			listOfObserver.get(index).updateListOfEvent();
		}	
	}
	
	public static void setUpDataFromDatabase() throws Exception {
		addObserver(Executor.getInstance());
		addObserver(ListOfAlarm.getInstance());
		FileIO database = new FileIO(fileName);
		database.setUpDatabase();
		notifyObservers();
		feedback.clear();
	}
	
	public static void syncDataToDatabase() throws IOException {
		FileIO database = new FileIO(fileName);
		ArrayList<String> listOfEventInString = new ArrayList<String>();
		listOfEventInString = getContentToSyncToDatabase();
		database.syncToDatabase(listOfEventInString);
	}
	
	public void clearFeedback() {
		feedback.clear();
		return;
	}
	
	public static ArrayList<String> getFeedback() {
		ArrayList<String> feedbackToReturn = new ArrayList<String>();
		feedbackToReturn.addAll(feedback);
		feedback.clear();
		return feedbackToReturn;
	}
	
	public static int size() {
		return listOfEvent.size();
	}
	
	public static Event markDoneSearch(int position) {
		if(isOutOfBoundIndex(position, searchResults)) {
			return null;
		}
		searchResults.get(position).markDone();
		int index = indexOf(searchResults.get(position));
		markDoneList(index);
		return listOfEvent.get(index);
	}
	
	public static Event markUndoneSearch(int position) {
		if(isOutOfBoundIndex(position, searchResults)) {
			return null;
		}
		searchResults.get(position).markUndone();
		int index = indexOf(searchResults.get(position));
		markUndoneList(index);
		return listOfEvent.get(index);
	}
	
	public static Event markUndoneList(int position) {
		if(isOutOfBoundIndex(position,listOfEvent )) {
			return null;
		}
		Event eventToMarkUndone = listOfEvent.get(position);
		eventToMarkUndone.markUndone();
		return eventToMarkUndone;
	}
	
	public static Event markDoneList(int position) {
		if(isOutOfBoundIndex(position, listOfEvent)) {
			return null;
		}
		Event eventToMarkDone = listOfEvent.get(position);
		eventToMarkDone.markDone();
		return eventToMarkDone;
	}
	
	private static boolean isOutOfBoundIndex(int index, ArrayList<Event> list) {
		if(index > list.size()-1) {
			feedback.add("Error: Out Of Bound index");
			return true;
		}
		return false;
	}
	
	public static String markDoneList(Event event) {
		int index = indexOf(event);
		if(index >= 0) {
			listOfEvent.get(index).markDone();
			return "'" + event.getEventName() + "'";
		}
		return "";
	}
	
	public static String markUndoneList(Event event) {
		int index = indexOf(event);
		if(index >= 0) {
			listOfEvent.get(index).markUndone();
			return "'" + event.getEventName() + "'";
		}
		return "";
	}
	
	public static String add(Event newEvent) {
		listOfEvent.add(newEvent);
		return "'" + newEvent.getEventName() + "'";
	}
	
	public static Event add(String eventInString) {
		Event newEvent = getEventFromString(eventInString);
		if(!newEvent.isSameEvent(new Event())) {
			checkForWarning(newEvent);
			listOfEvent.add(newEvent);
			return newEvent;
		}
		return null;
	}
	
	private static void checkForWarning(Event newEvent) {
		isClashedWithExistingEvents(newEvent);
		if(newEvent.isBeforeCurrentTime()) {
			feedback.add("WARNING: The New Event is before Present time.");
		}
	}
	
	private static void isClashedWithExistingEvents(Event newEvent) {
		for(int index = 0; index < listOfEvent.size(); index++) {
			Event currentEvent = listOfEvent.get(index);
			if(currentEvent.isClashedWith(newEvent)) {
				feedback.add("WARNING: The New Event is Clashed with existing Events.");
				return;
			}
		}
	}
	
	public static Event get(int position) {
		return listOfEvent.get(position);
	}
	
	public static Event removeList(int position) {
		return remove(position, listOfEvent);
	}	
	
	public static Event removeSearch(int position) {
		Event eventToRemove = remove(position, searchResults);
		remove(eventToRemove);
		return eventToRemove;
	}
	
	private static Event remove(int position, ArrayList<Event> list) {
		assert list.size() > position;
		return list.remove(position);
	}
	
	public static String remove(Event eventToRemove) {
		int index = indexOf(eventToRemove);
		remove(index, listOfEvent);
		return "'" + eventToRemove.getEventName() + "'";
	}
	
	public static ArrayList<Event> updateSearch(int position, String eventToUpdate) {
		ArrayList<Event> update = new ArrayList<Event>();
		if(isOutOfBoundIndex(position, searchResults)) {
			return update;
		}
		Event removedEvent = searchResults.remove(position);
		Event updatedEvent = getEventFromString(eventToUpdate);
		if(!updatedEvent.isSameEvent(new Event())) {
			searchResults.add(updatedEvent);
			checkForWarning(updatedEvent);
			update(removedEvent, updatedEvent);
			update.add(removedEvent);
			update.add(updatedEvent);
			return update;
		}
		return update;
	}
	
	public static ArrayList<Event> updateList(int position, String eventToUpdate) {
		ArrayList<Event> update = new ArrayList<Event>();
		if(isOutOfBoundIndex(position, listOfEvent)) {
			return update;
		}
		Event removedEvent = listOfEvent.remove(position);
		Event updatedEvent = getEventFromString(eventToUpdate);
		if(!updatedEvent.isSameEvent(new Event())) {
			checkForWarning(updatedEvent);
			listOfEvent.add(updatedEvent);
			update.add(removedEvent);
			update.add(updatedEvent);
			return update;
		}
		return update;
	}
	
	public static String update(Event before, Event after) {
		int indexOfBefore = indexOf(before);
		if (indexOfBefore != -1) {
			listOfEvent.remove(indexOfBefore);
			listOfEvent.add(indexOfBefore, after);
			return "'" + after.getEventName() + "' to '"  + before.getEventName() + "'";
		}
		return "";
	}
	
	public static void searchInNameAndHashTags(Vector<String> searchWords) {
		searchResults.clear();
		int size = ListOfEvent.size();
		for (int i = 0; i < size; i++) {
			if (searchWords.isEmpty())
				break;
			boolean isChecked = false;
			String searchCheck = STRING_NULL;
			String[] tags = ListOfEvent.get(i).getEventHashTag().split("#");
			for (int j = 0; j < tags.length; j++) {
				searchCheck += tags[j];
				searchCheck += ".";
			}
			searchCheck += ListOfEvent.get(i).getEventName();
			for (int k = 0; k < searchWords.size(); k++) {
				if (searchCheck.toLowerCase().contains(
						searchWords.get(k).toLowerCase())
						&& isChecked == false && !searchWords.get(k).trim().equalsIgnoreCase("")) {
					searchResults.add(ListOfEvent.get(i));
					break;
				}
			}
		}
	}
	
//	public static void searchInNameAndHashTag(ArrayList<String> searchWords) {
//		searchResults = new ArrayList<Event>();
//		int numberOfSearchWords = searchWords.size();
//		for(int indexOfSearchWord = 0; indexOfSearchWord < numberOfSearchWords; indexOfSearchWord++) {
//			String currentSearchWord = searchWords.get(indexOfSearchWord);
//			searchForAWordInNameAndHashTag(currentSearchWord);
//		}
//		return;
//	}
//	
//	private static void searchForAWordInNameAndHashTag(String searchWord) {
//		int currentNumberOfEvents = listOfEvent.size();
//		for(int indexEvent = 0; indexEvent < currentNumberOfEvents; indexEvent++) {
//			Event currentEvent = listOfEvent.get(indexEvent);
//			if(currentEvent.seachInName(searchWord) 
//					|| currentEvent.searchInHashTag(searchWord)) {
//				searchResults.add(currentEvent);
//			}
//		}
//		return;
//	}

	public static ArrayList<AlarmType> setUpListOfReminder() {
		ArrayList<AlarmType> reminderList = new ArrayList<AlarmType>();
		for(int index = 0; index < listOfEvent.size(); index++) {
			Event currentEvent = listOfEvent.get(index);
			DateTime eventReminder = currentEvent.getEventReminder();
			if((Clock.getBigBangTime() != eventReminder)
					&& !currentEvent.isDone() && !eventReminder.isBeforeNow()) {
				AlarmType newAlarm = new AlarmType(currentEvent.getEventName(), eventReminder);
				reminderList.add(newAlarm);
			}
		}
		return reminderList;
	}
	
	public static void sortList() {
		listOfEvent = sort(listOfEvent);
	}
	
	public static ArrayList<Event> sort(ArrayList<Event> list) {
		Comparator<Event> cmp = new CompareEventByTime();		
		Collections.sort(list, cmp);
		return list;
	}
	
	private static ArrayList<String> getSearchResultsToDisplay() {
		ArrayList<String> listToDisplay = new ArrayList<String>();

		for(int index = 0; index < searchResults.size(); index++) {
			Event currentEvent = searchResults.get(index);
			
				String contentToDisplay = currentEvent.composeContentToDisplayInString();
				contentToDisplay = String.format(displayFormat,  index + 1, contentToDisplay);
				listToDisplay.add(contentToDisplay);
			
		}
		return listToDisplay;
	}
	
	private static String formatEventToStringToDisplay(Event event, int index) {
		String contentToDisplay = event.composeContentToDisplayInString();
		contentToDisplay = String.format(displayFormat, index, contentToDisplay);
		return contentToDisplay;
	}
	
	private static ArrayList<String> getFloatingEventToDisplay() {
		ArrayList<String> floatingEventToGUI = new ArrayList<String>();
		for(int index = 0; index < listOfEvent.size(); index++) {
			Event currentEvent = listOfEvent.get(index);
			if(currentEvent.getEventType() == Event.FLOATING_TYPE && !currentEvent.isDone()) {
				String contentToDisplay = formatEventToStringToDisplay(currentEvent, index + 1);
				floatingEventToGUI.add(contentToDisplay);
			} else {
				return floatingEventToGUI;
			}
		}
		return floatingEventToGUI;
	}
	
	private static ArrayList<String> getUpcomingEventToDisplay() {
		ArrayList<String> upcomingEventToGUI = new ArrayList<String>();
		for(int index = 0; index < listOfEvent.size(); index++) {
			Event currentEvent = listOfEvent.get(index); 
			if(currentEvent.getEventType() != Event.FLOATING_TYPE) {
				if(!currentEvent.isBeforeCurrentTime()) {
					String contentToDisplay = formatEventToStringToDisplay(currentEvent, index + 1);
					upcomingEventToGUI.add(contentToDisplay);					
				} else {
					return upcomingEventToGUI;
				}
			}
		}
		return upcomingEventToGUI;
	}
	
	public static Event getEventFromString(String eventInString) {
		String[] eventContent = eventInString.split("\\..");
		Event newEvent = new Event();
		newEvent.parse(eventContent);
		if(!isValidNewEvent(newEvent)) {
			return new Event();
		}
		return newEvent;
	}
	
	private static boolean isValidNewEvent(Event newEvent) {
		if(isObsoleteEvent(newEvent) || !checkEventID(newEvent)) {
			return false;
		}
		return true;
	}

	private static boolean isObsoleteEvent(Event event) {
		DateTime completedTime = event.getTimeCompleted();
		if(completedTime.isBefore(DateTime.now().minusMonths(1)) && !Clock.isBigBangTime(completedTime)) {
			return true;
		}
		return false;
	}
	
	public static ArrayList<String> getContentToSyncToDatabase() {
		ArrayList<String> listOfEventToString = new ArrayList<String>();
		for(int index = 0; index < listOfEvent.size(); index++) {
			listOfEventToString.add(listOfEvent.get(index).toString());
		}
		return listOfEventToString;
	}

	private static int indexOf(Event event) {
		for (int index = 0; index < listOfEvent.size(); index++) {
			Event currentEvent = listOfEvent.get(index);
			if (currentEvent.isSameEvent(event)) {
				return index;
			}
		}	
		return -1;
	}
	
	public static boolean checkEventID(Event newEvent) {
		for(int index = 0; index < listOfEvent.size(); index++) {
			if(newEvent.isSameEvent(listOfEvent.get(index))) {
				return false;
			}
		}
		return true;
	}

	public static ArrayList<String> getSearchResultsToDisplayInString() {
		searchResults = sort(searchResults);
		return getSearchResultsToDisplay();		
	}
	
	public static ArrayList<String> getListOfEventToDisplayInString() {
		listOfEvent = sort(listOfEvent);
		return getUpcomingEventToDisplay();
	}
	
	public static ArrayList<String> getListOfFloatingEventToDisplayInString() {
		listOfEvent = sort(listOfEvent);
		return getFloatingEventToDisplay();
	}
	
	public static void formatListOfEvent() throws Exception {
		listOfEvent.clear();
		searchResults.clear();
		setUpDataFromDatabase();
	}

	public static void clearListOfEvent() {
		listOfEvent.clear();
	} 	
}
