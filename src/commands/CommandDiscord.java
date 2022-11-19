package commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import utils.Utils;

public class CommandDiscord {
	
	public void run(CommandSender sender) {
		sender.sendMessage("§a§l§m---------------------------------------------");
		sender.sendMessage(" ");
		TextComponent msg = new TextComponent(Utils.centerText("§eClick §bhere §eto join the discord !"));
		msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/Gh24vw5b54"));
		((Player)sender).spigot().sendMessage(msg);
		sender.sendMessage(" ");
		sender.sendMessage("§a§l§m---------------------------------------------");
	}
}
