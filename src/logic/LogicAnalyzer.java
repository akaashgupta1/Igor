//@author A0088617R
package logic;
 
import global.Clock;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.DateTime;
import org.joda.time.Duration;

 
public class LogicAnalyzer {
	private static final String Priority_Low_Shorthand2 = "l";
	private static final String Priority_Low_Shorthand1 = "low";
	private static final String Priority_Normal_Shorthand2 = "n";
	private static final String Priority_Normal_Shorthand1 = "normal";
	private static final String Priority_High_Shorthand2 = "h";
	private static final String Priority_High_Shorthand1 = "high";
	private static final String ELEMENT_EMPTY = "-1";
	private static final String EXTRACT_NUMBERS_PATTERN = "[0-9]{1,}";
	private static final String STRING_FALSE = "false";
	private static final String STRING_INVALID = "invalid"; 
	private static final String STRING_SPACE = " ";
	private static final String EMPTY_STRING = "";
	private static final String REMINDER_SECOND_SHORTHAND2 = "s";
	private static final String REMINDER_SECOND_SHORTHAND1 = "sec";
	private static final String REMINDER_SECOND = "second";
	private static final String REMINDER_MINUTE_SHORTHAND2 = "m";
	private static final String REMINDER_MINUTE_SHORTHAND1 = "min";
	private static final String REMINDER_MINUTE = "minute";
	private static final String REMINDER_DAY_SHORTHAND2 = "d";
	private static final String REMINDER_DAY_SHORTHAND1 = "day";
	private static final String REMINDER_DAYS = "days";
	private static final String REMINDER_HOUR_SHORTHAND2 = Priority_High_Shorthand2;
	private static final String REMINDER_HOUR_SHORTHAND1 = "hr";
	private static final String REMINDER_HOUR = "hour";
	private static final int MILLISECONDS_IN_SECOND = 1000;
	private static final int MILLISECONDS_IN_MINUTE = 60000;
	private static final int MILLISECONDS_IN_HOUR = 3600000;
	private static final int MILLISECONDS_IN_DAY = 86400000;
	private static final String SPLITTER_HASH = "#";
	private static final String SPLITTER_DATE = "/";
	private static final String SPLITTER_TIME = ":";
	private static final String SPLITTER = "..";
	private static final String Priority_Normal = "NORMAL";
	private static final String Priority_Low = "LOW";
	private static final String Priority_High = "HIGH";
	private static boolean fieldFound[] = { false, false, false, false, false,
		false };
	private static final PatternLib timePattern = PatternLib.getInstance();
	
	public static void setUp() {
		for (int i = 0; i < 6; i++) {
			fieldFound[i] = false;
		}
	}
	public static int getInteger(String[] parameterList) {
		try {
			return Integer.parseInt(parameterList[1].trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	public static String getAddUpdateEventString(String[] parameterList) {
		String eventID = getEventID();
		String eventName = getKeyWords(parameterList);
		if(eventName.trim().isEmpty())
		{
			return "";
		}
		Vector<String> listOfHashTags = getAllHashTags(parameterList);
		String priority = listOfHashTags.get(0);
		listOfHashTags.remove(0);
		Duration eventReminder = getReminderTime(parameterList);
		String eventHashTag = getHashTagsString(listOfHashTags);
		String startTime=getTime(parameterList);
		String endTime=getTime(parameterList);
		if (endTime == EMPTY_STRING) {
			endTime = STRING_INVALID;
		}
		if (startTime == EMPTY_STRING) {
			startTime = STRING_INVALID;
		}
		DateTime start, end;
		if (!(startTime != STRING_INVALID && endTime != STRING_INVALID)) {
			start = timePattern.getDateTime(startTime,
					timePattern.isMatchDateTime(startTime));
			end = timePattern.getDateTime(endTime,
					timePattern.isMatchDateTime(endTime));
		} else {
			start = DateTime.parse(startTime);
			end = DateTime.parse(endTime);
		}
		if ((!startTime.equalsIgnoreCase(STRING_INVALID))
				&& !(endTime.equalsIgnoreCase(STRING_INVALID))) {
			if (end.isBefore(start)) {
				DateTime temp = start;
				start = end;
				end = temp;
			}
		}
		if (startTime != STRING_INVALID) {
			startTime = Clock.toString(start);
		}
		if (endTime != STRING_INVALID) {
			endTime = Clock.toString(end);
		}

		String eventTime = startTime + SPLITTER + endTime;
		String reminderString = "";
		if (startTime != STRING_INVALID && eventReminder!=null) {
			DateTime reminder = start.minusSeconds((int) eventReminder
					.getStandardSeconds());
			reminderString = getReminderTimeInString(reminderString, reminder);
			
		} else {
			reminderString = STRING_INVALID;
		}
		String content = eventID + SPLITTER + eventName + SPLITTER + priority
				+ SPLITTER + eventHashTag + SPLITTER + STRING_FALSE + SPLITTER
				+ reminderString + SPLITTER + eventTime + SPLITTER + STRING_INVALID;
		return content;
	}
	private static String getReminderTimeInString(String reminderString,
			DateTime reminder) {
		if(reminder.getHourOfDay()<10)
		{
			reminderString+="0"+reminder.getHourOfDay();
		}
		else
		{
			reminderString+=reminder.getHourOfDay();
		}
		reminderString+=SPLITTER_TIME;
		if(reminder.getMinuteOfHour()<10)
		{
			reminderString+="0"+reminder.getMinuteOfHour();
		}
		else
		{
			reminderString+=reminder.getMinuteOfHour();
		}
		reminderString+=STRING_SPACE;
		if(reminder.getDayOfMonth()<10)
		{
			reminderString+="0"+reminder.getDayOfMonth();
		}
		else
		{
			reminderString+=reminder.getDayOfMonth();
		}
		reminderString+=SPLITTER_DATE;
		if(reminder.getMonthOfYear()<10)
		{
			reminderString+="0"+reminder.getMonthOfYear();
		}
		else
		{
			reminderString+=reminder.getMonthOfYear();
		}
		reminderString+=SPLITTER_DATE;
		if(reminder.getYear()<10)
		{
			reminderString+="0"+reminder.getYear();
		}
		else
		{
			reminderString+=reminder.getYear();
		}
		return reminderString;
	}

	private static String getKeyWords(String[] parameterList) {
		fieldFound[1] = true;
		if (parameterList[1].trim().contains(SPLITTER_HASH)) {
			return parameterList[1].trim()
					.substring(0, parameterList[1].trim().indexOf(SPLITTER_HASH))
					.trim();
		} else {
			return parameterList[1].trim();
		}
	}

	private static void getPriority(Vector<String> hashTags) {
		boolean found = false;
		for (int i = 0; i < hashTags.size(); i++) {
			if (hashTags.get(i).trim().equalsIgnoreCase(Priority_High_Shorthand1)
					|| hashTags.get(i).trim().equalsIgnoreCase(Priority_High_Shorthand2)) {
				hashTags.remove(i);
				hashTags.add(0, Priority_High);
				found = true;
				return;
			} else if (hashTags.get(i).trim().equalsIgnoreCase(Priority_Normal_Shorthand1)
					|| hashTags.get(i).trim().equalsIgnoreCase(Priority_Normal_Shorthand2)) {
				hashTags.remove(i);
				hashTags.add(0, Priority_Normal);
				found = true;
				return;
			} else if (hashTags.get(i).trim().equalsIgnoreCase(Priority_Low_Shorthand1)
					|| hashTags.get(i).trim().equalsIgnoreCase(Priority_Low_Shorthand2)) {
				hashTags.remove(i);
				hashTags.add(0, Priority_Low);
				found = true;
				return;
			}
		}
		if (found == false) {
			hashTags.add(0, Priority_Normal);
		}
	}

	private static Duration getReminderTime(String[] parameterList) {
		long miliseconds = 0;
		int indexOfReminder = -1;
		Vector<Long> timeQuantity = new Vector<Long>();
		for (int i = 0; i < parameterList.length; i++) {
			if (parameterList[i].trim().contains("r-")
					|| parameterList[i].trim().contains("R-")) {
				fieldFound[i] = true;
				indexOfReminder = i;
				Pattern p = Pattern.compile(EXTRACT_NUMBERS_PATTERN);
				Matcher matches = p.matcher(parameterList[i].trim());
				while (matches.find()) {
					timeQuantity.add((Long.parseLong(matches.group())));
				}
				break;
			}
		}
		if (indexOfReminder != -1) {
				if (parameterList[indexOfReminder].trim()
						.contains(REMINDER_DAY_SHORTHAND2)
						|| parameterList[indexOfReminder].trim()
								.contains(REMINDER_DAY_SHORTHAND1)
						|| parameterList[indexOfReminder].trim()
								.contains(REMINDER_DAYS)) {
					miliseconds += timeQuantity.get(0) * MILLISECONDS_IN_DAY;
				} else if (parameterList[indexOfReminder].trim()
						.contains(REMINDER_HOUR_SHORTHAND2)
						|| parameterList[indexOfReminder].trim()
								.contains(REMINDER_HOUR_SHORTHAND1)
						|| parameterList[indexOfReminder].trim()
								.contains(REMINDER_HOUR)) {
					miliseconds += timeQuantity.get(0) * MILLISECONDS_IN_HOUR;
				} else if (parameterList[indexOfReminder].trim()
						.contains(REMINDER_MINUTE_SHORTHAND2)
						|| parameterList[indexOfReminder].trim()
								.contains(REMINDER_MINUTE_SHORTHAND1)
						|| parameterList[indexOfReminder].trim()
								.contains(REMINDER_MINUTE)) {
					miliseconds += timeQuantity.get(0) * MILLISECONDS_IN_MINUTE;
				} else if (parameterList[indexOfReminder].trim()
						.contains(REMINDER_SECOND_SHORTHAND2)
						|| parameterList[indexOfReminder].trim()
								.contains(REMINDER_SECOND_SHORTHAND1)
						|| parameterList[indexOfReminder].trim()
								.contains(REMINDER_SECOND)) {
					miliseconds += timeQuantity.get(0) * MILLISECONDS_IN_SECOND;
				}
				return (new Duration(miliseconds));
		} else {
			fieldFound[4] = true;
		}
		return null;
	}

	private static Vector<String> getAllHashTags(String[] parameterList) {
		Vector<String> listOfHashTags = getHashTags(parameterList);
		getPriority(listOfHashTags);
		return listOfHashTags;
	}

	public static Vector<String> getHashTags(String[] parameterList) {
		Vector<String> listOfHashTags = new Vector<String>();
		for (int i = 0; i < parameterList.length; i++) {
			int startHashCode = parameterList[i].indexOf(SPLITTER_HASH);
			if (startHashCode > -1) {
				String[] hashCodes = parameterList[i].trim()
						.substring(startHashCode).trim()
						.split(SPLITTER_HASH);
				for (int j = 0; j < hashCodes.length; j++) {
					if (!hashCodes[j].trim().isEmpty()
							|| !hashCodes[j].trim().equalsIgnoreCase(
									EMPTY_STRING)) {
						listOfHashTags.add(hashCodes[j].trim());
					}
				}
				break;
			}
	
		}
		return listOfHashTags;
	}
	private static String getHashTagsString(Vector<String> hashList) {
		String allHash = EMPTY_STRING;
		for (int i = 0; i < hashList.size(); i++) {
			allHash = allHash + "#" + hashList.get(i);
		}
		return allHash;
	}
	
	private static String getTime(String[] parameterList) {
		String endTime = EMPTY_STRING;
		for (int i = 2; i < 5 && i < parameterList.length; i++) {
			if (fieldFound[i] == false
					&& !parameterList[i].equals(ELEMENT_EMPTY)) {
				endTime = EMPTY_STRING;
				fieldFound[i] = true;
				return parameterList[i];
			}
		}

		return endTime;
	}

	private static String getEventID() {
		long id=System.currentTimeMillis();
		String eventId=""+id;
		return eventId;
		

	}
}
