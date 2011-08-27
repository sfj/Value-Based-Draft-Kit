package model;

import java.util.HashMap;

public class FantasyLeague {
	
	private int teams;
	private int playersPerTeam;
	private HashMap<PosEnum, Integer> noPlayers;
	
	private static FantasyLeague uniqueInstance;
	
	private FantasyLeague() {
		
	}	
	
	public void setTeams(int teams) {
		this.teams = teams;
	}
	public int getTeams() {
		return teams;
	}
	@Deprecated
	public void setPlayersPerTeam(int playersPerTeam) {
		this.playersPerTeam = playersPerTeam;
	}
	@Deprecated
	public int getPlayersPerTeam() {
		return playersPerTeam;
	}
	public static FantasyLeague getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new FantasyLeague();
		}
		return uniqueInstance;
	}
	public int getNoPlayers(Position position) {
		return noPlayers.get(PosEnum.valueOf(position.toString())) * getTeams();
	}

	public HashMap<PosEnum, Integer> getNoPlayers() {
		return noPlayers;
	}

	public void setNoPlayers(HashMap<PosEnum, Integer> noPlayers) {
		this.noPlayers = noPlayers;
	}

}
