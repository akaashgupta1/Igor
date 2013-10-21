//@author A0088658H
package gui;

import java.util.ArrayList;

import executor.Executor;

public class DataToUser {
	
		private static final String ENDING_LINE_HTML = "</p><b>------------------------------------------------------------------------------------------------------------------------</b></p>";
		private static final String REMINDER_HTML = "<span style=\"color: rgb(228, 108, 10); font-family: courier;\">%s</span></span></b></p>";
		private static final String ALERT_BEFORE_HTML = "<b><i>Alert Before : </b></i></span>&nbsp";
		private static final String END_TIME_HTML = "<span style=\"color: rgb(79, 129, 189); font-family: courier;\">%s</span>&nbsp; ";
		private static final String SPACES_IF_NO_END_TIME_HTML = "<b><i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></i></span>";
		private static final String TO_HTML = "<b><i>to </b></i></span>&nbsp; ";
		private static final String NORMAL_FONT_HTML = "<span style=\"color: rgb(0, 0, 0); font-family: courier;\">";
		private static final String START_TIME_HTML = "<span style=\"color: rgb(75, 172, 198); font-family: courier;\">%s</span>&nbsp;";
		private static final String SPACE_HTML = "&nbsp;";
		private static final String HASHTAGS_HTML = "&nbsp;<span style=\"color: rgb(155, 187, 89); font-family: courier;\">%s</span> &nbsp;";
		private static final String PRIORITY_LOW_HTML = "<span style=\"color: rgb(204,127,50); font-family: courier;\">%s</span> &nbsp;&nbsp;&nbsp;&nbsp;";
		private static final String PRIORITY_NORMAL_HTML = "<span style=\"color: rgb(255,128,0); font-family: courier;\">%s</span> &nbsp;";
		private static final String PRIORITY_HIGH_HTML = "<span style=\"color: red; font-family: courier;\">%s</span> &nbsp;&nbsp;&nbsp;";
		private static final String NAME_UNHIGHLIGHTED_HTML = "<span style=\"color: rgb(0, 32, 96); font-family: courier; \">%s</span>";
		private static final String NAME_HIGHLIGHTED_HTML = "<span style=\"color: rgb(0, 32, 96); font-family: courier; background-color: lime; background-position: initial initial; background-repeat: initial initial;\">%s</span>";
		private static final String ID_HTML = "<br><p class=\"MsoNormal\"><b>%s";
		private static final String STARTING_HTML = "<html>";
		private static final String ENDING_HTML = "</p></html>";
		
		//Constants related to positioning in containers,
		private static final int NUMBER_OF_FIELDS = 8;
		private static final int INDEX_HASHTAG = 3;
		private static final int INDEX_EVENT_NAME = 1;
		private static final String DOT_DOT = "..";
		private static final int LENGHT_MAX_LARGE_WORD = 15;
		private static final int LENGHT_MAX_SMALL_WORD = 6;
		private static final int POSITION_START = 0;
		private static final int LENGHT_LARGE_WORD = 25;
		private static final int LENGTH_SMALL_WORD = 10;
		
	
	
	ArrayList<String> upcomingEvents = new ArrayList<String>();
	ArrayList<String> floatingEvents = new ArrayList<String>();
	ArrayList<String> searchResults = new ArrayList<String>();
	ArrayList<ArrayList<String>> formattedUpcomingEvents = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> formattedFloatingEvents = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> formattedSearchResults = new ArrayList<ArrayList<String>>();
	
	public String data_upcoming_events = "";
	public String data_floating_events = "";
	public String data_search_results = "";
	
	String upcoming_database="";
	String floating_database="";
	String search_database="";
	
	int upcoming_start_position = 0;
	int upcoming_end_position = 6;
	int floating_start_position = 0;
	int floating_end_position = 6;
	int search_start_position = 0;
	int search_end_position = 6;
	/*
	private static DataToUser _instance = new DataToUser();
	
	private DataToUser(){
		
	}
	
	public static DataToUser getInstance() {
		return _instance;
	}
*/
	
	private void obtainDataFromDatabase() {
		upcomingEvents = obtainUpcomingEventsFromDatabase();
		floatingEvents = obtainFloatingEventsFromDatabase();
		searchResults = obtainSearchResultsFromDatabase();
	}
	private ArrayList<String> obtainUpcomingEventsFromDatabase() {
		ArrayList<String> upcomingEvents;
		upcomingEvents = Executor.printDataBase();
		return upcomingEvents;
	}
	private ArrayList<String> obtainFloatingEventsFromDatabase() {
		ArrayList<String> floatingEvents;
		floatingEvents = Executor.printFloatingDataBase();
		return floatingEvents;
	}
	private ArrayList<String> obtainSearchResultsFromDatabase() {
		ArrayList<String> searchResults;
		searchResults = Executor.printSearchResults();
		return searchResults;
	}
	private void initializeFormattedDatabasesToNull() {
		formattedUpcomingEvents.clear();
		formattedFloatingEvents.clear();
		formattedSearchResults.clear();
		for (int i = 0; i < 100; i++) {
			ArrayList<String> temporaryList = new ArrayList<String>();
			for (int j = 0; j < 10; j++) {
				temporaryList.add("");
			}
			formattedUpcomingEvents.add(temporaryList);
		}
		for (int i = 0; i < 100; i++) {

			ArrayList<String> temporaryList = new ArrayList<String>();
			for (int j = 0; j < 10; j++) {
				temporaryList.add("");
			}
			formattedFloatingEvents.add(temporaryList);
		}
		for (int i = 0; i < 100; i++) {
			ArrayList<String> temporaryList = new ArrayList<String>();
			for (int j = 0; j < 10; j++) {
				temporaryList.add("");
			}
			formattedSearchResults.add(temporaryList);
		}
	}
	private void formatLocalDatabase() {
		format(formattedUpcomingEvents, upcomingEvents);
		format(formattedFloatingEvents, floatingEvents);
		format(formattedSearchResults, searchResults);
	}
	private void format(ArrayList<ArrayList<String>> formattedDisplay,
			ArrayList<String> events) {

		for (int i = 0; i < events.size(); i++) {

			String[] individualEvents;
			individualEvents = obtainFromOriginalDatabase(events, i);

			String[] individualEventsForFormatting = new String[NUMBER_OF_FIELDS];
			intializeToNull(individualEventsForFormatting);
			transferElements(individualEvents, individualEventsForFormatting);

			resizeLongFields(individualEventsForFormatting);

			placeElementsIntoFormattedList(formattedDisplay, i,
					individualEventsForFormatting);
		}
	}
	private String[] obtainFromOriginalDatabase(ArrayList<String> events, int i) {
		String[] individualEvents;
		individualEvents = events.get(i).split("\\..");
		return individualEvents;
	}
	private void intializeToNull(String[] individualEventsForFormatting) {
		for (int j = 0; j < NUMBER_OF_FIELDS; j++) {
			individualEventsForFormatting[j] = "";
		}
	}
	private void transferElements(String[] individualEvents,
			String[] individualEventsForFormatting) {
		for (int j = 0; j < individualEvents.length; j++) {
			individualEventsForFormatting[j] = individualEvents[j];
		}
	}
	private void resizeLongFields(String[] individualEventsForFormatting) {
		individualEventsForFormatting[INDEX_EVENT_NAME] = largeResizeForDisplay(individualEventsForFormatting[1]);
		individualEventsForFormatting[INDEX_HASHTAG] = smallResizeForDisplay(individualEventsForFormatting[3]);
	}
	private void placeElementsIntoFormattedList(
			ArrayList<ArrayList<String>> formattedDisplay, int i,
			String[] individualEventsForFormatting) {
		formattedDisplay.get(i + 1).set(0, individualEventsForFormatting[0]);
		formattedDisplay.get(i + 1).set(1, individualEventsForFormatting[1]);
		formattedDisplay.get(i + 1).set(2, individualEventsForFormatting[2]);
		formattedDisplay.get(i + 1).set(3, individualEventsForFormatting[3]);
		formattedDisplay.get(i + 1).set(4, individualEventsForFormatting[4]);
		formattedDisplay.get(i + 1).set(5, individualEventsForFormatting[5]);
		formattedDisplay.get(i + 1).set(6, individualEventsForFormatting[6]);
		formattedDisplay.get(i + 1).set(7, individualEventsForFormatting[7]);
	}
	private String smallResizeForDisplay(String wordToResize) {
		boolean is_OK_length = wordToResize.length() < LENGTH_SMALL_WORD;
		if (is_OK_length)
			return wordToResize;
		else {
			return wordToResize
					.substring(POSITION_START, LENGHT_MAX_SMALL_WORD)
					+ DOT_DOT;
		}
	}

	private String largeResizeForDisplay(String wordToResize) {
		boolean is_OK_length = wordToResize.length() < LENGHT_LARGE_WORD;
		if (is_OK_length)
			return wordToResize;
		else {
			return wordToResize
					.substring(POSITION_START, LENGHT_MAX_LARGE_WORD)
					+ DOT_DOT;
		}
	}
	private void setUpDisplayStrings() {
		data_upcoming_events = STARTING_HTML;
		data_floating_events = STARTING_HTML;
		data_search_results = STARTING_HTML;
	}

	private void updateEndPositions() {
		upcoming_end_position = upcoming_start_position + 6;

		floating_end_position = floating_start_position + 6;

		search_end_position = search_start_position + 6;
	}
	private void fillInDatabases() {
		data_upcoming_events = fillInUpcomingEventsDatabase();
		data_floating_events = fillInFloatingEventsDatabase();
		data_search_results = fillInSearchResultsDatabase();
	}
	private String fillInUpcomingEventsDatabase() {
		for (int i = upcoming_start_position; i < upcoming_end_position; i++) {
			boolean fieldIsEmpty = formattedUpcomingEvents.get(i + 1).get(0)
					.equals("");
			if (!fieldIsEmpty) {

				data_upcoming_events += String.format(
						ID_HTML,
						formattedUpcomingEvents.get(i + 1).get(0) + ")");

				try {
					if (Integer.parseInt(formattedUpcomingEvents.get(i + 1)
							.get(0).trim()) < 10) {
						data_upcoming_events += SPACE_HTML;
					}
				} catch (Exception e) {
					// do nothing
				}
				if (formattedUpcomingEvents.get(i + 1).get(4).contains("true")) {
					data_upcoming_events += String
							.format(NAME_HIGHLIGHTED_HTML,
									formattedUpcomingEvents.get(i + 1).get(1));
				} else {
					data_upcoming_events += String
							.format(NAME_UNHIGHLIGHTED_HTML,
									formattedUpcomingEvents.get(i + 1).get(1));
				}
				for (int j = 0; j < getSpacesForLargeWord(formattedUpcomingEvents
						.get(i + 1).get(1)); j++) {
					data_upcoming_events += SPACE_HTML;
				}
				if (formattedUpcomingEvents.get(i + 1).get(2).contains("HIGH")) {
					data_upcoming_events += String
							.format(PRIORITY_HIGH_HTML,
									formattedUpcomingEvents.get(i + 1).get(2));
				}
				if (formattedUpcomingEvents.get(i + 1).get(2).contains("NORMAL")) {
					data_upcoming_events += String
							.format(PRIORITY_NORMAL_HTML,
									formattedUpcomingEvents.get(i + 1).get(2));
				}
				if (formattedUpcomingEvents.get(i + 1).get(2).contains("LOW")) {
					data_upcoming_events += String
							.format(PRIORITY_LOW_HTML,
									formattedUpcomingEvents.get(i + 1).get(2));
				}
				data_upcoming_events += String
						.format(HASHTAGS_HTML,
								formattedUpcomingEvents.get(i + 1).get(3));
				for (int j = 0; j < getSpacesForSmallWord(formattedUpcomingEvents
						.get(i + 1).get(3)); j++) {
					data_upcoming_events += SPACE_HTML;
				}
				data_upcoming_events += String.format(START_TIME_HTML,formattedUpcomingEvents.get(i + 1).get(5));

				data_upcoming_events += NORMAL_FONT_HTML;
				if (formattedUpcomingEvents.get(i + 1).get(6).contains(":")) {
					data_upcoming_events += TO_HTML;
				} else {

					data_upcoming_events += SPACES_IF_NO_END_TIME_HTML;
				}
				data_upcoming_events+=String.format(END_TIME_HTML, formattedUpcomingEvents.get(i + 1).get(6));
				data_upcoming_events += NORMAL_FONT_HTML;
				if (formattedUpcomingEvents.get(i + 1).get(7).contains("r-")) {
					data_upcoming_events += ALERT_BEFORE_HTML;
				} 
				
				data_upcoming_events += String.format(REMINDER_HTML,
						formattedUpcomingEvents.get(i + 1).get(7).replace("r-", ""));
				data_upcoming_events += ENDING_LINE_HTML;

			}
		}
		return data_upcoming_events;
	}	private String fillInFloatingEventsDatabase() {
		for (int i = floating_start_position; i < floating_end_position
				&& !(formattedFloatingEvents.get(i + 1).get(0).equals("")); i++) {

			data_floating_events += String.format(
					ID_HTML, formattedFloatingEvents
							.get(i + 1).get(0) + ")");

			try {
				if (Integer.parseInt(formattedFloatingEvents.get(i + 1).get(0)
						.trim()) < 10) {
					data_floating_events += SPACE_HTML;
				}
			} catch (Exception e) {
				// do nothing
			}
			if (formattedFloatingEvents.get(i + 1).get(4).contains("true")) {
				data_floating_events += String
						.format(NAME_HIGHLIGHTED_HTML,
								formattedFloatingEvents.get(i + 1).get(1));
			} else {
				data_floating_events += String
						.format(NAME_UNHIGHLIGHTED_HTML,
								formattedFloatingEvents.get(i + 1).get(1));
			}
			for (int j = 0; j < getSpacesForLargeWord(formattedFloatingEvents
					.get(i + 1).get(1)); j++) {
				data_floating_events += SPACE_HTML;
			}
			if (formattedFloatingEvents.get(i + 1).get(2).contains("HIGH")) {
				data_floating_events += String
						.format(PRIORITY_HIGH_HTML,
								formattedFloatingEvents.get(i + 1).get(2));
			}
			if (formattedFloatingEvents.get(i + 1).get(2).contains("NORMAL")) {
				data_floating_events += String
						.format(PRIORITY_NORMAL_HTML,
								formattedFloatingEvents.get(i + 1).get(2));
			}
			if (formattedFloatingEvents.get(i + 1).get(2).contains("LOW")) {
				data_floating_events += String
						.format(PRIORITY_LOW_HTML,
								formattedFloatingEvents.get(i + 1).get(2));
			}
			data_floating_events += String
					.format(HASHTAGS_HTML,
							formattedFloatingEvents.get(i + 1).get(3));
			for (int j = 0; j < getSpacesForLargeWord(formattedFloatingEvents
					.get(i + 1).get(3)); j++) {
				data_floating_events += SPACE_HTML;
			}
			data_floating_events +=ENDING_LINE_HTML ;
		}
		return data_floating_events;
	}	private String fillInSearchResultsDatabase() {
		for (int i = search_start_position; i < search_end_position; i++) {
			boolean fieldIsEmpty = formattedSearchResults.get(i + 1).get(0)
					.equals("");
			if (!fieldIsEmpty) {

				data_search_results += String.format(
						ID_HTML,
						formattedSearchResults.get(i + 1).get(0) + ")");

				try {
					if (Integer.parseInt(formattedSearchResults.get(i + 1)
							.get(0).trim()) < 10) {
						data_search_results += SPACE_HTML;
					}
				} catch (Exception e) {
					// do nothing
				}
				if (formattedSearchResults.get(i + 1).get(4).contains("true")) {
					data_search_results += String
							.format(NAME_HIGHLIGHTED_HTML,
									formattedSearchResults.get(i + 1).get(1));
				} else {
					data_search_results += String
							.format(NAME_UNHIGHLIGHTED_HTML,
									formattedSearchResults.get(i + 1).get(1));
				}
				for (int j = 0; j < getSpacesForLargeWord(formattedSearchResults
						.get(i + 1).get(1)); j++) {
					data_search_results += SPACE_HTML;
				}
				if (formattedSearchResults.get(i + 1).get(2).contains("HIGH")) {
					data_search_results += String
							.format(PRIORITY_HIGH_HTML,
									formattedSearchResults.get(i + 1).get(2));
				}
				if (formattedSearchResults.get(i + 1).get(2).contains("NORMAL")) {
					data_search_results += String
							.format(PRIORITY_NORMAL_HTML,
									formattedSearchResults.get(i + 1).get(2));
				}
				if (formattedSearchResults.get(i + 1).get(2).contains("LOW")) {
					data_search_results += String
							.format(PRIORITY_LOW_HTML,
									formattedSearchResults.get(i + 1).get(2));
				}
				data_search_results += String
						.format(HASHTAGS_HTML,
								formattedSearchResults.get(i + 1).get(3));
				for (int j = 0; j < getSpacesForSmallWord(formattedSearchResults
						.get(i + 1).get(3)); j++) {
					data_search_results += SPACE_HTML;
				}
				data_search_results += String.format(START_TIME_HTML,formattedSearchResults.get(i + 1).get(5));

				data_search_results += NORMAL_FONT_HTML;
				if (formattedSearchResults.get(i + 1).get(6).contains(":")) {
					data_search_results += TO_HTML;
				} else {

					data_search_results += SPACES_IF_NO_END_TIME_HTML;
				}
				data_search_results+=String.format(END_TIME_HTML, formattedSearchResults.get(i + 1).get(6));
				data_search_results += NORMAL_FONT_HTML;
				if (formattedSearchResults.get(i + 1).get(7).contains("r-")) {
					data_search_results += ALERT_BEFORE_HTML;
				} 
				
				data_search_results += String.format(REMINDER_HTML,
						formattedSearchResults.get(i + 1).get(7).replace("r-", ""));
				data_search_results += ENDING_LINE_HTML;

			}
		}
		return data_search_results;
	}
	private int getSpacesForSmallWord(String word) {
		int number_spaces_required = LENGTH_SMALL_WORD - word.length();
		return number_spaces_required;

	}

	private int getSpacesForLargeWord(String word) {
		int number_spaces_required = LENGHT_LARGE_WORD - word.length();

		return number_spaces_required;
	}
	private void endDisplayStrings() {
		data_upcoming_events += ENDING_HTML;
		data_floating_events += ENDING_HTML;
		data_search_results += ENDING_HTML;
	}
		public void getResults(){
			obtainDataFromDatabase();
			initializeFormattedDatabasesToNull();
			formatLocalDatabase();
			setUpDisplayStrings();
			updateEndPositions();
			fillInDatabases();
			endDisplayStrings();
			
		}
	}




