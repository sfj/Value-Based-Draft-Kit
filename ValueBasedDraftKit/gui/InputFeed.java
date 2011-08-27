package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;

import service.Service;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JScrollPane;

public class InputFeed extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextArea textArea;

	/**
	 * Create the frame.
	 */
	public InputFeed() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(300, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);

		JButton btnParse = new JButton("Parse");
		btnParse.addActionListener(new BtnParseActionListener());
		panel.add(btnParse);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setText("<insert text here>");
	}

	private class BtnParseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String input = textArea.getText();
			try {
				Pattern p1 = Pattern
						.compile(
								"(?:(\\d*?)\t+?([a-zA-Z-.'\\d]+\\s[a-zA-Z-/]+)\\*?,?\\s?(\\w{2,3})\\s(\\w{1,2}/?\\w{0,2})\t*?,?\\s?\\w?\\w?\t*?\t*?(?:(?:\\s(?:\\[.*?\\]\\s)|(?:\\s\\s\\w))*\t+?[a-zA-Z-]+\t+?(?:\t+?(\\d+?)/(\\d+?)))?\t+?(\\d*?)\t+?(\\d*?)\t+?(\\d*?)\t+?(\\d*?)\t+?(\\d*?)\t+?(\\d*?)\t+?(\\d*?)\t+?(\\d*?)\t+?(\\d*?)\t+?(\\d*\\.?\\d*))",
								Pattern.MULTILINE);
				Matcher m1 = p1.matcher(input);
					while (m1.find()) {
						String name = "";
						String team = "";
						String pos = "";
						double proj = 0;
						double adp = -1;
						for (int i = 1; i <= m1.groupCount(); i++) {
							String item = m1.group(i);
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
							case 16:
								proj = Double.parseDouble(item);
								break;
							}
						}
						Service.getInstance().addPlayer(name, team, pos, adp, proj);
					}					
			} catch (PatternSyntaxException ex) {
				// Syntax error in the regular expression
			} catch (NumberFormatException ex) {
				
			}
		}
	}
}