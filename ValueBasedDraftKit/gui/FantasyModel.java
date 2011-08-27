package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import service.Service;

import model.Player;

public class FantasyModel extends AbstractTableModel {
	private List<Player> players = Service.getInstance().getPlayers();
	private String[] colnames = new String[]{"Name", "Position", "Team", "Projection", "ADP", "VBP", "Taken"};

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return players.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Player p = players.get(row);
		Object r = null;
		if(col == 0) {
			r = p.getName();
		} else if (col == 1) {
			r = p.getPosition();
		} else if (col == 2) {
			r = p.getTeam();
		} else if (col == 3) {
			r = p.getProjection();
		} else if (col == 4) {
			r = p.getAdp();
		} else if (col == 5) {
			r = p.getProjection()-p.getPosition().getVbline();
		} else if (col == 6) {
			r = p.isTaken();
		}
		return r;
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int col) {
		if(col == 6) {
			Service.getInstance().findPlayer(getValueAt(row, 0).toString(), getValueAt(row, 2).toString()).setTaken((Boolean) aValue);
		}
	}
	
	@Override
	public String getColumnName(int col) {
		return colnames[col];
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		Class<?> c = null;
		if(col == 0) {
			c = String.class;
		} else if (col == 1) {
			c = String.class;
		} else if (col == 2) {
			c = String.class;
		} else if (col == 3) {
			c = Double.class;
		} else if (col == 4) {
			c = Double.class;
		} else if (col == 5) {
			c = Double.class;
		} else if (col == 6) {
			c = Boolean.class;
		}
		return c;		
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		boolean edit = false;
		if(col == 6) {
			edit = true;
		}
		return edit;
	}
	
	public void update() {
		players = Service.getInstance().getPlayers();
		super.fireTableDataChanged();
	}
}