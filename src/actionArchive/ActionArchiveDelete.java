//@author A00077245B

package actionArchive;

import event.Event;
import event.ListOfEvent;
 
 
public class ActionArchiveDelete extends ActionArchive{
	private Event _deletedEvent;
	private static final String MESSAGE_UNDO_DELETE = "Undo: Delete %1$s";
	
	public ActionArchiveDelete(Event deletedEvent) {
		_deletedEvent = deletedEvent;
	}

	public Event getDeletedEvent() {
		return _deletedEvent;
	}
	
	public String rollBack() {
		String nameOfDeletedEvent = ListOfEvent.add(_deletedEvent);
		String messageUndo = String.format(MESSAGE_UNDO_DELETE, nameOfDeletedEvent);
		return messageUndo;
	}
}
