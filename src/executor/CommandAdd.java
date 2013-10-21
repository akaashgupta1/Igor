//@author A0091565Y
package executor;

import logic.LogicAnalyzer;
import actionArchive.ActionArchiveAdd;
import actionArchive.ListOfActionArchive;
import event.Event;
import event.ListOfEvent;

public class CommandAdd extends Command{
	
	public CommandAdd(String userInputString, boolean searchState, int searchSize, int shownEventSize){
		super(userInputString, searchState,  searchSize, shownEventSize);		
	}
	
	private static Event analyzeAddInput(String[] parameterList) {

		String eventToAdd = LogicAnalyzer
				.getAddUpdateEventString(parameterList);
		return ListOfEvent.add(eventToAdd);

	}
		
	public void execute() throws Exception {
		
		int splitError = this.splitInput();
		if (splitError!= -1) {
			setLogAndMessage(LOG_ERROR, splitError);			
			return;
		}
		
		Event newEvent = analyzeAddInput(parameterList);
		if (newEvent != null) {
			ListOfActionArchive.add(new ActionArchiveAdd(newEvent));
			try {
				// Saving to file after add
				ListOfEvent.syncDataToDatabase();				
				ListOfEvent.notifyObservers();
				setLogAndMessage(LOG_MESSAGE,SUCCESSFUL_ADD);
				return;
			} catch (Exception e) {				
				setLogAndMessage(LOG_ERROR,ERROR_DATABASE);
				return;
			}			
			
		} else {			
			setLogAndMessage(LOG_ERROR,INVALID_COMMAND);			
			return;
		}
	}

}
