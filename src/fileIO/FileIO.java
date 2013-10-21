package fileIO;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import event.ListOfEvent;
 
public class FileIO {
	private String _fileName;
	private BufferedReader _reader;
	private File _database;
	private BufferedWriter _writer;
	
	public FileIO(String name) throws IOException {
		_fileName = name;
		setUpFile();
	}
	
	public void setUpDatabase() throws IOException {
		ArrayList<String> data = retrieveDatabase();
		for(int index = 0; index < data.size(); index++) {
			String currentLine = data.get(index);
			ListOfEvent.add(currentLine);
		}
		return;
	}
	
	public void clearFile() throws IOException {
		FileWriter writer = new FileWriter(_database);
		writer.close();	
	}
	
	private void setUpFile() throws IOException {
		_database = new File(_fileName);
		if (!_database.exists()) {
			_database.createNewFile();
		}
	}
	
	public void syncToDatabase(ArrayList<String> currentListOfEvent) throws IOException {
		setUpBufferedWriter();
		Iterator<String> iterator = currentListOfEvent.iterator();
		while(iterator.hasNext()) {
			String content = iterator.next();
			writeToDatabase(content);
		}
		closeBufferedWriter();
		return;
	}
	
	public void writeContinue(ArrayList<String> currentListOfEvent) throws IOException {
		FileWriter writer = new FileWriter(_database, true);
		writer.write("-------------------------------------\n");
		for(int index = 0; index < currentListOfEvent.size(); index++) {
			writer.write(currentListOfEvent.get(index) + "\n");
		}
		writer.close();
		return;
	}
	
	public ArrayList<String> retrieveDatabase() throws IOException {
		_reader = new BufferedReader(new FileReader(_fileName));
		ArrayList<String> listOfEvent = new ArrayList<String>();
		String currentLine;
		while((currentLine = _reader.readLine()) != null) {
			listOfEvent.add(currentLine);
		}
		return listOfEvent;
	}
	
	private void closeBufferedWriter() throws IOException {
		_writer.close();
	}

	private void setUpBufferedWriter() throws IOException {
		_writer = new BufferedWriter(new FileWriter(_fileName));
	}
	
	private void writeToDatabase(String line) throws IOException {
		_writer.write(line + "\n");
	}

	public void formatDatabase() throws IOException {
		setUpBufferedWriter();
		closeBufferedWriter();
	}
}
