package logic;
 
import global.Clock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
 
public class PatternDateTime {
	private Pattern _pattern;
	private String _timeFormat;

	public PatternDateTime(Pattern pattern, String timeFormat) {
		_pattern = pattern;
		_timeFormat = timeFormat;
	}

	public boolean isMatches(String input) {
		Matcher matcher = _pattern.matcher(input);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public int[] isFind(String input) {
		Matcher matcher = _pattern.matcher(input);
		int[] pos = { -1, -1 };
		if (matcher.find()) {
			pos[0] = matcher.start();
			pos[1] = matcher.end();
			return pos;
		} else {
			return pos;
		}
	}

	public DateTime getDateTime(String input) {
		DateTime newDateTime = null;
		if (_timeFormat.contains("pattern") && _timeFormat.contains("Time")) {
			newDateTime = getDateTimeSpecial(input);
		} else if (_timeFormat.contains("pattern")) {
			newDateTime = getDateSpecial(input);
		} else if (_timeFormat.contains("r-")) {
			newDateTime = getReminder(input);
		} else {
			DateTimeFormatter dateFormat = DateTimeFormat
					.forPattern(_timeFormat);
			newDateTime = dateFormat.parseDateTime(input);
			if (newDateTime.getYearOfCentury() == 0) {
				newDateTime.plusYears(DateTime.now().getYearOfCentury());
			}
		}
		return newDateTime;
	}

	public DateTime getReminder(String input) {
		int number = extractFirstNumber(input);
		int minuteToMillis = 60 * 1000;
		int hourToMillis = 60 * minuteToMillis;
		int dayToMillis = 24 * hourToMillis;
		if (input.contains("h")) {
			number = number * hourToMillis;
		} else if (input.contains("m")) {
			number = number * minuteToMillis;
		} else {
			number = number * dayToMillis;
		}
		DateTime date = Clock.getBigBangTime();
		return date.plusMillis(number);
	} 

	public static int extractFirstNumber(String input) {
		Pattern pat = Pattern.compile("[a-z\\- &&[^0-9]][0-9][0-9]?[a-z &&[^0-9]]");
		Matcher mat = pat.matcher(input);
		if(mat.find()) {
			int posStart = mat.start() + 1;
			int posEnd = mat.end() - 1;
			int firstNumber = Integer.parseInt(input.substring(posStart, posEnd));
			return firstNumber;
		}
		return -1;
	}
	
	public DateTime getTime(String input) {
		DateTime dt;
		if (input.contains("am") || input.contains("pm")) {
			DateTimeFormatter timeFormat = DateTimeFormat.forPattern("hh:mmaa");
			dt = timeFormat.parseDateTime(input);
		} else {
			DateTimeFormatter timeFormat = DateTimeFormat.forPattern("HH:mm");
			dt = timeFormat.parseDateTime(input);
		}
		return dt;
	}

	public String removeFirst(String input) {
		int firstSpace = input.indexOf(" ");
		if (firstSpace >= 0) {
			return input.substring(firstSpace, input.length()).trim();
		} else {
			return input;
		}
	}

	public String getFirst(String input) {
		int firstSpace = input.indexOf(" ");
		if (firstSpace >= 0) {
			return input.substring(0, firstSpace);
		} else {
			return input;
		}
	}

	public int getDayOfWeek(String day) {
		if (day.contains("mon")) {
			return 1;
		} else if (day.contains("tue")) {
			return 2;
		} else if (day.contains("wed")) {
			return 3;
		} else if (day.contains("thu")) {
			return 4;
		} else if (day.contains("fri")) {
			return 5;
		} else if (day.contains("sat")) {
			return 6;
		} else if (day.contains("sun")) {
			return 7;
		}
		return 0;
	}

	public DateTime getDate(String input) {
		DateTime today = new DateTime();
		if (input.contains("today")) {
			return today.minus(today.getMillisOfDay());
		}
		if (input.contains("tomorrow") || input.contains("tmr")) {
			today = today.minus(today.getMillisOfDay());
			today = today.plusDays(1);
			return today;
		}

		int dayOfWeek = getDayOfWeek(input);

		String weekIndicator = getFirst(input);

		if (weekIndicator.equalsIgnoreCase("this")) {
			today = today.minusDays(today.getDayOfWeek());
			today = today.plusDays(dayOfWeek);
		} else {
			today = today.minusDays(today.getDayOfWeek());
			today = today.plusDays(dayOfWeek + 7);
		}
		return today.minus(today.getMillisOfDay());
	}

	public DateTime getDateTimeSpecial(String input) {
		if (input.contains("this") || input.contains("next")
				|| input.contains("today") || input.contains("tmr")
				|| input.contains("tomorrow")) {
			DateTime dt = getDate(removeFirst(input));
			if (!input.equalsIgnoreCase(removeFirst(input)))
				dt = dt.plus(getTime(getFirst(input)).getMillisOfDay());
			return dt;
		} else if (input.contains(":")) {
			DateTime dt = new DateTime();
			dt = dt.minusMillis(dt.getMillisOfDay());
			dt = dt.plusMillis(getTime(input).getMillisOfDay());
			return dt;
		} else {
			return getDate(input);
		}
	}
	//@author A0088617R
	private DateTime getDateSpecial(String input) {
		if (input.contains("this") || input.contains("next")
				|| input.contains("today") || input.contains("tmr")
				|| input.contains("tomorrow")) {
			DateTime dt = getDate(input);
			return dt;
		} else
			return null;
	}
}
