//@author A00077245B

package Test;


import java.io.IOException;
import java.util.ArrayList;
import logAndException.Log;
import logAndException.MessageHandler;
import event.ListOfEvent;
import executor.Executor;
import fileIO.FileIO;


public class SystemTest {
	private static final String testFileName = "SystemTest.txt";
	private static final String upcomingFileName = "Upcoming.txt";
	private static final String floatingFileName = "Floating.txt";
	private static final String searchFileName = "Search.txt";
	private static final String listOfEventFileName = "ListOfEvent.txt";
	private static FileIO testFileIO, upcomingFileIO, floatingFileIO, searchFileIO, listOfEventFileIO;
	private static ArrayList<String> testCases = new ArrayList<String>();
	private static ArrayList<String> upcoming = new ArrayList<String>();
	private static ArrayList<String> floating = new ArrayList<String>();
	private static ArrayList<String> search = new ArrayList<String>();
	private static ArrayList<String> listOfEventInString = new ArrayList<String>();
	
	public static void main(String[] args) {
		setUp();
		processTest();
	}
	
	private static void setUp() {
		try {
			setUpWhat2Do();
			retrieveTest();
			setUpFileIO();
			clearFile();
		} catch (Exception e) {
			System.out.println("unable to set up files");
		}
	}
	
	private static void setUpWhat2Do() throws Exception {
		Log.setup();
		MessageHandler.setUpList();
		Executor.loadDatabase();
		ListOfEvent.clearListOfEvent();
	}
	
	private static void processTest() {
		for(int index = 0; index < testCases.size(); index ++) {
			String currentLine = testCases.get(index);
			System.out.println(currentLine);
			if(currentLine.startsWith("search") || currentLine.startsWith("s-")) {
				Executor.searchToTrue();
				processCommand(currentLine, index);
			} else if (currentLine.trim().equalsIgnoreCase("back")) {
				Executor.searchToFalse();
			} else  {
				processCommand(currentLine, index);
			}
		}
	}
	
	private static void processCommand(String currentLine, int currentIndex) {
		try {
			Executor.analyze(currentLine);
			upcoming = Executor.printDataBase();
			floating = Executor.printFloatingDataBase();
			search = Executor.printSearchResults();
			sync();
		} catch (Exception e) {
			System.out.printf("error: %s: %d\n", currentLine, currentIndex);
		}
	}
	
	private static void sync() {
		try {
			listOfEventInString = ListOfEvent.getContentToSyncToDatabase();
			listOfEventFileIO.writeContinue(listOfEventInString);
			floatingFileIO.writeContinue(floating);
			upcomingFileIO.writeContinue(upcoming);
			searchFileIO.writeContinue(search);
		} catch (IOException e) {
			System.out.println("unable to write to File");
		}
		
	}
	
	private static void setUpFileIO() throws IOException {
		upcomingFileIO = new FileIO(upcomingFileName);
		floatingFileIO = new FileIO(floatingFileName);
		searchFileIO = new FileIO(searchFileName);
		listOfEventFileIO = new FileIO(listOfEventFileName);
	}
	
	private static void clearFile() throws IOException {
		upcomingFileIO.clearFile();
		floatingFileIO.clearFile();
		searchFileIO.clearFile();
		listOfEventFileIO.clearFile();
	}
	
	private static void retrieveTest() {
		try {
			testFileIO = new FileIO(testFileName);
			testCases = testFileIO.retrieveDatabase();
		} catch (IOException e) {
			System.out.println("unable to set up data");
		}
	}
}
