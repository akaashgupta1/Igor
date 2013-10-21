//@author A00077245B

package actionArchive;
 
import java.util.LinkedList;

public class ListOfActionArchive {
	private static LinkedList<ActionArchive> listOfUserLog = new LinkedList<ActionArchive>();
	
	public static void add(ActionArchive userLog) {
		listOfUserLog.add(userLog);
		return;
	}

	public static String undo() {
		ActionArchive lastActionArchive = listOfUserLog.pollLast();
		if(lastActionArchive == null) {
			return "Error: No undo action available";
		}
		return lastActionArchive.rollBack();
	}	
}
