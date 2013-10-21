//@author A0091565Y
package executor;

import actionArchive.ActionArchiveDelete;
import actionArchive.ListOfActionArchive;
import event.ListOfEvent;

public class CommandDelete extends Command {

	public CommandDelete(String userInputString, boolean searchState,
			int searchSize, int shownEventSize) {
		super(userInputString, searchState, searchSize, shownEventSize);
	}

	public void execute() throws Exception {

		int splitError = this.splitInput();
		if (splitError != -1) {
			setLogAndMessage(LOG_ERROR, splitError);			
			return;
		}
		
		assert (userInputInteger>-3);
		if (isValidIndex()) {
			if (searchState == true) {
				if (userInputInteger < this.searchSize) {
					ListOfActionArchive.add(new ActionArchiveDelete(ListOfEvent
							.removeSearch(userInputInteger)));
				} else {
					setLogAndMessage(LOG_ERROR, ERROR_OUT_OF_BOUNDS);					
					return;
				}
			} else {
				if (userInputInteger < this.shownEventSize) {
					ListOfActionArchive.add(new ActionArchiveDelete(ListOfEvent
							.removeList(userInputInteger)));
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
		setLogAndMessage(LOG_MESSAGE, SUCCESSFUL_DELETE);
		return;
	}

}
