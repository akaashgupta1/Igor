//@author A00077245B

package actionArchive;

import event.Event;
import event.ListOfEvent;

 
public class ActionArchiveMarkDone extends ActionArchive {
	private Event _eventMarkedDone;
	private static final String MESSAGE_UNDO_MARK_DONE = "Undo: Mark done %1$s";
	
	public ActionArchiveMarkDone(Event eventMarkedDone) {
		_eventMarkedDone = eventMarkedDone;
	}
	
	public String rollBack() {
		String nameOfMarkDoneEvent = ListOfEvent.markUndoneList(_eventMarkedDone);
		String undoMessage = String.format(MESSAGE_UNDO_MARK_DONE, nameOfMarkDoneEvent);
		return undoMessage;
	}
}
