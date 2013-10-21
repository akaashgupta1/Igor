//@author A0091565Y
package executor;

import actionArchive.ActionArchiveMarkDone;
import actionArchive.ListOfActionArchive;
import event.Event;
import event.ListOfEvent;

public class CommandDone extends Command {

	public CommandDone(String userInputString, boolean searchState,
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
				if (this.userInputInteger < this.searchSize) {
					Event doneEvent = ListOfEvent
							.markDoneSearch(userInputInteger);
					ListOfActionArchive
							.add(new ActionArchiveMarkDone(doneEvent));
				} else {
					setLogAndMessage(LOG_ERROR, ERROR_OUT_OF_BOUNDS);			
					return;
				}
			} else if (searchState == false) {
				if (this.userInputInteger < this.shownEventSize) {
					Event doneEvent = ListOfEvent
							.markDoneList(this.userInputInteger);
					ListOfActionArchive
							.add(new ActionArchiveMarkDone(doneEvent));
				} else {
					setLogAndMessage(LOG_ERROR, ERROR_OUT_OF_BOUNDS);
					return;
				}
			}			
		} else {
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
		setLogAndMessage(LOG_MESSAGE, SUCCESSFUL_DONE);
		return;
	}
}
