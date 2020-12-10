package utils;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import main.GamePlayer;

/**
 * Manage Scoreboard
 * @author Blackoutburst
 */
public class Board {
	 
    private final Scoreboard scoreboard;
    private final Objective objective;
    private static Team team;
 
    /**
     * Create a new Scoreboard for the player
     * and set a personal team to change his nameTag
     * @param player player who need a Scoreboard
     * @param prefix player name prefix
     * @param suffix player name suffix
     * @author Blackoutburst
     */
    @SuppressWarnings("deprecation")
	public Board(Player player, String prefix, String suffix) {
    	this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(player.getName(), "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(this.scoreboard);
        if (this.scoreboard.getTeam(player.getName()) == null) {
        	this.scoreboard.registerNewTeam(player.getName());
        }
        team = this.scoreboard.getTeam(player.getName());
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        team.addPlayer(player);
    }
 
    /**
     * Add team for every player
     * @param player player who need to add someone else
     * @param newPlayer player added to everyone
     * @author Blackoutburst
     */
    @SuppressWarnings("deprecation")
	public static void addTeam(GamePlayer player, GamePlayer newPlayer) {
        if (player.getBoard().scoreboard.getTeam(newPlayer.getPlayer().getName()) == null) {
        	player.getBoard().scoreboard.registerNewTeam(newPlayer.getPlayer().getName());
        }

        team = player.getBoard().scoreboard.getTeam(newPlayer.getPlayer().getName());
        team.setPrefix(newPlayer.getRank());
        team.setSuffix("§r");
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        team.addPlayer(newPlayer.getPlayer());
    }
    
    /**
     * Get Scorebaord
     * @return Scorebaord
     * @author Blackoutburst
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
 
    /**
     * Get Scoreboard name
     * @return Scoreboard name
     * @author Blackoutburst
     */
    public String getTitle() {
        return objective.getDisplayName();
    }
 
    /**
     * Set Scoreboard name
     * @param name new Name
     * @author Blackoutburst
     */
    public void setTitle(String name) {
        this.objective.setDisplayName(name);
    }
 
    /**
     * Set lines
     * @param row line position
     * @param text line text
     * @author Blackoutburst
     */
    public void set(int row, String text) {
        Validate.isTrue(16 > row, "Row can't be higher than 16");
        if(text.length() > 32) { text = text.substring(0, 32); }
 
        for(String entry : this.scoreboard.getEntries()) {
            if(this.objective.getScore(entry).getScore() == row) {
                this.scoreboard.resetScores(entry);
                break;
            }
        }
 
        this.objective.getScore(text).setScore(row);
    }
 
    /**
     * Remove line
     * @param row line position
     * @author Blackoutburst
     */
    public void remove(int row) {
        for(String entry : this.scoreboard.getEntries()) {
            if(this.objective.getScore(entry).getScore() == row) {
                this.scoreboard.resetScores(entry);
                break;
            }
        }
    }
}
