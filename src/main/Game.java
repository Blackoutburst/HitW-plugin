package main;

/**
 * Game object
 * Manage Wall play field and play area
 * @author Blackoutburst
 */
public class Game {
	int[] area;
	int[] play;
	int[] wall;
	boolean running;
	int clock;
	
	public Game(int[] area, int[] play, int[] wall, boolean running, boolean classic, int clock) {
		super();
		this.area = area;
		this.play = play;
		this.wall = wall;
		this.running = running;
		this.clock = clock;
	}

	public int[] getArea() {
		return area;
	}

	public void setArea(int[] area) {
		this.area = area;
	}

	public int[] getPlay() {
		return play;
	}

	public void setPlay(int[] play) {
		this.play = play;
	}

	public int[] getWall() {
		return wall;
	}

	public void setWall(int[] wall) {
		this.wall = wall;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}
	
}
