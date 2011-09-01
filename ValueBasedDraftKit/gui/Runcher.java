package gui;

import java.util.HashMap;

import model.FantasyLeague;
import model.PosEnum;
import dao.RamDao;
import service.Service;

public class Runcher {
	
	public static void main(String[] args) {
		Service.getInstance().setDao(RamDao.getInstance());
		FantasyLeague.getInstance().setTeams(14);
		HashMap<PosEnum,Integer> noPlayers = new HashMap<PosEnum,Integer>();
		noPlayers.put(PosEnum.QB, 1);
		noPlayers.put(PosEnum.RB, 3);
		noPlayers.put(PosEnum.WR, 2);
		noPlayers.put(PosEnum.TE, 1);
		noPlayers.put(PosEnum.LB, 1);
		noPlayers.put(PosEnum.DB, 1);
		noPlayers.put(PosEnum.DL, 1);
		noPlayers.put(PosEnum.DST, 1);
		noPlayers.put(PosEnum.K, 0);
		FantasyLeague.getInstance().setNoPlayers(noPlayers);
		MainFrame frame = new MainFrame();
		frame.setVisible(true);	
	}
}