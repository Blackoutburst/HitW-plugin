package main;

import java.util.List;

public class Coop {
	protected List<GamePlayer> players;

	public Coop(List<GamePlayer> players) {
		super();
		this.players = players;
	}

	public List<GamePlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<GamePlayer> players) {
		this.players = players;
	}
	
}
