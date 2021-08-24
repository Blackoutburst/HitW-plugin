package core;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import main.Main;
import utils.Utils;

public class HPlayer {
	protected Player player;
	protected short wallColor;
	protected short glassColor;
	protected float leverDelay;
	protected float memTime;
	protected float brushLag;
	protected boolean fly;
	protected boolean title;
	protected boolean rightSided;
	protected boolean oldAnimation;
	protected boolean blind;
	protected boolean destroy;
	protected boolean autoLeave;
	protected boolean inGame;
	protected boolean inParty;
	protected boolean partyLeader;
	protected Board board;
	protected String rank;
	protected List<HPlayer> party;
	protected int wall;
	protected int perfectWall;
	protected int missed;
	protected int misplaced;
	protected int choke;
	protected int scoreLimit;
	protected int time;
	protected int score;
	protected Instant wallBegin;
	protected Instant wallEnd;
	protected List<Float> wallTime;
	protected boolean partyInvite;
	protected boolean duelInvite;
	protected HPlayer opponent;
	protected String duelType;
	protected boolean inDuel;
	protected PositionSongPlayer rsp;
	protected Song song;
	protected String songName;
	protected boolean usePlay;
	
	protected int scoreQualification = 0;
	protected int scoreFinals = 0;
	protected int scoreWideQualification = 0;
	protected int scoreLobby = 0;
	
	public HPlayer(Player player, short wallColor, short glassColor, float leverDelay, float memTime, float brushLag,
			boolean fly, boolean title, boolean rightSided, boolean oldAnimation, boolean blind, boolean destroy, boolean autoLeave, 
			Board board, String rank, String songName, int scoreQualification, int scoreFinals, int scoreWideQualification, int scoreLobby) {
		this.player = player;
		this.wallColor = wallColor;
		this.glassColor = glassColor;
		this.leverDelay = leverDelay;
		this.memTime = memTime;
		this.brushLag = brushLag;
		this.fly = fly;
		this.title = title;
		this.rightSided = rightSided;
		this.oldAnimation = oldAnimation;
		this.blind = blind;
		this.destroy = destroy;
		this.autoLeave = autoLeave;
		this.inGame = false;
		this.inParty = false;
		this.partyLeader = false;
		this.board = board;
		this.rank = rank;
		this.party = new ArrayList<HPlayer>();
		this.wall = 0;
		this.perfectWall = 0;
		this.missed = 0;
		this.misplaced = 0;
		this.scoreLimit = 0;
		this.time = 0;
		this.score = 0;
		this.wallBegin = null;
		this.wallEnd = null;
		this.wallTime = new ArrayList<Float>();
		this.partyInvite = false;
		this.duelInvite = false;
		this.opponent = null;
		this.duelType = "none";
		this.inDuel = false;
		this.songName = songName;
		this.usePlay = true;
		this.scoreQualification = scoreQualification;
		this.scoreFinals = scoreFinals;
		this.scoreWideQualification = scoreWideQualification;
		this.scoreLobby = scoreLobby;
	}

	public static HPlayer getHPlayer(Player p) {
		for (HPlayer h : Main.hPlayers) {
			if (h.player == p) {
				return (h);
			}
		}
		return (null);
	}
	
	public static void updatePlayerData(HPlayer p) {
		try {
			File f = new File("./plugins/HitW/player data/"+p.player.getUniqueId().toString().replace("-", "")+".yml");
			
			if (!f.exists()) {
				f.createNewFile();
			}
				YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
				config.set("colors", null);
				config.set("colors.wall", p.wallColor);
				config.set("colors.glass", p.glassColor);
				config.set("leverDelay", p.leverDelay);
				config.set("memtime", p.memTime);
				config.set("brushLag", p.brushLag);
				config.set("fly", p.fly);
				config.set("title", p.title);
				config.set("rightSided", p.rightSided);
				config.set("oldAnimation", p.oldAnimation);
				config.set("blind", p.blind);
				config.set("destroy", p.destroy);
				config.set("autoLeave", p.autoLeave);
				config.set("songName", p.songName);
				config.set("score", null);
				config.set("score.Q", p.scoreQualification);
				config.set("score.F", p.scoreFinals);
				config.set("score.WQ", p.scoreWideQualification);
				config.set("score.L", p.scoreLobby);
				config.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void restorePlayerData() {
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File("./plugins/HitW/player data/"+this.player.getUniqueId().toString().replace("-", "")+".yml"));
		this.wallColor = (short) playerData.getInt("colors.wall");
		this.glassColor = (short) playerData.getInt("colors.glass");
		this.leverDelay = (float) playerData.getDouble("leverDelay");
		this.memTime = (float) playerData.getDouble("memtime");
		this.brushLag = (float) playerData.getDouble("brushLag");
		this.fly = playerData.getBoolean("fly");
		this.title = playerData.getBoolean("title");
		this.rightSided = playerData.getBoolean("rightSided");
		this.oldAnimation = playerData.getBoolean("oldAnimation");
		this.blind = playerData.getBoolean("blind");
		this.destroy = playerData.getBoolean("destroy");
		this.autoLeave = playerData.getBoolean("autoLeave");
		this.songName = playerData.getString("songName");
		this.scoreQualification = playerData.getInt("score.Q");
		this.scoreFinals = playerData.getInt("score.F");
		this.scoreWideQualification = playerData.getInt("score.WQ");
		this.scoreLobby = playerData.getInt("score.L");
	}

	public String getDisplayName() {
		return player.getDisplayName();
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public short getWallColor() {
		return wallColor;
	}

	public void setWallColor(short wallColor) {
		this.wallColor = wallColor;
	}

	public short getGlassColor() {
		return glassColor;
	}

	public void setGlassColor(short glassColor) {
		this.glassColor = glassColor;
	}

	public float getLeverDelay() {
		return leverDelay;
	}

	public void setLeverDelay(float leverDelay) {
		this.leverDelay = leverDelay;
	}

	public float getMemTime() {
		return memTime;
	}

	public void setMemTime(float memTime) {
		this.memTime = memTime;
	}

	public float getBrushLag() {
		return brushLag;
	}

	public void setBrushLag(float brushLag) {
		this.brushLag = brushLag;
	}

	public boolean isFly() {
		return fly;
	}

	public void setFly(boolean fly) {
		this.fly = fly;
	}

	public boolean isTitle() {
		return title;
	}

	public void setTitle(boolean title) {
		this.title = title;
	}

	public boolean isRightSided() {
		return rightSided;
	}

	public void setRightSided(boolean rightSided) {
		this.rightSided = rightSided;
	}

	public boolean isOldAnimation() {
		return oldAnimation;
	}

	public void setOldAnimation(boolean oldAnimation) {
		this.oldAnimation = oldAnimation;
	}

	public boolean isBlind() {
		return blind;
	}

	public void setBlind(boolean blind) {
		this.blind = blind;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame, HGame game) {
		this.inGame = inGame;
		
		if (!inGame) {
			if (rsp != null) {
				rsp.setPlaying(false);
				rsp.destroy();
			}
			Utils.giveConfigItem(this.player);
		} else {
			if (new File("./plugins/HitW/songs/"+songName+".nbs").exists()) {
				Location loc = new Location(Bukkit.getWorld("world"), (game.getWall().x0 + game.getWall().x1) / 2, (game.getWall().y0 + game.getWall().y1) / 2, (game.getWall().z0 + game.getWall().z1) / 2);
				
				
				song = NBSDecoder.parse(new File("./plugins/HitW/songs/"+songName+".nbs"));
				rsp = new PositionSongPlayer(song);
				rsp.setTargetLocation(loc);
				rsp.setDistance(128);
				rsp.setRepeatMode(RepeatMode.ONE);
				rsp.addPlayer(this.getPlayer());
				rsp.setPlaying(true);
			}
		}
	}

	public boolean isInParty() {
		return inParty;
	}

	public void setInParty(boolean inParty) {
		this.inParty = inParty;
	}

	public boolean isPartyLeader() {
		return partyLeader;
	}

	public void setPartyLeader(boolean partyLeader) {
		this.partyLeader = partyLeader;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public List<HPlayer> getParty() {
		return party;
	}

	public void setParty(List<HPlayer> party) {
		this.party = party;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	public int getWall() {
		return wall;
	}

	public void setWall(int wall) {
		this.wall = wall;
	}

	public int getPerfectWall() {
		return perfectWall;
	}

	public void setPerfectWall(int perfectWall) {
		this.perfectWall = perfectWall;
	}

	public int getMissed() {
		return missed;
	}

	public void setMissed(int missed) {
		this.missed = missed;
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

	public int getScoreLimit() {
		return scoreLimit;
	}

	public void setScoreLimit(int scoreLimit) {
		this.scoreLimit = scoreLimit;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Instant getWallBegin() {
		return wallBegin;
	}

	public void setWallBegin(Instant wallBegin) {
		this.wallBegin = wallBegin;
	}

	public Instant getWallEnd() {
		return wallEnd;
	}

	public void setWallEnd(Instant wallEnd) {
		this.wallEnd = wallEnd;
	}

	public List<Float> getWallTime() {
		return wallTime;
	}

	public void setWallTime(List<Float> wallTime) {
		this.wallTime = wallTime;
	}

	public boolean isPartyInvite() {
		return partyInvite;
	}

	public void setPartyInvite(boolean partyInvite) {
		this.partyInvite = partyInvite;
	}

	public boolean isDuelInvite() {
		return duelInvite;
	}

	public void setDuelInvite(boolean duelInvite) {
		this.duelInvite = duelInvite;
	}

	public boolean isAutoLeave() {
		return autoLeave;
	}

	public void setAutoLeave(boolean autoLeave) {
		this.autoLeave = autoLeave;
	}

	public HPlayer getOpponent() {
		return opponent;
	}

	public void setOpponent(HPlayer opponent) {
		this.opponent = opponent;
	}

	public String getDuelType() {
		return duelType;
	}

	public void setDuelType(String duelType) {
		this.duelType = duelType;
	}

	public boolean isInDuel() {
		return inDuel;
	}

	public void setInDuel(boolean inDuel) {
		this.inDuel = inDuel;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public boolean canUsePlay() {
		return usePlay;
	}

	public void setUsePlay(boolean usePlay) {
		this.usePlay = usePlay;
	}

	public int getScoreFinals() {
		return scoreFinals;
	}

	public void setScoreFinals(int scoreFinals) {
		this.scoreFinals = scoreFinals;
	}

	public int getScoreQualification() {
		return scoreQualification;
	}

	public void setScoreQualification(int scoreQualification) {
		this.scoreQualification = scoreQualification;
	}

	public int getScoreWideQualification() {
		return scoreWideQualification;
	}

	public void setScoreWideQualification(int scoreWideQualification) {
		this.scoreWideQualification = scoreWideQualification;
	}

	public int getScoreLobby() {
		return scoreLobby;
	}

	public void setScoreLobby(int scoreLobby) {
		this.scoreLobby = scoreLobby;
	}
	
	
}
