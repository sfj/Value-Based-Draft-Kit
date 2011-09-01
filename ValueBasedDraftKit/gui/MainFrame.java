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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel infopanel;
	private JLabel lblPlayers;
	private JLabel lblWithAdp;
	private JLabel lblWithProjection;
	private JTable table;
	private FantasyModel model;
	private JComboBox filterPosComboBox;
	private JLabel lblSearch;
	private JMenuBar menuBar;
	private JMenu mnTable;
	private JMenuItem mntmPrint;
	private JMenuItem mntmLockRounds;
	private JMenu mnInput;
	private JMenuItem mntmProjections;
	private JMenuItem mntmAdp;
	private JLabel lblFilterTo;
	private JToolBar toolBar;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private Component horizontalStrut_4;
	private JTextField txtTxfsearch;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 494);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnTable = new JMenu("Table");
		menuBar.add(mnTable);
		
		mntmPrint = new JMenuItem("Print");
		mntmPrint.addActionListener(new MntmPrintActionListener());
		mnTable.add(mntmPrint);
		
		mntmLockRounds = new JMenuItem("Lock rounds");
		mntmLockRounds.addActionListener(new MntmLockRoundsActionListener());
		mnTable.add(mntmLockRounds);
		
		mnInput = new JMenu("Input");
		menuBar.add(mnInput);
		
		mntmProjections = new JMenuItem("Projections");
		mntmProjections.addActionListener(new MntmProjectionsActionListener());
		mnInput.add(mntmProjections);
		
		mntmAdp = new JMenuItem("ADP");
		mntmAdp.addActionListener(new MntmAdpActionListener());
		mnInput.add(mntmAdp);
		
		toolBar = new JToolBar();
		menuBar.add(toolBar);
		
		lblFilterTo = new JLabel("Filter to:");
		toolBar.add(lblFilterTo);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut);
		
		filterPosComboBox = new JComboBox();
		filterPosComboBox.setMaximumSize(new Dimension(200, 20));
		toolBar.add(filterPosComboBox);
		filterPosComboBox.addActionListener(new ComboBoxActionListener());
		
		String[] values = new String[PosEnum.values().length + 1];
		values[0] = "All";
		for(int i = 0; i < PosEnum.values().length; i++) {
			values[i+1] = PosEnum.values()[i].toString();
		} 
		
		filterPosComboBox.setModel(new DefaultComboBoxModel(values));
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_1);
		
		lblSearch = new JLabel("Search:");
		toolBar.add(lblSearch);
		
		horizontalStrut_2 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_2);
		
		txtTxfsearch = new JTextField();
		txtTxfsearch.addKeyListener(new TxtTxfsearchKeyListener());
		txtTxfsearch.setPreferredSize(new Dimension(170, 20));
		txtTxfsearch.setMinimumSize(new Dimension(150, 20));
		txtTxfsearch.setMaximumSize(new Dimension(200, 20));
		toolBar.add(txtTxfsearch);
		txtTxfsearch.setColumns(5);
		
		horizontalStrut_3 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_3);
		
		JButton btnRefresh = new JButton("Refresh");
		toolBar.add(btnRefresh);
		
		horizontalStrut_4 = Box.createHorizontalStrut(20);
		toolBar.add(horizontalStrut_4);
		btnRefresh.addActionListener(new BtnRefreshActionListener());
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		model = new FantasyModel();
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		@SuppressWarnings("unchecked")
		DefaultRowSorter<TableModel, String> rowsorter = (DefaultRowSorter<TableModel, String>) table.getRowSorter();
		rowsorter.setRowFilter(new RowFilter<TableModel, String>() {

			@Override
			public boolean include(
					javax.swing.RowFilter.Entry<? extends TableModel, ? extends String> entry) {
				boolean include = false;
				for (int i = entry.getValueCount() - 1; i >= 0; i--) {
					String entryString = entry.getStringValue(i);
					if(filterPosComboBox.getSelectedItem().toString().equals("All") || entryString.equals((filterPosComboBox.getSelectedItem().toString()))){
						if(txtTxfsearch.getText().length() > 0) {
							if(entryString.toLowerCase().indexOf(txtTxfsearch.getText().toLowerCase()) > -1) {
								include = true;
							}
						} else {
							include = true;
						}
					}
				}
				return include;
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		infopanel = new JPanel();
		contentPane.add(infopanel, BorderLayout.SOUTH);
		
		lblPlayers = new JLabel("Players:");
		infopanel.add(lblPlayers);
		
		lblWithAdp = new JLabel("with ADP:");
		infopanel.add(lblWithAdp);
		
		lblWithProjection = new JLabel("with projection:");
		infopanel.add(lblWithProjection);
		
		update();
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
	private class MntmPrintActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				table.print(PrintMode.FIT_WIDTH);
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}
	private class MntmLockRoundsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			for(int i = 0; i < model.getRowCount(); i++) {
				int round = 1 + i / FantasyLeague.getInstance().getTeams();
				model.setValueAt(round, table.getRowSorter().convertRowIndexToModel(i), 7);
			}
			update();
		}
	}
	private class MntmProjectionsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFrame inputproj = new InputFeed();
			inputproj.setVisible(true);			
		}
	}
	private class MntmAdpActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFrame adpfeed = new ADPFeed();
			adpfeed.setVisible(true);			
		}
	}
	private class TxtTxfsearchKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent arg0) {
			update();
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
