package me.xnuminousx.moistical.Abilities.Moist;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.xnuminousx.moistical.Abilities.MoisticalAbility;
import me.xnuminousx.moistical.Listeners.AbilityListener;

public class MoistyBarrier extends MoisticalAbility implements AddonAbility {
	
	private long cooldown;
	
	private double radius;
	private String moistymessage;

	private Permission perm;

	public MoistyBarrier(Player player) {
		super(player);
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		setFields();
		start();
	}
	public void setFields() {
		this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.xNuminousx.MoistyBarrier.Cooldown");
		this.moistymessage = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.MoistyBarrier.MoistyMessage");
		
	}

	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		if (player.isSneaking()) {
			barrierDisplay();
			barrierEffect();
		} else {
			remove();
			bPlayer.addCooldown(this);
			return;
		}
	}
	public void barrierDisplay() {
		Location location = player.getLocation();
		double radius = 5;
		
		for (double i = 10; i >= 0; i -= 0.2) {
			radius = i / 2;
			double x = radius * Math.sin(i) * Math.cos(i);
			double z = radius * Math.sin(i) * Math.cos(i);
		ParticleEffect.SPLASH.display(location, (float) x, 0.5F, (float) z, 0F, 2);
		}
	}
	public void barrierEffect() {
		radius = 4;
		Location location = player.getLocation();
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, radius)) {
			if (((entity instanceof LivingEntity)) && (entity.getEntityId() != player.getEntityId())) {
				LivingEntity le = (LivingEntity)entity;
				le.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 1), true);
				le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1), true);
			}
		}
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, radius)) {
			if (((entity instanceof LivingEntity)) && (entity.getEntityId() != player.getEntityId())) {
				entity.getWorld().playSound(location, Sound.ENTITY_SLIME_SQUISH, 0.3F, 1);
				player.sendMessage(ChatColor.DARK_AQUA + getMessage());
				remove();
				bPlayer.addCooldown(this);
				return;
			}
		}
	}
	public String getMessage() {
		return moistymessage;
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
		return "MoistyBarrier";
	}
	
	@Override
	public String getDescription() {
		return "Excite everyone around you with moisty wonders";
	}
	@Override
	public String getInstructions() {
		return "Hold shift and feel the humidity rise around you.";
	}

	@Override
	public String getAuthor() {
		return "MoistyEmpire";
	}
	@Override
	public String getVersion() {
		return "v2.Moist";
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
		return true;
	}
	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilityListener(), ProjectKorra.plugin);
		
		perm = new Permission("bending.ability.moistybarrier");
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
		perm.setDefault(PermissionDefault.TRUE);
		
		ConfigManager.getConfig().addDefault("ExtraAbilities.xNuminousx.MoistyBarrier.Cooldown", 5000);
		ConfigManager.getConfig().addDefault("ExtraAbilities.xNuminousx.MoistyBarrier.MoistyMessage", "You've successfully made another being moist");
		
	}
	@Override
	public void stop() {
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(this.perm);
		super.remove();
		
	}

}
