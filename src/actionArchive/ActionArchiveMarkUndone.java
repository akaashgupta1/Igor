//@author A00077245B

package actionArchive;

import event.Event;
import event.ListOfEvent;

 
public class ActionArchiveMarkUndone extends ActionArchive {
	private Event _eventMarkedDone;
	private static final String MESSAGE_UNDO_MARK_UNDONE = "Undo: Mark undone %1$s";
	
	public ActionArchiveMarkUndone(Event eventMarkedDone) {
		_eventMarkedDone = eventMarkedDone;
	}
	
	public String rollBack() {
		String nameOfEventMarkedUndone = ListOfEvent.markDoneList(_eventMarkedDone);
		String undoMessage = String.format(MESSAGE_UNDO_MARK_UNDONE, nameOfEventMarkedUndone);
		return undoMessage;
	}
}