//@author A0091565Y
package alarm;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
 
class Dialogs extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AlarmSound sound = new AlarmSound();

	public Dialogs(JFrame parent,String a) {
		super(parent, "REMINDER", true);
		setDisplay(a);				
	}

	private void setDisplay(String a) {
		Container cp = getContentPane();		
		setLayout(cp);		
		createMessage(a, cp);				
		createConfirmButton(cp);
	}

	private void setLayout(Container cp) {
		cp.setSize(300, 100);
		setSize(300,100);
		BoxLayout box = new BoxLayout(cp, BoxLayout.Y_AXIS);
		cp.setLayout(box);
	}

	private void createMessage(String a, Container cp) {
		JLabel message = new JLabel(a); 
		message.setAlignmentY(TOP_ALIGNMENT);
		message.setAlignmentX(CENTER_ALIGNMENT);
		cp.add(message);
		message.setVisible(true);
	}
	
	public void startAlarm() {
		sound.play();
	}

	private void createConfirmButton(Container cp) {
		JButton ok = new JButton("OK");	
		ok.setAlignmentY(BOTTOM_ALIGNMENT);
		ok.setAlignmentX(CENTER_ALIGNMENT);
		ok.setVisible(true);
		this.getRootPane().setDefaultButton(ok);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sound.stop();
				dispose();// Closes the dialog
			}
		});
		cp.add(ok);
	}
}

