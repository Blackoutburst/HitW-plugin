package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandsManager {
	
	public void execute(CommandSender sender, Command command, String label, String[] args) {
		switch(label) {
			case "discord": new CommandDiscord().run(sender); break;
			case "lag": new CommandLag().run(sender, args); break;
			case "memtime": new CommandMemtime().run(sender, args); break;
			case "delay": new CommandDelay().run(sender, args); break;
			case "fly": new CommandFly().run(sender); break;
			case "title": new CommandTitle().run(sender); break;
			case "animation": new CommandAnimation().run(sender); break;
			case "right": new CommandRight().run(sender); break;
			case "blind": new CommandBlind().run(sender); break;
			case "coop": case "p": case "party": new CommandParty().run(sender, args); break;
			case "spawn": case "lobby": new CommandSpawn().run(sender); break;
			case "play": new CommandPlay().run(sender, args); break;
			case "l": case "leave": new CommandLeave().run(sender); break;
			case "duel": new CommandDuel().run(sender, args); break;
			case "afk": new CommandAFK().run(sender); break;
			default: return;
		}
	}
}
