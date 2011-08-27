package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Position {
	private String name;
	private LinkedList<Player> players;
	
	public Position(String name) {
		setName(name);
		players = new LinkedList<Player>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public double getVbline() {
		FantasyLeague fl = FantasyLeague.getInstance();
		int noPlayers = fl.getNoPlayers(this);
		double vbline = 0;
		if(noPlayers + 3 < players.size() && noPlayers - 2 > 0) {
			double sum = 0;
			for(int i = noPlayers - 2; i < noPlayers + 3; i++) {
					sum += players.get(i).getProjection();
			}
			vbline = sum / 5;
		} else {
			if(players.size() > 0) {
				vbline = players.getLast().getProjection();
			}
		}
		return vbline;
	}

	void addPlayerUD(Player player) {
		players.add(player);
		Collections.sort(players, new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				return (int) (p2.getProjection() - p1.getProjection());
			}			
		});
	}
	void removePlayerUD(Player player) {
		players.remove(player);		
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
