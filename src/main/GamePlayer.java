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
	protected int goalTime;
	protected int goalScore;
	protected int wallColor;
	protected int glassColor;
	protected float leverDelay;
	protected boolean inTourney;
	protected String tourneyRole;
	protected boolean inClassicGame;
	protected boolean showTitle;
	
	public GamePlayer(Player player, boolean inGame, int time, int walls, int perfectwalls, Board board, String stage, int missing,
			int misplaced, int choke, int score, boolean leverPulled, String rank, int gameID, int goalTime, int goalScore,
			int wallColor, int glassColor, float leverDelay, boolean inTourney, String tourneyRole, boolean inClassicGame,
			boolean showTitle) {
		this.player = player;
		this.inGame = inGame;
		this.time = time;
		this.walls = walls;
		this.perfectwalls = perfectwalls;
		this.board = board;
		this.stage = stage;
		this.missing = missing;
		this.misplaced = misplaced;
		this.choke = choke;
		this.score = score;
		this.leverPulled = leverPulled;
		this.rank = rank;
		this.gameID = gameID;
		this.goalTime = goalTime;
		this.goalScore = goalScore;
		this.wallColor = wallColor;
		this.glassColor = glassColor;
		this.leverDelay = leverDelay;
		this.inTourney = inTourney;
		this.tourneyRole = tourneyRole;
		this.inClassicGame = inClassicGame;
		this.showTitle = showTitle;
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

	public int getGoalTime() {
		return goalTime;
	}

	public void setGoalTime(int goalTime) {
		this.goalTime = goalTime;
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

	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}
	
}
