package gui;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultRowSorter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable.PrintMode;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import service.Service;

import model.FantasyLeague;
import model.Player;
import model.PosEnum;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel infopanel;
	private JLabel lblPlayers;
	private JLabel lblWithAdp;
	private JLabel lblWithProjection;
	private JTable table;
	private FantasyModel model;
	private JComboBox filterPosComboBox;
	private JLabel lblFilterTo;
	private JLabel lblFilterOut;
	private JButton btnLockRounds;
	private JButton btnPrint;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		model = new FantasyModel();
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		DefaultRowSorter<TableModel, String> rowsorter = (DefaultRowSorter<TableModel, String>) table.getRowSorter();
		rowsorter.setRowFilter(new RowFilter<TableModel, String>() {

			@Override
			public boolean include(
					javax.swing.RowFilter.Entry<? extends TableModel, ? extends String> entry) {
				boolean include = false;
				for (int i = entry.getValueCount() - 1; i >= 0; i--) {
					String entryString = entry.getStringValue(i);
					if(filterPosComboBox.getSelectedItem().toString().equals("All") || entryString.equals((filterPosComboBox.getSelectedItem().toString()))){
						include = true;
					}
				}
				return include;
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		contentPane.add(scrollPane, BorderLayout.CENTER);
			
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnInputProjections = new JButton("Input - Projections");
		btnInputProjections.addActionListener(new BtnInputProjectionsActionListener());
		
		btnLockRounds = new JButton("Lock rounds");
		btnLockRounds.addActionListener(new BtnLockRoundsActionListener());
		
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new BtnPrintActionListener());
		panel.add(btnPrint);
		panel.add(btnLockRounds);
		panel.add(btnInputProjections);
		
		JButton btnInputAdp = new JButton("Input - ADP");
		btnInputAdp.addActionListener(new BtnInputAdpActionListener());
		panel.add(btnInputAdp);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new BtnRefreshActionListener());
		panel.add(btnRefresh);
		
		lblFilterTo = new JLabel("Filter to:");
		panel.add(lblFilterTo);
		
		filterPosComboBox = new JComboBox();
		filterPosComboBox.addActionListener(new ComboBoxActionListener());
		String[] values = new String[PosEnum.values().length + 1];
		values[0] = "All";
		for(int i = 1; i < PosEnum.values().length; i++) {
			values[i] = PosEnum.values()[i - 1].toString();
		}		
		
		filterPosComboBox.setModel(new DefaultComboBoxModel(values));
		panel.add(filterPosComboBox);
		
		lblFilterOut = new JLabel("Filter out:");
		panel.add(lblFilterOut);
		
		JComboBox comboBox_1 = new JComboBox();
		panel.add(comboBox_1);
		
		infopanel = new JPanel();
		contentPane.add(infopanel, BorderLayout.SOUTH);
		
		lblPlayers = new JLabel("Players:");
		infopanel.add(lblPlayers);
		
		lblWithAdp = new JLabel("with ADP:");
		infopanel.add(lblWithAdp);
		
		lblWithProjection = new JLabel("with projection:");
		infopanel.add(lblWithProjection);
	}

	private class BtnInputProjectionsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JFrame inputproj = new InputFeed();
			inputproj.setVisible(true);
		}
	}
	private class BtnInputAdpActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JFrame adpfeed = new ADPFeed();
			adpfeed.setVisible(true);
		}
	}
	private class BtnRefreshActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			update();
		}
	}
	private class ComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			update();
		}
	}
	private class BtnLockRoundsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			for(int i = 0; i < model.getRowCount(); i++) {
				int round = 1 + i / FantasyLeague.getInstance().getTeams();
				model.setValueAt(round, table.getRowSorter().convertRowIndexToModel(i), 7);
			}
			update();
		}
	}
	private class BtnPrintActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				table.print(PrintMode.FIT_WIDTH);
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}
	private void update() {
		model.update();
		List<Player> players = Service.getInstance().getPlayers();
		lblPlayers.setText("Players: " + players.size());
		int count = 0;
		for(Player p : players) {
			if(p.getAdp() > -1) {
				count++;
			}
		}
		lblWithAdp.setText(" with ADP:  " + count);
		count = 0;
		for(Player p : players) {
			if(p.getProjection() > -1) {
				count++;
			}
		}
		lblWithProjection.setText(" with Proj: " + count);
	}
}
