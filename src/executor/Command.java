//@author A0091565Y
package executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import event.ListOfEvent;

import logAndException.Log;
import logAndException.MessageHandler;
import logic.LogicAnalyzer;
import logic.LogicSplitter;

public class Command {

	protected String userInputString;
	protected int userInputInteger;
	protected boolean searchState;
	protected int searchSize;
	protected int shownEventSize;
	protected String[] parameterList;
	protected ArrayList<String>feedback;
	protected int returnVal;
	
	protected final int LOG_MESSAGE = 0;
	protected final int LOG_ERROR = 2;
	
	protected final int SUCCESSFUL_ADD = 0;
	protected final int SUCCESSFUL_DELETE = 1;
	protected final int SUCCESSFUL_UPDATE = 2;
	protected final int SUCCESSFUL_DONE = 3;
	protected final int SUCCESSFUL_UNDONE = 4;
	protected final int ERROR_COMMAND = 5;
	protected final int SUCCESSFUL_UNDO = 6;
	protected final int MESSAGE_SAVE = 7;
	protected final int WARNING_CLASH= 8;
	protected final int INVALID_COMMAND = 9;
	protected final int ERROR_DATABASE = 10;
	protected final int SUCCESSFUL_COMMAND = 11;
	protected final int ERROR_ILLEGAL_COMMAND = 12;
	protected final int ERROR_TIME = 13;
	protected final int ERROR_KEYWORD = 14;
	protected final int MESSAGE_SWITCH = 15;
	protected final int ERROR_NO_KEYWORDS = 16;
	protected final int MESSAGE_UPCOMING= 17;
	protected final int ERROR_UPDATE_FORMAT = 18;
	protected final int ERROR_OUT_OF_BOUNDS = 19;
	protected final int ERROR_NON_INTEGER = 20;
	protected final int ERROR_UNDO = 21;
	
	
	public Command(String userInputString, boolean searchState, int searchSize, int shownEventSize) {
		
		this.userInputString = userInputString;
		this.searchSize = searchSize;
		this.searchState = searchState;
		this.shownEventSize = shownEventSize;
		this.userInputInteger = -1;
		this.feedback = new ArrayList<String>();
		init();
		ListOfEvent.sortList();
		this.parameterList = new String[6];
		Arrays.fill(this.parameterList,0,6,"-1");
				
	}

	protected int splitInput() throws Exception {
		Vector<String> parameters = new Vector<String>();		
		try {
			parameters = LogicSplitter.splitInput(this.userInputString);
			for (int i = 0; i < parameters.size(); i++)
				this.parameterList[i] = parameters.get(i);
			this.userInputInteger = LogicAnalyzer.getInteger(parameterList) - 1;
			return -1;

		} catch (Exception e) {
			if (LogicSplitter.getMessage() == 0) {				
				return 9;
			} else
				return LogicSplitter.getMessage();
		}
	}
	
	protected void setLogAndMessage(int logType,int messageNum) throws Exception {
		Log.toLog(logType, this.userInputString);
		Log.toLog(logType, MessageHandler.getMessage(messageNum));
		this.returnVal = messageNum;
	}
	 
	protected boolean isValidIndex() {
		return (userInputInteger > -1);
	}

	private void init() {
		LogicSplitter.setUp();
		LogicAnalyzer.setUp();
	}
	
	public ArrayList<String> getFeedback() {
		feedback =  ListOfEvent.getFeedback();
		return feedback;
	}
	
	public void execute() throws Exception {
		return ;
	}
	
	public int getMessageValue() {
		return returnVal;
	}
}
