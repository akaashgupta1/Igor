//@author A0091565Y
package executor;

import java.util.*;

import logAndException.MessageHandler;
import logAndException.Log;

import event.ListOfEvent;
import event.ListOfEventObserver;

public class Executor implements ListOfEventObserver {

	private static final int INDEX_FIRST = 0;
	private static final String EXPRESSION_WHITESPACE = "\\s+";
	private static final String COMMAND_SWITCH_UPCOMING = "upcoming";
	private static final String COMMAND_BACK = "back";
	private static ArrayList<String> currentListOfUpcomingEventToDisplay = new ArrayList<String>();
	private static ArrayList<String> currentListOfFloatingEventToDisplay = new ArrayList<String>();
	private static ArrayList<String> searchResults = new ArrayList<String>();
	private static ArrayList<String> feedback = new ArrayList<String>();
	private static final String SHORTHAND_UPDATE = "u";
	private static final String SHORTHAND_DELETE = "-";
	private static final String SHORTHAND_ADD = "+";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_UPDATE = "update";
	private static final String COMMAND_DONE = "done";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_UNDONE = "undone";
	private static final String COMMAND_UNDO = "undo";
	private static boolean searchState = false;	

	private static Executor _instance = new Executor();

	private Executor() {

	}

	public static Executor getInstance() {
		return _instance;
	}	

	public static void searchToFalse() {
		searchState = false;
	}

	public static void searchToTrue() {
		searchState = true;
	}

	public static int analyze(String userInput) throws Exception {
			
		String command = getCommand(userInput);
		Command action;		
		feedback.clear();
		try {
			
			if (isAdd(command)) {
				action = new CommandAdd(userInput,searchState,getSearchSize(),getSizeOfDisplayedEvents());
				action.execute();
				feedback = action.getFeedback();
				return action.getMessageValue();
				
			} else if (isDelete(command)) {
				action = new CommandDelete(userInput,searchState,getSearchSize(),getSizeOfDisplayedEvents());
				action.execute();
				feedback = action.getFeedback();
				return action.getMessageValue();
				
			} else if (isUpdate(command)) {
				action = new CommandUpdate(userInput,searchState,getSearchSize(),getSizeOfDisplayedEvents());
				action.execute();
				feedback = action.getFeedback();
				return action.getMessageValue();
				
			} else if (isSearch(command)) {
				action = new CommandSearch(userInput,searchState,getSearchSize(),getSizeOfDisplayedEvents());
				action.execute();
				feedback = action.getFeedback();
				return action.getMessageValue();
				
			} else if (isDone(command)) {
				action = new CommandDone(userInput,searchState,getSearchSize(),getSizeOfDisplayedEvents());
				action.execute();
				feedback = action.getFeedback();
				return action.getMessageValue();
				
			} else if (isUndone(command)) {
				action = new CommandUndone(userInput,searchState,getSearchSize(),getSizeOfDisplayedEvents());
				action.execute();
				feedback = action.getFeedback();
				return action.getMessageValue();
				
			} else if (isUndo(command)) {
				action = new CommandUndo(userInput,searchState,getSearchSize(),getSizeOfDisplayedEvents());
				action.execute();
				feedback = action.getFeedback();
				return action.getMessageValue();
				
			} else if (command.equalsIgnoreCase(COMMAND_EXIT)) {
				action = new CommandExit(userInput,searchState,getSearchSize(),getSizeOfDisplayedEvents());
				action.execute();
				feedback = action.getFeedback();
				return action.getMessageValue();
			} else if (command.equalsIgnoreCase(COMMAND_BACK)) {
				searchToFalse();
				return 15;
			} else if (command.equalsIgnoreCase(COMMAND_SWITCH_UPCOMING)) {
				return 17;
			} else
				return 5;
		} catch (Exception e) {
			Log.toLog(2, MessageHandler.getMessage(9));
			return 9;
		}	
	}	

	public static ArrayList<String> getFeedback() {
		return feedback;
	}	

	private static boolean isUndo(String command) {
		return command.equalsIgnoreCase(COMMAND_UNDO);
	}

	private static boolean isUndone(String command) {
		return command.equalsIgnoreCase(COMMAND_UNDONE);
	}

	private static boolean isDone(String command) {
		return command.equalsIgnoreCase(COMMAND_DONE);
	}

	private static boolean isAdd(String command) {
		return command.equalsIgnoreCase(COMMAND_ADD)
				|| command.equalsIgnoreCase(SHORTHAND_ADD);
	}

	private static boolean isSearch(String command) {
		return command.equalsIgnoreCase(COMMAND_SEARCH);
	}

	private static boolean isDelete(String command) {
		return command.equalsIgnoreCase(COMMAND_DELETE)
				|| command.equalsIgnoreCase(SHORTHAND_DELETE);
	}

	private static boolean isUpdate(String command) {
		return command.equalsIgnoreCase(COMMAND_UPDATE)
				|| command.equalsIgnoreCase(SHORTHAND_UPDATE);
	}	
	
	private static String getCommand(String userInput) {
		return userInput.split(EXPRESSION_WHITESPACE)[INDEX_FIRST].trim();
	}	
	
	private static int getSizeOfDisplayedEvents() {
		int size = currentListOfUpcomingEventToDisplay.size();
		size += currentListOfFloatingEventToDisplay.size();
		return size;
	}
	
	private static int getSearchSize() {
		return searchResults.size();
	}

	public static ArrayList<String> printDataBase() {

		return currentListOfUpcomingEventToDisplay;
	}

	public static ArrayList<String> printFloatingDataBase() {

		return currentListOfFloatingEventToDisplay;
	}

	public void updateListOfEvent() {
		ListOfEvent.sortList();
		currentListOfFloatingEventToDisplay = ListOfEvent
				.getListOfFloatingEventToDisplayInString();
		currentListOfUpcomingEventToDisplay = ListOfEvent
				.getListOfEventToDisplayInString();
		searchResults = ListOfEvent.getSearchResultsToDisplayInString();
	}

	public static ArrayList<String> printSearchResults() {
		return searchResults;
	}

	public static void loadDatabase() throws Exception {
		ListOfEvent.setUpDataFromDatabase();
	}

	public static void formatDatabase() throws Exception {
		ListOfEvent.formatListOfEvent();
	}

}