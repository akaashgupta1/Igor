//@author A0091565Y
package executor;

import event.ListOfEvent;

public class CommandExit extends Command {
	
	public CommandExit(String userInputString, boolean searchState,
			int searchSize, int shownEventSize) {
		super(userInputString, searchState, searchSize, shownEventSize);
	}
	
	public void execute() throws Exception {
		
		try {
			// Save to file before each operation
			ListOfEvent.syncDataToDatabase();
		} catch (Exception e) {
			ListOfEvent.formatListOfEvent();
			setLogAndMessage(LOG_ERROR, ERROR_DATABASE);
			return;
		}
		setLogAndMessage(LOG_MESSAGE, MESSAGE_SAVE);
		System.exit(0);
	}

}
