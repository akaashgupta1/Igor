//@author A0088617R
package logAndException;
  
import java.util.Vector;

public class MessageHandler {
	private static Vector<String> listOfMessages;
	public static void setUpList()
	{	
		listOfMessages=new Vector<String>();
		listOfMessages.add("Event Added Successfully.");//0
		listOfMessages.add("Event Deleted Successfully.");//1
		listOfMessages.add("Event Updated Successfully.");//2
		listOfMessages.add("Event Marked as COMPLETE Successfully.");//3
		listOfMessages.add("Event Marked as INCOMPLETE Successfully.");//4
		listOfMessages.add("Error: Command Not Found.");//5
		listOfMessages.add("Undo Successful.");//6
		listOfMessages.add("Saving Your Data and Exiting.");//7
		listOfMessages.add("Clash Exists with Another Event - Add anyway ?");//8
		listOfMessages.add("Error: Could Not Perform Action - Please Check the Command entered.");//9
		listOfMessages.add("Error: Database Corrupted - Formating Now.");//10
		listOfMessages.add("Command Performed Successfully.");//11
		listOfMessages.add("Error: Illegal Command Entered.");//12
		listOfMessages.add("Error: Illegal Content Entered.Please Check Your START/END/REMINDER Field Formats.");//13
		listOfMessages.add("Error: Illegal Content Entered.Please Check Your Keywords.");//14
		listOfMessages.add("Switched to Original View");//15
		listOfMessages.add("Error: No Keywords Entered");//16
		listOfMessages.add("Switched to Upcoming Events View");//17
		listOfMessages.add("Error: Update Format Wrong.");//18
		listOfMessages.add("Error: No Event at Entered Index.");//19
		listOfMessages.add("Error: Illegal Keyword Entered.Please Enter a Valid Integer");//20
	}
	
	public static void setMessage(int index, String message) {
		listOfMessages.remove(index);
		listOfMessages.add(index, message);
	}
	
	public static String getMessage(int index)
	{
		return listOfMessages.get(index);
	}
}
