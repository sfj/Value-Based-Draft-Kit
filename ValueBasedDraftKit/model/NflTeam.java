package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class NflTeam {
	private String name;
	@Deprecated
	private String acronym;
	private LinkedList<Player> players;
	
	public NflTeam(String team) {
		setName(team);
		players = new LinkedList<Player>();
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	@Deprecated
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	@Deprecated
	public String getAcronym() {
		return acronym;
	}
	@Override
	public String toString() {
		return name;
	}
	void addPlayerUD(Player player) {
		players.add(player);
		Collections.sort(players, new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				return (int) (p1.getProjection() - p2.getProjection());
			}			
		});
	}
	void removePlayerUD(Player player) {
		players.remove(player);		
	}
}