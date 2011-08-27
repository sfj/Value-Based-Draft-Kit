package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import service.Service;

import model.Player;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		model = new FantasyModel();
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		contentPane.add(scrollPane, BorderLayout.CENTER);
			
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnInputProjections = new JButton("Input - Projections");
		btnInputProjections.addActionListener(new BtnInputProjectionsActionListener());
		panel.add(btnInputProjections);
		
		JButton btnInputAdp = new JButton("Input - ADP");
		btnInputAdp.addActionListener(new BtnInputAdpActionListener());
		panel.add(btnInputAdp);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new BtnRefreshActionListener());
		panel.add(btnRefresh);
		
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		
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
}
