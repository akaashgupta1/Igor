//@author A0091565Y
package alarm;

import javax.swing.ImageIcon;

class DialogRun  implements Runnable {
  
	private String event;
	private Dialogs reminder;
	
	DialogRun(String ev) {
		event = ev;
		reminder = new Dialogs(null, event);
	}

	private static void run(Dialogs applet) {
		
		applet.setAlwaysOnTop(true);
		applet.setLocationRelativeTo(null);		
		applet.setIconImage(new ImageIcon(applet.getClass().getResource("/logo.jpg")).getImage());
		applet.startAlarm();
		applet.setVisible(true);
		
	}	
	
	@Override
	public void run() {
		run(reminder);		
	}
}