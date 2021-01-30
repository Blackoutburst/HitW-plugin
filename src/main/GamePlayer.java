package main;

import org.bukkit.entity.Player;

import utils.Board;

/**
 * GamePlayer object
 * add more value for a player
 * @author Blackoutburst
 */
public class GamePlayer {

	protected Player player;
	protected boolean inGame;
	protected int time;
	protected int walls;
	protected int perfectwalls;
	protected Board board;
	protected String stage;
	protected int missing;
	protected int misplaced;
	protected int choke;
	protected int score;
	protected boolean leverPulled;
	protected String rank;
	protected int gameID;
	protected int goalScore;
	protected int wallColor;
	protected int glassColor;
	protected float leverDelay;
	protected boolean inTourney;
	protected String tourneyRole;
	protected boolean inClassicGame;
	protected boolean showTitle;
	protected boolean inBlindGame;
	protected float memtime;
	protected boolean inCoop;
	protected boolean coopLeader;
	protected Coop coop;
	boolean easyMode;
	
	public GamePlayer(Player player, Board board, String rank, int wallColor, int glassColor, float leverDelay, 
			boolean showTitle, float memtime) {
		this.player = player;
		this.inGame = false;
		this.time = 0;
		this.walls = 0;
		this.perfectwalls = 0;
		this.board = board;
		this.stage = "none";
		this.missing = 0;
		this.misplaced = 0;
		this.choke = 0;
		this.score = 0;
		this.leverPulled = false;
		this.rank = rank;
		this.gameID = 0;
		this.goalScore = 0;
		this.wallColor = wallColor;
		this.glassColor = glassColor;
		this.leverDelay = leverDelay;
		this.inTourney = false;
		this.tourneyRole = "none";
		this.inClassicGame = false;
		this.showTitle = showTitle;
		this.inBlindGame = false;
		this.memtime = memtime;
		this.inCoop = false;
		this.coopLeader = false;
		this.coop = null;
		this.easyMode = false;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getWalls() {
		return walls;
	}

	public void setWalls(int walls) {
		this.walls = walls;
	}

	public int getPerfectwalls() {
		return perfectwalls;
	}

	public void setPerfectwalls(int perfectwalls) {
		this.perfectwalls = perfectwalls;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public int getMissing() {
		return missing;
	}

	public void setMissing(int missing) {
		this.missing = missing;
	}

	public int getMisplaced() {
		return misplaced;
	}

	public void setMisplaced(int misplaced) {
		this.misplaced = misplaced;
	}

	public int getChoke() {
		return choke;
	}

	public void setChoke(int choke) {
		this.choke = choke;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isLeverPulled() {
		return leverPulled;
	}

	public void setLeverPulled(boolean leverPulled) {
		this.leverPulled = leverPulled;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getGoalScore() {
		return goalScore;
	}

	public void setGoalScore(int goalScore) {
		this.goalScore = goalScore;
	}

	public int getWallColor() {
		return wallColor;
	}

	public void setWallColor(int wallColor) {
		this.wallColor = wallColor;
	}

	public int getGlassColor() {
		return glassColor;
	}

	public void setGlassColor(int glassColor) {
		this.glassColor = glassColor;
	}

	public float getLeverDelay() {
		return leverDelay;
	}

	public void setLeverDelay(float leverDelay) {
		this.leverDelay = leverDelay;
	}

	public boolean isInTourney() {
		return inTourney;
	}

	public void setInTourney(boolean inTourney) {
		this.inTourney = inTourney;
	}

	public String getTourneyRole() {
		return tourneyRole;
	}

	public void setTourneyRole(String tourneyRole) {
		this.tourneyRole = tourneyRole;
	}

	public boolean isInClassicGame() {
		return inClassicGame;
	}

	public void setInClassicGame(boolean inClassicGame) {
		this.inClassicGame = inClassicGame;
	}

	public boolean showTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}
	
	public boolean isInBlindGame() {
		return inBlindGame;
	}

	public void setInBlindGame(boolean inBlindGame) {
		this.inBlindGame = inBlindGame;
	}

	public float getMemtime() {
		return memtime;
	}

	public void setMemtime(float memtime) {
		this.memtime = memtime;
	}

	public boolean isInCoop() {
		return inCoop;
	}

	public void setInCoop(boolean inCoop) {
		this.inCoop = inCoop;
	}

	public boolean isCoopLeader() {
		return coopLeader;
	}

	public void setCoopLeader(boolean coopLeader) {
		this.coopLeader = coopLeader;
	}

	public Coop getCoop() {
		return coop;
	}

	public void setCoop(Coop coop) {
		this.coop = coop;
	}

	public boolean isEasyMode() {
		return easyMode;
	}

	public void setEasyMode(boolean easyMode) {
		this.easyMode = easyMode;
	}
	
}
