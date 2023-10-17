package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

public class PrepWorkshop {
	private static JFrame logWindow;
	private static JTextField nameBox;
	private static JTextField idBox;
	private static JFormattedTextField participantsBox;
	
	public static void show() {
//		Create Border
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black);
		
//		Setup Window
		logWindow = new JFrame("Enter workshop information");
		logWindow.setSize(640, 480);
		logWindow.setLayout(new BorderLayout());
		logWindow.setIconImage(Main.printerIcon.getImage());
		
//		Create main section
		JPanel logArea = new JPanel(new GridLayout(0, 2, 20, 2));
		logArea.setBorder(bottomBorder);
		logWindow.add(logArea);
		
		nameBox = new JTextField();
		logArea.add(new JLabel("Class or workshop name:"));
		logArea.add(nameBox);
		
		idBox = new JTextField();
		logArea.add(new JLabel("Class or workshop ID:"));
		logArea.add(idBox);
		
		NumberFormat fmt = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(fmt);
		formatter.setMinimum(0);
		formatter.setMaximum(999999999);
		participantsBox = new JFormattedTextField(formatter);
		logArea.add(new JLabel("Expected Participants:"));
		participantsBox.setActionCommand("Submit");
		participantsBox.addActionListener(new ButtonListener());
		logArea.add(participantsBox);
		logArea.setBorder(bottomBorder);
		
//		Create submit button
		JPanel submitArea = new JPanel(new BorderLayout());
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ButtonListener());
		submitArea.add(submit, BorderLayout.EAST);
		logWindow.add(submitArea, BorderLayout.SOUTH);
		
//		Finalize and Display Window
		logWindow.pack();
		logWindow.setVisible(true);
		logWindow.setMinimumSize(logWindow.getSize());
		logWindow.setLocation(new Point(Main.mainWindow.getLocation().x + (Main.mainWindow.getWidth() - logWindow.getWidth()) / 2, Main.mainWindow.getLocation().y + (Main.mainWindow.getHeight() - logWindow.getHeight()) / 2));
	}

	public static void submit() {
		String name = nameBox.getText();
		String id = idBox.getText();
		String participants = participantsBox.getText();
		
//		Clean up inputs
		if (name.length() == 0 || id.length() == 0 || participants.length() == 0) {
			JOptionPane.showMessageDialog(logWindow, "Please fill out all fields.");
			return;
		}

		if (id.charAt(0) == 'c')
			id = id.substring(1);
		if (id.charAt(0) != 'C')
			id = "C" + id;
		
		int parts = Integer.parseInt(participants);
		int budget = Math.min(parts * Settings.getCoursePerStud(), Settings.getCourseBudget());
		
		Database.logUser(id, name, budget);
	}
	
	static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Submit") {
				PrepWorkshop.submit();
			}
		}
		
	}
}
