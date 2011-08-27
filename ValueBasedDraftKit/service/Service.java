package service;

import java.util.ArrayList;
import java.util.List;

import dao.Dao;

import model.NflTeam;
import model.Player;
import model.Position;

public class Service {
	
	private static Service uniqueInstance;
	
	private Dao dao;
	
	public Service() {
		
	}
	
	public static Service getInstance() {
		if(uniqueInstance == null) {
			uniqueInstance = new Service();
		}
		return uniqueInstance;
	}
	
	public Player addPlayer(String name, String team, String pos, double adp, double proj) {
		if(pos.equals("D/ST")) {
			pos = "DST";
		} else if(pos.equals("S") || pos.equals("CB")){
			pos = "DB";
		} else if(pos.equals("DT") || pos.equals("DE")){
			pos = "DL";
		}
		Player p = findPlayer(name, team);
		NflTeam t = findTeam(team);
		Position po = findPosition(pos);
		if(t == null) {
			t = new NflTeam(team);
			dao.store(t);
		}
		if(po == null) {
			po = new Position(pos);
			dao.store(po);
		}
		if(p == null) {
			p = new Player(name, po, t);
			dao.store(p);
		}
		if(adp > -1) {
			p.setAdp(adp);
			dao.update(p);
		}
		if(proj > -1) {
			p.setProjection(proj);
			dao.update(p);
		}
		return p;
	}

	private Position findPosition(String pos) {
		boolean found = false;
		int i = 0;
		Position p = null;
		while(!found && i<dao.getPositions().size()){
			p = dao.getPositions().get(i);
			if (p.getName().equals(pos))
				found = true;
			else
				i++;		
		}
		if (found)
			return p;
		else
			return null;
	}

	private NflTeam findTeam(String team) {
		boolean found = false;
		int i = 0;
		NflTeam n = null;
		while(!found && i<dao.getTeams().size()){
			n = dao.getTeams().get(i);
			if (n.getName().equals(team))
				found = true;
			else
				i++;		
		}
		if (found)
			return n;
		else
			return null;
	}

	public Player findPlayer(String name, String team) {
		boolean found = false;
		int i = 0;
		Player p = null;
		while(!found && i<dao.getPlayers().size()){
			p = dao.getPlayers().get(i);
			if (p.getName().equals(name) && p.getTeam().getName().equals(team)) {
					found = true;
			} else {
				i++;
			}
		}
		if (found)
			return p;
		else
			return null;
	}
	
	public void printPlayers() {
		for(Player p : dao.getPlayers()) {
			System.out.println(p);
		}
	}

	public List<Player> getPlayers() {
		return new ArrayList<Player>(dao.getPlayers());
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public Dao getDao() {
		return dao;
	}

}
