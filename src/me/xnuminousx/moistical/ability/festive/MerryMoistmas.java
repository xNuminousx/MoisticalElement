package me.xnuminousx.moistical.ability.festive;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;

import me.xnuminousx.moistical.api.FestiveAbility;

public class MerryMoistmas extends FestiveAbility implements AddonAbility {

	private long cooldown;
	private int radius;

	public MerryMoistmas(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		setFields();
		start();
	}

	private void setFields() {
	}

	@Override
	public void progress() {
		
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		if (player.isSneaking()) {
			fwDisplay();
		}
	}
	
	public void fwDisplay() {
		radius = 4;
		Location loc = player.getLocation();
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, radius)) {
			if (((entity instanceof LivingEntity)) && (entity.getEntityId() != player.getEntityId())) {
				Location tloc = entity.getLocation();
				Firework redfw = (Firework) entity.getWorld().spawnEntity(tloc, EntityType.FIREWORK);
				FireworkMeta fwmeta = redfw.getFireworkMeta();
				FireworkEffect.Type fwtype = FireworkEffect.Type.STAR;
				FireworkEffect fweffect = (FireworkEffect.builder().trail(false).withColor(Color.RED).flicker(true).with(fwtype).build());
				fwmeta.addEffect(fweffect);
				fwmeta.setPower(1);
				redfw.setFireworkMeta(fwmeta);
				
				Firework greenfw = (Firework) entity.getWorld().spawnEntity(tloc, EntityType.FIREWORK);
				FireworkMeta fwmeta2 = greenfw.getFireworkMeta();
				FireworkEffect.Type fwtype2 = FireworkEffect.Type.BURST;
				FireworkEffect fweffect2 = (FireworkEffect.builder().trail(false).withColor(Color.GREEN).flicker(true).with(fwtype2).build());
				fwmeta2.addEffect(fweffect2);
				fwmeta2.setPower(1);
				greenfw.setFireworkMeta(fwmeta2);
				
				remove();
				return;
			}
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
	public boolean isHiddenAbility() {
		return true;
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
