package core;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private final HGame game;

	private final HPlayer hPlayer;

	private final Boolean manualEnd;

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public GameEndEvent(HGame game, HPlayer hPlayer, Boolean manualEnd) {
		this.game = game;
		this.hPlayer = hPlayer;
		this.manualEnd = manualEnd;
	}

	public HGame getGame() {
		return game;
	}


	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}


	public HPlayer getPlayer() {
		return hPlayer;
	}

	public Boolean getManualEnd() {
		return manualEnd;
	}
}
