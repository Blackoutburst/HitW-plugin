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

	public void setOwner(HPlayer owner) {
		HGame game = this;
		HPlayer player = this.owner;
		if (owner == null) {
			this.type = "none";
			WallManager.pullWall(this.owner, this, true, false);
			GameUpdater.stopGame(this.owner, this);
			
			if (this.owner.getWallTime().size() > 0 && !this.owner.inDuel) {
				if (this.owner.inParty) {
						
					for (HPlayer hp : this.owner.party) {
						ScoreboardManager.updateScoreboard(hp);
						Collections.sort(hp.getWallTime());
						float average = 0;
						
						for (Float f : hp.getWallTime()) {
							average += f;
						}
						average = average / hp.getWallTime().size();
						
						hp.getPlayer().sendMessage("§a§l§m---------------------------------------------");
						hp.getPlayer().sendMessage(Utils.centerText("§6§lGame summary"));
						hp.getPlayer().sendMessage("§6Stage: §f"+hp.board.get(11).replace("Stage: §a", ""));
						hp.getPlayer().sendMessage("§6Score: §f"+hp.score);
						
						if (hp.time > 0) {
							int minutes = hp.time / 60;
							int seconds = (hp.time) % 60;
							String str = String.format("%d:%02d", minutes, seconds);
							hp.getPlayer().sendMessage("§6Time: §f"+str);
						}
						hp.getPlayer().sendMessage(Utils.centerText("§b§lWall completion time"));
						hp.getPlayer().sendMessage("§bFastest wall: §f"+Utils.ROUND.format(hp.getWallTime().get(0))+"s");
						hp.getPlayer().sendMessage("§bSlowest wall: §f"+Utils.ROUND.format(hp.getWallTime().get(hp.getWallTime().size()-1))+"s");
						hp.getPlayer().sendMessage("§bAverage: §f"+Utils.ROUND.format(average)+"s");
						hp.getPlayer().sendMessage(Utils.centerText("§a§lWalls"));
						hp.getPlayer().sendMessage("§aNumber of walls: §f"+hp.wall);
						hp.getPlayer().sendMessage("§aPerfect walls: §f"+hp.perfectWall);
						hp.getPlayer().sendMessage(Utils.centerText("§4§lMistakes"));
						hp.getPlayer().sendMessage("§4Aim chokes: §f"+hp.choke);
						hp.getPlayer().sendMessage("§4Misplaced blocks: §f"+hp.misplaced);
						hp.getPlayer().sendMessage("§4Missed blocks: §f"+hp.missed);
						hp.getPlayer().sendMessage("§a§l§m---------------------------------------------");
					}
				} else {
					ScoreboardManager.updateScoreboard(this.owner);
					Collections.sort(this.owner.getWallTime());
					float average = 0;
					
					for (Float f : this.owner.getWallTime()) {
						average += f;
					}
					average = average / this.owner.getWallTime().size();
					
					this.owner.getPlayer().sendMessage("§a§l§m---------------------------------------------");
					this.owner.getPlayer().sendMessage(Utils.centerText("§6§lGame summary"));
					this.owner.getPlayer().sendMessage("§6Stage: §f"+this.owner.board.get(11).replace("Stage: §a", ""));
					this.owner.getPlayer().sendMessage("§6Score: §f"+this.owner.score);
					
					if (this.owner.time > 0) {
						int minutes = this.owner.time / 60;
						int seconds = (this.owner.time) % 60;
						String str = String.format("%d:%02d", minutes, seconds);
						this.owner.getPlayer().sendMessage("§6Time: §f"+str);
					}
					this.owner.getPlayer().sendMessage(Utils.centerText("§b§lWall completion time"));
					this.owner.getPlayer().sendMessage("§bFastest wall: §f"+Utils.ROUND.format(this.owner.getWallTime().get(0))+"s");
					this.owner.getPlayer().sendMessage("§bSlowest wall: §f"+Utils.ROUND.format(this.owner.getWallTime().get(this.owner.getWallTime().size()-1))+"s");
					this.owner.getPlayer().sendMessage("§bAverage: §f"+Utils.ROUND.format(average)+"s");
					this.owner.getPlayer().sendMessage(Utils.centerText("§a§lWalls"));
					this.owner.getPlayer().sendMessage("§aNumber of walls: §f"+this.owner.wall);
					this.owner.getPlayer().sendMessage("§aPerfect walls: §f"+this.owner.perfectWall);
					this.owner.getPlayer().sendMessage(Utils.centerText("§4§lMistakes"));
					this.owner.getPlayer().sendMessage("§4Aim chokes: §f"+this.owner.choke);
					this.owner.getPlayer().sendMessage("§4Misplaced blocks: §f"+this.owner.misplaced);
					this.owner.getPlayer().sendMessage("§4Missed blocks: §f"+this.owner.missed);
					this.owner.getPlayer().sendMessage("§a§l§m---------------------------------------------");
				}
			}
			
			if (this.owner.inDuel) {
				String player1 = this.owner.getPlayer().getDisplayName() + "§r: " + this.owner.getScore();
				String player2 = this.owner.getOpponent().getDisplayName() + "§r: " + this.owner.getOpponent().getScore();
				
				if (this.owner.duelType.equals("Qualification")) {
					Main.QDuelBusy = false;
				}
				
				if (this.owner.duelType.equals("Finals")) {
					Main.FDuelBusy = false;
				}
				
				this.owner.duelType = "none";
				
				if (this.owner.inParty) {
					for (HPlayer hp : this.owner.party) {
						ScoreboardManager.updateScoreboard(hp);
						hp.getPlayer().sendMessage("§a§l§m---------------------------------------------");
						hp.getPlayer().sendMessage(Utils.centerText("§4§lDuel"));
						hp.getPlayer().sendMessage("");
						hp.getPlayer().sendMessage(Utils.centerText("§6#1 " + (this.owner.getScore() > this.owner.getOpponent().getScore() ? player1 : player2)));
						this.owner.getPlayer().sendMessage(Utils.centerText("§f#2 " + (this.owner.getScore() < this.owner.getOpponent().getScore() ? player1 : player2)));
						hp.getPlayer().sendMessage("§a§l§m---------------------------------------------");
						
						
						final HPlayer own = hp;
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
							@Override
							public void run(){
								own.inDuel = false;
								own.restorePlayerData();
								ScoreboardManager.setDefaultScoreboard(own.getBoard());
								own.getBoard().setTitle(own.getDisplayName());
								own.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1045.5f, 0, 0));
							}
						}, (100L));
					}
				} else {
					ScoreboardManager.updateScoreboard(this.owner);
					this.owner.getPlayer().sendMessage("§a§l§m---------------------------------------------");
					this.owner.getPlayer().sendMessage(Utils.centerText("§6§lDuel"));
					this.owner.getPlayer().sendMessage("");
					this.owner.getPlayer().sendMessage(Utils.centerText("§6#1 " + (this.owner.getScore() > this.owner.getOpponent().getScore() ? player1 : player2)));
					this.owner.getPlayer().sendMessage(Utils.centerText("§f#2 " + (this.owner.getScore() < this.owner.getOpponent().getScore() ? player1 : player2)));
					this.owner.getPlayer().sendMessage("§a§l§m---------------------------------------------");
					
					final HPlayer own = this.owner;
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable(){
						@Override
						public void run(){
							own.inDuel = false;
							own.restorePlayerData();
							ScoreboardManager.setDefaultScoreboard(own.getBoard());
							own.getBoard().setTitle(own.getDisplayName());
							own.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -7.5f, 55, -1045.5f, 0, 0));
						}
					}, (100L));
				}
				
			}
			
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
