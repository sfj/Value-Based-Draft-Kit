package model;

public class Player {
	private String name;
	private double projection;
	private boolean taken;
	private Position position;
	private NflTeam team;
	private double adp;
	private int projRound;
	
	public Player(String name, double projection, Position position, NflTeam team, double adp) {
		this(name, position, team);
		this.setProjection(projection);
		this.setAdp(adp);
	}

	public Player(String name, Position po, NflTeam t) {
		this.setName(name);
		this.setPosition(po);
		this.setTeam(t);
		this.setProjection(-1);
		this.setAdp(-1);
		this.setTaken(false);
		this.setProjRound(0);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setProjection(double projection) {
		this.projection = projection;
	}

	public double getProjection() {
		return projection;
	}

	public void setPosition(Position position) {
		if(position != null) {
			this.position = position;
			position.addPlayerUD(this);
		} else {
			this.position.removePlayerUD(this);
			this.position = position;
		}
	}

	public Position getPosition() {
		return position;
	}

	public void setTeam(NflTeam team) {
		if(team == null) {
			this.team.removePlayerUD(this);
		} else {
			team.addPlayerUD(this);
		}
		setTeamUD(team);
	}

	void setTeamUD(NflTeam team) {
		this.team = team;		
	}

	public NflTeam getTeam() {
		return team;
	}

	public void setAdp(double adp) {
		this.adp = adp;
	}

	public double getAdp() {
		return adp;
	}
	
	@Deprecated
	@Override
	public String toString() {
		String tabs = "\t";
		if(name.length() < 16) {
			tabs = "\t\t";
		}
		return "["+ position +"]\t" + name + tabs + team + "\t" + adp + "\t" + projection;
	}

	public boolean isTaken() {
		return taken;
	}
	
	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	public int getProjRound() {
		return projRound;
	}
	
	public void setProjRound(int round) {
		this.projRound = round;
	}
}
