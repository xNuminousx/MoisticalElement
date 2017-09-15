package me.xnuminousx.moistical.Abilities.Festive;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.AddonAbility;

import me.xnuminousx.moistical.Abilities.FestiveAbility;

public class MerryMoistmas extends FestiveAbility implements AddonAbility {

	private long cooldown;

	public MerryMoistmas(Player player) {
		super(player);
	}

	@Override
	public void progress() {
		
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "MerryMoistmas";
	}
	
	@Override
	public String getDescription() {
		return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "CHRISTMAS ABILITY: " + ChatColor.DARK_RED + "Envoke the merriness around you; spead cheer!";
	}
	
	@Override
	public String getInstructions() {
		return "Nothing yet";
	}

	@Override
	public String getAuthor() {
		return ChatColor.DARK_GREEN + "MerryEmpire";
	}

	@Override
	public String getVersion() {
		return ChatColor.DARK_RED + "v1.Merry";
	}

	@Override
	public boolean isExplosiveAbility() {
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isIgniteAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void load() {
		
	}

	@Override
	public void stop() {
		
	}

}
