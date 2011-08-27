package dao;

import java.util.List;

import model.NflTeam;
import model.Player;
import model.Position;

public interface Dao {
	
	public List<Player> getPlayers();
	public List<NflTeam> getTeams();
	public List<Position> getPositions();
	
	public Player store(Player p);
	public NflTeam store(NflTeam t);
	public Position store(Position p);
	
	public Player update(Player p);
	public NflTeam update(NflTeam t);
	public Position update(Position p);
	
	public Player remove(Player p);
	public NflTeam remove(NflTeam t);
	public Position remove(Position p);
}
