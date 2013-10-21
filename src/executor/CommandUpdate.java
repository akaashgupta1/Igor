//@author A0091565Y
package executor;

import java.util.ArrayList;

import logic.LogicAnalyzer;
import actionArchive.ActionArchiveUpdate;
import actionArchive.ListOfActionArchive;
import event.Event;
import event.ListOfEvent;

public class CommandUpdate extends Command {

	public CommandUpdate(String userInputString, boolean searchState,
			int searchSize, int shownEventSize) {
		super(userInputString, searchState, searchSize, shownEventSize);
	}

	public void execute() throws Exception {

		int splitError = this.splitInput();
		if (splitError != -1) {
			setLogAndMessage(LOG_ERROR, splitError);			
			return;
		}

		int updateIndex = -1;

		try {
			updateIndex = Integer
					.parseInt(parameterList[parameterList.length - 1]) - 1;
		} catch (Exception e) {
			setLogAndMessage(LOG_ERROR, INVALID_COMMAND);			
			return;
		}

		ArrayList<Event> changedEvents;
		String eventToAdd = LogicAnalyzer
				.getAddUpdateEventString(parameterList);

		if (eventToAdd.equals("")) {
			setLogAndMessage(LOG_ERROR, ERROR_NO_KEYWORDS);	
			return;
		}

		if (searchState == false) {
			if (updateIndex < this.shownEventSize) {
				changedEvents = ListOfEvent.updateList(updateIndex, eventToAdd);
			} else {
				setLogAndMessage(LOG_ERROR, ERROR_OUT_OF_BOUNDS);	
				return;
			}

		} else {
			if (updateIndex < searchSize) {
				changedEvents = ListOfEvent.updateSearch(updateIndex,
						eventToAdd);
			} else {
				setLogAndMessage(LOG_ERROR, ERROR_OUT_OF_BOUNDS);
				return;
			}
		}

		if (!changedEvents.isEmpty()) {
			ListOfActionArchive.add(new ActionArchiveUpdate(changedEvents
					.get(0), changedEvents.get(1)));
		}
		updateIndex = -1;

		try {
			// Save to file before each operation
			ListOfEvent.syncDataToDatabase();
		} catch (Exception e) {
			ListOfEvent.formatListOfEvent();
			setLogAndMessage(LOG_ERROR, ERROR_DATABASE);
			return;
		}
		
		ListOfEvent.notifyObservers();
		setLogAndMessage(LOG_MESSAGE, SUCCESSFUL_UPDATE);
		return;
	}

}
