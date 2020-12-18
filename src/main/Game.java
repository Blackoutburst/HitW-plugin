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
	int[] holes;
	boolean running;
	int clock;
	String type;
	int stageTime;
	int memoryTime;
	
	public Game(int[] area, int[] play, int[] wall, int[] holes , String type, int stageTime, int memoryTime) {
		super();
		this.area = area;
		this.play = play;
		this.wall = wall;
		this.holes = holes;
		this.type = type;
		this.stageTime = stageTime;
		this.memoryTime = memoryTime;
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

	public int[] getHoles() {
		return holes;
	}
	
	public void setHoles(int[] holes) {
		this.holes = holes;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStageTime() {
		return stageTime;
	}

	public void setStageTime(int stageTime) {
		this.stageTime = stageTime;
	}

	public int getMemoryTime() {
		return memoryTime;
	}

	public void setMemoryTime(int memoryTime) {
		this.memoryTime = memoryTime;
	}
	
}
