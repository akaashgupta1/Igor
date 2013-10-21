//@author A0091565Y
package executor;

import actionArchive.ActionArchiveMarkUndone;
import actionArchive.ListOfActionArchive;
import event.Event;
import event.ListOfEvent;

public class CommandUndone extends Command {

	public CommandUndone(String userInputString, boolean searchState,
			int searchSize, int shownEventSize) {
		super(userInputString, searchState, searchSize, shownEventSize);
	}

	public void execute() throws Exception {

		int splitError = this.splitInput();
		if (splitError != -1) {
			setLogAndMessage(LOG_ERROR, splitError);			
			return;
		}
		
		if (isValidIndex()) {
			if (searchState == true) {
				if (userInputInteger < searchSize) {
					Event eventUndone = ListOfEvent
							.markUndoneSearch(userInputInteger);
					ListOfActionArchive.add(new ActionArchiveMarkUndone(
							eventUndone));
				} else {
					setLogAndMessage(LOG_ERROR, ERROR_OUT_OF_BOUNDS);			
					return;					
				}
			} else if (searchState == false) {
				if (userInputInteger < this.shownEventSize) {
					Event eventUndone = ListOfEvent
							.markUndoneList(userInputInteger);
					ListOfActionArchive.add(new ActionArchiveMarkUndone(
							eventUndone));
				} else {
					setLogAndMessage(LOG_ERROR, ERROR_OUT_OF_BOUNDS);	
					return;
				}
			}			
		}else {
			setLogAndMessage(LOG_ERROR, ERROR_NON_INTEGER);	
			return;
		}
		
		try {
			// Save to file before each operation
			ListOfEvent.syncDataToDatabase();
		} catch (Exception e) {
			ListOfEvent.formatListOfEvent();
			setLogAndMessage(LOG_ERROR, ERROR_DATABASE);	
			return;
		}
		
		ListOfEvent.notifyObservers();
		setLogAndMessage(LOG_MESSAGE, SUCCESSFUL_UNDONE);	
		return;		
	}

}
