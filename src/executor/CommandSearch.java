//@author A0091565Y
package executor;

import java.util.Arrays;
import java.util.Vector;

import logic.LogicAnalyzer;

import event.ListOfEvent;

public class CommandSearch extends Command {

	private static final String EXPRESSION_WHITESPACE = "\\s+";

	public CommandSearch(String userInputString, boolean searchState,
			int searchSize, int shownEventSize) {
		super(userInputString, searchState, searchSize, shownEventSize);
	}

	public void execute() throws Exception {

		int splitError = this.splitInput();
		if (splitError != -1) {
			setLogAndMessage(LOG_ERROR, splitError);			
			return;
		}

		analyzeAndSearch(parameterList);
		ListOfEvent.notifyObservers();		
		setLogAndMessage(LOG_MESSAGE, SUCCESSFUL_COMMAND);			
		return;		
	}

	private void analyzeAndSearch(String[] parameterList) {

		Vector<String> searchWords = getSearchWords(parameterList);
		commenceSearch(searchWords);		
	}

	private Vector<String> getSearchWords(String[] parameterList) {
		Vector<String> searchWords = new Vector<String>();
		searchWords = LogicAnalyzer.getHashTags(parameterList);
		String[] tempArr = (parameterList[1].trim()
				.split(EXPRESSION_WHITESPACE));
		searchWords.addAll(Arrays.asList(tempArr));
		return searchWords;
	}

	private void commenceSearch(Vector<String> searchWords) {
		ListOfEvent.searchInNameAndHashTags(searchWords);
	}

}
