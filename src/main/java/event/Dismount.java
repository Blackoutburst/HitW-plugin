package event;

import org.bukkit.entity.ArmorStand;
import org.spigotmc.event.entity.EntityDismountEvent;

public class Dismount {

	public void execute(EntityDismountEvent event) {
		if (event.getDismounted() instanceof ArmorStand)
			event.getDismounted().remove();
	}
}
