//@author A0088658H
//Unused class

package gui;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.swing.JFrame;

import javax.swing.SwingConstants;

public class AddMenuBarIntoJFrame {
	public static JFrame frame;

	AddMenuBarIntoJFrame(JFrame frameForW2D) {
		frame = frameForW2D;
	}

	public void createMenu() {
		JMenuBar frameMenuBar = new JMenuBar();

		JMenu menu1 = new JMenu("Menu1");
		JMenu menu2 = new JMenu("Menu2");
		JMenu menu3 = new JMenu("Menu3");
		JMenu menu3_internalMenu1 = new JMenu("Menu3_Menu1");
		JMenuItem menuItem1 = new JMenuItem("Menu1_MenuItem1");
		JMenuItem menuItem2 = new JMenuItem("Menu2_MenuItem1");
		JMenuItem menuItem3 = new JMenuItem("Menu2_MenuItem2");
		JMenuItem menuItem4 = new JMenuItem("Menu3_MenuItem1");
		JMenuItem menuItem5 = new JMenuItem("Menu3_Menu1_MenuItem1");
		menu1.add(menuItem1);
		menu2.add(menuItem2);
		menu2.add(menuItem3);
		menu3.add(menuItem4);
		menu3.add(menu3_internalMenu1);
		menu3_internalMenu1.add(menuItem5);
		frameMenuBar.add(menu1);
		frameMenuBar.add(menu2);

		frameMenuBar.add(menu3);
		
		frame.setJMenuBar(frameMenuBar);

	}
}
