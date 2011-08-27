package dao;

import java.util.ArrayList;
import java.util.List;

import model.NflTeam;
import model.Player;
import model.Position;

public class RamDao implements Dao {
	
	private List<Player> players;
	private List<Position> positions;
	private List<NflTeam> teams;
	
	private static Dao uniqueInstance;
	
	public RamDao() {
		players = new ArrayList<Player>();
		positions = new ArrayList<Position>();
		teams = new ArrayList<NflTeam>();
	}
	
	public static Dao getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new RamDao();
		}
		return uniqueInstance;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public List<NflTeam> getTeams() {
		return teams;
	}

	@Override
	public List<Position> getPositions() {
		return positions;
	}

	@Override
	public Player store(Player p) {
		players.add(p);
		return p;
	}

	@Override
	public NflTeam store(NflTeam t) {
		teams.add(t);
		return t;
	}

	@Override
	public Position store(Position p) {
		positions.add(p);
		return p;
	}

	@Override
	public Player update(Player p) {
		return p;
	}

	@Override
	public NflTeam update(NflTeam t) {
		return t;
	}

	@Override
	public Position update(Position p) {
		return p;
	}

	@Override
	public Player remove(Player p) {
		players.remove(p);
		return p;
	}

	@Override
	public NflTeam remove(NflTeam t) {
		teams.remove(t);
		return t;
	}

	@Override
	public Position remove(Position p) {
		positions.remove(p);
		return p;
	}
}
