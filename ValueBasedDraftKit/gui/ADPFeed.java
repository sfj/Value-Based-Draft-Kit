package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import service.Service;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ADPFeed extends JFrame {

	private JPanel contentPane;
	
	private JTextArea inputfield;

	/**
	 * Create the frame.
	 */
	public ADPFeed() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 300, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		
		JButton btnParse = new JButton("Parse");
		btnParse.addActionListener(new BtnParseActionListener());
		panel.add(btnParse);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		inputfield = new JTextArea();
		scrollPane.setViewportView(inputfield);
	}

	private class BtnParseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				String input = inputfield.getText();
				Pattern regex = Pattern.compile("(?:(.*?)\t([\\w-.]*\\s[\\w-/]*)\\*?,?\\s?(.*?)\t(.*?)\t(.*?)\t(.*?)\t(.*?)\t(.*?)\t(.*))", Pattern.MULTILINE);
				Matcher regexMatcher = regex.matcher(input);
				while (regexMatcher.find()) {
					String name = "";
					String team = "";
					String pos = "";
					double proj = -1;
					double adp = 0;
					for (int i = 1; i <= regexMatcher.groupCount(); i++) {
						String item = regexMatcher.group(i);
						switch (i) {
						case 2:
							name = item;
							break;
						case 3:
							team = item;
							break;
						case 4:
							pos = item;
							break;
						case 5:
							adp = Double.parseDouble(item);
							break;
						default:
							break;
						}
					}
					Service.getInstance().addPlayer(name, team, pos, adp, proj);
				}
			} catch (PatternSyntaxException ex) {
				System.err.println("Error in regex pattern");
			}
		}
	}
}