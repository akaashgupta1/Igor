//@author A0088617R
package logic;
import global.Clock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
  
public class LogicSplitter {
	protected static final String SHORTHAND_UPDATE = "u";
	protected static final String COMMAND_UPDATE = "update";
	protected static final String SHORTHAND_ADD = "+";
	protected static final String COMMAND_ADD = "add";
	protected static final String COMMAND_DONE = "undone";
	protected static final String COMMAND_UNDONE = "done";
	protected static final String COMMAND_UNDO = "undo";
	protected static final String SHORTHAND_DELETE = "-";
	protected static final String COMMAND_DELETE = "delete";
	protected static final String COMMAND_SEARCH = "search";
	private static final String STRING_SPACE = " ";
	private static final String EMPTY_STRING = "";
	protected static int message = 0;
	private static final PatternLib timePattern = PatternLib.getInstance();
	
	public static void setUp() {
		message = 0;
	}
 
	public static int getMessage() {
		return message;
	}
	public static Vector<String> splitInput(String userInput) throws Exception{
		Vector<String> parameterList = new Vector<String>();
		Exception exception = new Exception();
		try {
			userInput = extractCommandTypeAndUpdateInputString(userInput,
					parameterList);
		} catch (Exception e) {
			message = 12;
			throw exception;
			
		}
		try{
		if(parameterList.get(0).toLowerCase().trim().equalsIgnoreCase(COMMAND_ADD)||parameterList.get(0).trim().equalsIgnoreCase(SHORTHAND_ADD))
		{
			parameterList=LogicSplitterAdd.splitInputAdd(userInput,parameterList);
		}
		else if(parameterList.get(0).trim().equalsIgnoreCase(COMMAND_UPDATE)||parameterList.get(0).trim().equalsIgnoreCase(SHORTHAND_UPDATE))
		{
			parameterList=LogicSplitterUpdate.splitInputUpdate(userInput,parameterList);
		}
		else if(parameterList.get(0).trim().equalsIgnoreCase(COMMAND_UNDONE))
		{
			parameterList=LogicSplitterUndone.splitInputUndone(userInput,parameterList);
		}
		else if(parameterList.get(0).trim().equalsIgnoreCase(COMMAND_DONE))
		{
			parameterList=LogicSplitterDone.splitInputDone(userInput,parameterList);
		}
		else if(parameterList.get(0).trim().equalsIgnoreCase(COMMAND_DELETE)||parameterList.get(0).trim().equalsIgnoreCase(SHORTHAND_DELETE))
		{
			parameterList=LogicSplitterDelete.splitInputDelete(userInput,parameterList);
		}
		else if(parameterList.get(0).trim().equalsIgnoreCase(COMMAND_SEARCH))
		{
			parameterList=LogicSplitterSearch.splitInputSearch(userInput,parameterList);
		}
		}
		catch(Exception e)
		{
			throw exception;
		}
		String hashTags=getAllHashTags(userInput);
		if(parameterList.size()>1)
		{
			parameterList.set(1, String.format(parameterList.get(1) + hashTags));
		}
		parameterList = trimAllParameters(parameterList);
		return parameterList;
	}
	protected static String getAllHashTags(String userInput) {
		String hashTagString="";
		Pattern hashPattern=Pattern.compile("(#[a-zA-Z0-9&&[^ ]]{1,} ?)");
		Matcher matches = hashPattern.matcher(userInput);
		while(matches.find())
		{
			hashTagString+=matches.group();
		}
		return hashTagString;
	}
	protected static String extractCommandTypeAndUpdateInputString(
			String command, Vector<String> parameterList) throws Exception {
		command = removeExtraSpace(command);
		command = command + STRING_SPACE;
		parameterList.add(command.substring(0, command.indexOf(STRING_SPACE)));
		command = command.substring(command.indexOf(parameterList.get(0))+parameterList.get(0).length());
		command = command.trim();
		return command;
	}

	private static String removeExtraSpace(String input) {
		input = input.replaceAll(" {1,}", " ").trim();
		return input;
	}
	
	protected static String extractTimeFieldsAndUpdateInputString(
			int[] reminderFound, String userInput,
			Vector<String> parameterList, Vector<Integer> timeIndexes,
			int[] timeCount) throws Exception {
		Vector<String> timeList=new Vector<String>();
		for (int j = 0; j < userInput.length(); j++) {
			String temp = userInput.substring(j, userInput.length());
			String original = temp;
			userInput = userInput.substring(0, j);
			if (timePattern.isFindDateTime(temp)[1] == 0) {
					int indexStart = 0;
					int indexEnd = timePattern.isFindDateTime(temp)[2];
					j += timePattern.isFindDateTime(temp)[2]-1;
					timeList.add(prepareInputToAnalyzeTime(temp.substring(indexStart, indexEnd)));
					userInput += temp.substring(indexStart, indexEnd);
					timeIndexes.add(j);
					timeIndexes.add(indexEnd);
					if (indexEnd < original.length() - 1) {
						userInput = userInput + original.substring(indexEnd);
					} else
						userInput += EMPTY_STRING;
				timeCount[0]++;
				if(timeList.size()>2) {
					timeList.remove(0);
				}
			} else if (timePattern.isFindReminderTime(original)[1] == 0) {
				if (reminderFound[0] < 1) {
					int a[] = timePattern.isFindReminderTime(original);
					userInput = userInput
							+ original.substring(0,
									timePattern.isFindReminderTime(original)[2]);
					if (reminderFound[0] == 0) {
						parameterList.add(original.substring(0,
								timePattern.isFindReminderTime(original)[2]));

					}
					j += a[2];
					int i;
					String formatted = original.substring(
							timePattern.isFindReminderTime(original)[1],
							timePattern.isFindReminderTime(original)[2]);
					for (i = original.length(); i >= 0; i--) {
						if (!original.substring(0, i).contains(formatted)) {
							break;
						}
					}
					if (i < original.length() - 1) {
						userInput = userInput + original.substring(i + 1);
					} else
						userInput += EMPTY_STRING;
				}
				reminderFound[0]++;
			} else {
				userInput = userInput + original;
			}
		}
		parameterList.addAll(timeList);
		return userInput;
	}


	protected static void processEndStartTime(String userInput,
			Vector<String> parameterList, Vector<Integer> timeIndexes)
			throws Exception {
		Vector<String> timeFields = new Vector<String>();
		Vector<Integer> indexes = new Vector<Integer>();
		if (parameterList.size() > 3) {
			for (int i = 2; i < parameterList.size(); i++) {
				if (!parameterList.get(i).toLowerCase().trim().contains("r-")) {
					timeFields.add(parameterList.get(i));
					indexes.add(i);
				}
			}
		}
		if (timeFields.size() == 2) {
			int endFirst = timeIndexes.get(0)+1;
			int startFirst = timeIndexes.get(0) - timeIndexes.get(1)+1;
			int endSecond = timeIndexes.get(2)+1;
			int startSecond = timeIndexes.get(2) - timeIndexes.get(3)+1;
			int firstTimePattern = timePattern.isMatchDateTime(userInput
					.substring(startFirst, endFirst));
			int secondTimePattern = timePattern.isMatchDateTime(userInput
					.substring(startSecond, endSecond));
			DateTime time1 = timePattern
					.getDateTime(prepareInputToAnalyzeTime(userInput.substring(startFirst, endFirst)),
							firstTimePattern);
			DateTime time2 = timePattern.getDateTime(
					prepareInputToAnalyzeTime(userInput.substring(startSecond, endSecond)),
					secondTimePattern);
			if (userInput.substring(endFirst - 1, startSecond + 1).contains(
					"to")) {
				if (firstTimePattern < 14 && firstTimePattern > 11) {
					time1 = Clock.changeToDate(time1, time2);
				}
			}
				parameterList.add(indexes.get(0), time1.toString());
				parameterList.remove(indexes.get(0) + 1);
				parameterList.add(indexes.get(1), time2.toString());
				parameterList.remove(indexes.get(1) + 1);

		}

	}

	protected static void extractKeywordsAlongWithHashTags(String userInput,
			Vector<String> parameterList) {
		if (!userInput.trim().isEmpty()
				&& isInteger(userInput.trim()) == -1) {
			try
			{
			parameterList.add(userInput.substring(0,
					getIndexOfNextComponent(userInput)).trim());
			}
			catch(Exception e)
			{
				parameterList.add(userInput.substring(0,
						getIndexOfNextComponent(userInput)-1).trim());
			}
			userInput = userInput.replace(parameterList.lastElement(),
					EMPTY_STRING);
		} else
			parameterList.add(userInput);
	}

	protected static int getIndexOfNextComponent(String input) {
		int result1= input.length() ,result3= input.length(), result2 = input.length();
		for (int i = 0; i < input.length(); i++) {

			if (input.toLowerCase().indexOf("r-") == i) {
				result1 = i;
				break;
			}
		}
		for (int i=0; i<input.length();i++)
		{
			if(input.indexOf("#") ==i ) {
			result3=i;
			break;
			}
		}
		for (int i = 0; i < input.length(); i++) {
			int timeIndex[] = timePattern.isFindDateTime(input.substring(
					input.length() - 1 - i, input.length()));
			if (timeIndex[1] == 0) {
				result2 = input.length() - 1 - i;
			}
		}
		ArrayList<Integer> arrayForSort = new ArrayList<Integer>();
		arrayForSort.add(result1);
		arrayForSort.add(result2);
		arrayForSort.add(result3);
		Collections.sort(arrayForSort);
		return arrayForSort.get(0);
	}

	protected static void shiftKeywordsToSecondIndex(Vector<String> parameterList) {
		parameterList.add(1, parameterList.lastElement());
		parameterList.remove(parameterList.size() - 1);
	}

	protected static Vector<String> trimAllParameters(Vector<String> parameterList) {
		for (int i = 0; i < parameterList.size(); i++) {
			parameterList.add(i, parameterList.get(i).trim());
			parameterList.remove(i + 1);
		}
		return parameterList;
	}
	
	private static String correctTime(String input) {
		input = input.replaceFirst("\\.", ":");
		Pattern pat = Pattern.compile("^(00?):?(00?) ?((am))?");
		Matcher mat = pat.matcher(input);
		if (mat.find()) {
			int pos = mat.end();  
			input = input.substring(pos, input.length());
			input = insertCharAt(input, "23:59", 0);
		}
		if (input.matches("^\\d\\d?")) {
			StringBuilder sb = new StringBuilder(input);
			sb.append(":00");
			input = sb.toString();
		}
		if (input.matches("^\\d\\d?\\,[0-9tn].{0,}")) {
			input = input.replaceFirst(",", ":00,");
		}
		if (input.matches("^\\d\\d? ?((am)|(pm)).{0,}")) {
			input = input.replaceFirst("(am)", ":00am");
			input = input.replaceFirst("(pm)", ":00pm");
		}
		return input;
	}
	
	private static String toLowerCase(String input) {
		input = input.toLowerCase().trim();
		return input;
	}
	
	
	
	public static String removeSpace(String input) {
		input = input.replaceAll(" ", "");
		return input;
	}
	
	public static String insertCharAt(String input, String ch, int i) {
		StringBuffer buffer = new StringBuffer(input);
		input = buffer.insert(i, ch).toString();	
		return input;
	}
	
	private static String correctDate(String input) {
		input = input.replaceAll("-", "/");
		Pattern pat = Pattern.compile("\\d[a-z&&[^ap]]");
		Matcher mat = pat.matcher(input);
		while(mat.find()) {
			int pos = mat.start();
			input = insertCharAt(input, "/", pos + 1);
		}
		pat = Pattern.compile("[a-z]\\d");
		mat = pat.matcher(input);
		while(mat.find()) {
			int pos = mat.start();
			input = insertCharAt(input, "/", pos + 1);
		}
		return input;
	}
	
	private static String prepareInputToAnalyzeTime(String input) {
		input = toLowerCase(input);
		input = addDateTimeSeparator(input);
		input = removeSpace(input);
		input = correctTime(input);
		input = correctDate(input);
		input = removeDateTimeSeparator(input);
		return input;
	}
	
	private static String addDateTimeSeparator(String input) {
		Pattern pat = Pattern.compile("[0-9] [0-9t]");
		Matcher mat = pat.matcher(input);
		if(mat.find()) {
			int pos = mat.start();
			input = insertCharAt(input, ",", pos + 1);
		}
		pat = Pattern.compile("[0-9] (ne)");
		mat = pat.matcher(input);
		if(mat.find()) {
			int pos = mat.start();
			input = insertCharAt(input, ",", pos + 1);
		}
		pat = Pattern.compile("m [0-9]");
		mat = pat.matcher(input);
		if(mat.find()) {
			int pos = mat.start();
			input = insertCharAt(input, ",", pos + 1);
		}
		pat = Pattern.compile("[a-z] [a-z]");
		mat = pat.matcher(input);
		while(mat.find()) {
			int pos = mat.start();
			input = insertCharAt(input, ",", pos + 1);
			mat = pat.matcher(input);
		}
		return input;
	}
	
	private static String removeDateTimeSeparator(String input) {
		input = input.replaceAll(",", " ");
		return input;
	}
	
	private static int isInteger(String input) {
		int intValue;
		try {
			intValue = Integer.parseInt(input);
		} catch (NumberFormatException e){
			return -1;
		}
		return intValue;
	}
}