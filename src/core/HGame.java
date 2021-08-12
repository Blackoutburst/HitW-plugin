package core;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import main.Main;
import utils.ScoreboardManager;
import utils.Utils;

public class HGame {
	protected Direction direction;
	protected HPlayer owner;
	protected Area wall;
	protected Area area;
	protected Area playfield;
	protected String name;
	protected int[] holes;
	private boolean incrementingHoles;
	private boolean leverBusy;
	private boolean wallPulled;
	private String type;
	
	public HGame(Direction direction, Area wall, Area area, Area playfield, String name, int[] holes) {
		this.direction = direction;
		this.owner = null;
		this.wall = wall;
		this.area = area;
		this.playfield = playfield;
		this.name = name;
		this.holes = holes;
		this.incrementingHoles = false;
		this.leverBusy = false;
		this.wallPulled = false;
		this.type = "";
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public HPlayer getOwner() {
		return owner;
	}

	private void endStatsMessage(HPlayer p) {
		ScoreboardManager.updateScoreboard(p);
		Collections.sort(p.getWallTime());
		float average = 0;
		
		for (Float f : p.getWallTime()) {
			average += f;
		}
		average = average / p.getWallTime().size();
		
		p.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		p.getPlayer().sendMessage(Utils.centerText("§6§lGame summary"));
		p.getPlayer().sendMessage("§6Stage: §f"+p.board.get(11).replace("Stage: §a", ""));
		p.getPlayer().sendMessage("§6Score: §f"+p.score);
		
		if (p.time > 0) {
			int minutes = p.time / 60;
			int seconds = (p.time) % 60;
			String str = String.format("%d:%02d", minutes, seconds);
			p.getPlayer().sendMessage("§6Time: §f"+str);
		}
		p.getPlayer().sendMessage(Utils.centerText("§b§lWall completion time"));
		p.getPlayer().sendMessage("§bFastest wall: §f"+Utils.ROUND.format(p.getWallTime().get(0))+"s");
		p.getPlayer().sendMessage("§bSlowest wall: §f"+Utils.ROUND.format(p.getWallTime().get(p.getWallTime().size()-1))+"s");
		p.getPlayer().sendMessage("§bAverage: §f"+Utils.ROUND.format(average)+"s");
		p.getPlayer().sendMessage(Utils.centerText("§a§lWalls"));
		p.getPlayer().sendMessage("§aNumber of walls: §f"+p.wall);
		p.getPlayer().sendMessage("§aPerfect walls: §f"+p.perfectWall);
		p.getPlayer().sendMessage(Utils.centerText("§4§lMistakes"));
		p.getPlayer().sendMessage("§4Aim chokes: §f"+p.choke);
		p.getPlayer().sendMessage("§4Misplaced blocks: §f"+p.misplaced);
		p.getPlayer().sendMessage("§4Missed blocks: §f"+p.missed);
		p.getPlayer().sendMessage("§a§l§m---------------------------------------------");
	}
	
	private void printEndStats() {
		if (this.owner.inParty)
			for (HPlayer hp : this.owner.party)
				endStatsMessage(hp);
		else
			endStatsMessage(this.owner);
	}
	
	private void duelResult(HPlayer p, HPlayer owner) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
		    @Override
		    public void run(){
				String player1 = owner.getPlayer().getDisplayName() + "§r: " + owner.getScore();
				String player2 = owner.getOpponent().getDisplayName() + "§r: " + owner.getOpponent().getScore();
				
				if (owner.duelType.equals("Qualification"))
					Main.QDuelBusy = false;
				
				if (owner.duelType.equals("Finals"))
					Main.FDuelBusy = false;
				
				owner.duelType = "none";
				
				ScoreboardManager.updateScoreboard(p);
				p.getPlayer().sendMessage("§a§l§m---------------------------------------------");
				p.getPlayer().sendMessage(Utils.centerText("§4§lDuel"));
				p.getPlayer().sendMessage("");
				p.getPlayer().sendMessage(Utils.centerText("§6#1 " + (owner.getScore() > owner.getOpponent().getScore() ? player1 : player2)));
				p.getPlayer().sendMessage(Utils.centerText("§f#2 " + (owner.getScore() < owner.getOpponent().getScore() ? player1 : player2)));
				p.getPlayer().sendMessage("§a§l§m---------------------------------------------");
		    }
		}, (5L));
	}

	private void teleportToSpawn(HPlayer p) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
			@Override
			public void run(){
				p.inDuel = false;
				p.restorePlayerData();
				ScoreboardManager.setDefaultScoreboard(p.getBoard());
				p.getBoard().setTitle(p.getDisplayName());
				p.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1045.5f, 0, 0));
			}
		}, (100L));
	}
	
	private void endDuel() {
		if (this.owner.inParty) {
			for (HPlayer hp : this.owner.party) {
				duelResult(hp, this.owner);
				teleportToSpawn(hp);
			}
		} else {
			duelResult(this.owner, this.owner);
			teleportToSpawn(this.owner);
		}
	}
	
	public void setOwner(HPlayer owner) {
		HGame game = this;
		HPlayer player = this.owner;
		if (owner == null) {
			this.type = "none";
			WallManager.pullWall(this.owner, this, true, false);
			GameUpdater.stopGame(this.owner, this);
			
			if (this.owner.getWallTime().size() > 0 && !this.owner.inDuel)
				printEndStats();
			
			if (this.owner.inDuel)
				endDuel();
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
				@Override
				public void run(){
					WallManager.resetWall(game);
					WallManager.resetPlayField(game, player, true);
				}
			}, (30L));
		}
		this.owner = owner;
	}

	public Area getWall() {
		return wall;
	}

	public void setWall(Area wall) {
		this.wall = wall;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Area getPlayfield() {
		return playfield;
	}

	public void setPlayfield(Area playfield) {
		this.playfield = playfield;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getHoles() {
		return holes;
	}

	public void setHoles(int[] holes) {
		this.holes = holes;
	}

	public boolean isIncrementingHoles() {
		return incrementingHoles;
	}

	public void setIncrementingHoles(boolean incrementingHoles) {
		this.incrementingHoles = incrementingHoles;
	}

	public boolean isLeverBusy() {
		return leverBusy;
	}

	public void setLeverBusy(boolean leverBusy) {
		this.leverBusy = leverBusy;
	}

	public boolean isWallPulled() {
		return wallPulled;
	}

	public void setWallPulled(boolean wallPulled) {
		this.wallPulled = wallPulled;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
