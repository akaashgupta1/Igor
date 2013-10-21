//@author A00077245B

package actionArchive;

import event.Event;
import event.ListOfEvent;

public class ActionArchiveAdd extends ActionArchive {
	private Event  _addedEvent;
	private static final String MESSAGE_UNDO_ADD = "Undo: Add %1$s";
	
	public ActionArchiveAdd(Event addedEvent) {
		_addedEvent = addedEvent;
	}
	
	public Event getAddedEvent() {
		return _addedEvent;
	}

	public String rollBack() {
		String nameOfAddEvent = ListOfEvent.remove(_addedEvent);
		String messageUndo = String.format(MESSAGE_UNDO_ADD, nameOfAddEvent);
		return messageUndo;
	} 
}
