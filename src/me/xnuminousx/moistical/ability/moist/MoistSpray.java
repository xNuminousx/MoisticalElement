package me.xnuminousx.moistical.ability.moist;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.xnuminousx.moistical.api.MoisticalAbility;

public class MoistSpray extends MoisticalAbility implements AddonAbility{
	
	private long cooldown;
	
	private Location origin;
	private Location location;
	private Vector direction;
	private String moistymessage;
	private String border1;
	private String border2;
	private long time;
	private long duration;

	public MoistSpray(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		setFields();
		time = System.currentTimeMillis();
		start();
	}
	public void setFields() {
		this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.xNuminousx.MoistSpray.Cooldown");
		this.border1 = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.MoistSpray.Border 1");
		this.moistymessage = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.MoistSpray.MoistyMessage");
		this.border2 = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.MoistSpray.Border 2");
		this.duration = 1000;
		
		this.origin = player.getLocation().clone().add(0, 1, 0);
		this.location = origin;
		this.direction = player.getLocation().getDirection().clone();
	}
	
	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline() || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			remove();
			return;
		}
		if (System.currentTimeMillis() > time + duration) {
			bPlayer.addCooldown(this);
			remove();
			return;
			
		}
		blast();
	}
	
	public void blast() {
		location.add(direction.multiply(1));
		ParticleEffect.SPLASH.display(location, 0.2F, 0.2F, 0.5F, 1, 3);
		ParticleEffect.DRIP_WATER.display(location, 0.3F, 0.2F, 0.3F, 0, 1);
		GeneralMethods.displayColoredParticle(location, "0081FF");
		location.getWorld().playSound(location, Sound.ITEM_BUCKET_FILL, 0.1F, 1);
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1.5)) {
			if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
				show();
				bPlayer.addCooldown(this);
				remove();
				return;
			}
		}
		if (GeneralMethods.isSolid(location.getBlock())) {
			ParticleEffect.FLAME.display(location, 0, 1, 0, 0.3F, 5);
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void show() {
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1.5)) {
			Location target = entity.getLocation().add(0, 1, 0);
			double radius = 2;
			for (double y = 10; y >= 0; y -= 0.5) {
				radius = y / 4;
				double x = radius * Math.cos(1.5 * y);
				double z = radius * Math.sin(1.5 * y);
				ParticleEffect.FIREWORKS_SPARK.display(target, (float) x, (float) radius, (float) z, 0.05F, 1);
				ParticleEffect.SPLASH.display(target, (float) x, (float) radius, (float) z, 0.05F, 2);
				target.getWorld().playSound(location, Sound.ENTITY_SLIME_SQUISH, 0.3F, 1);
			}
		}
		for (Player target : GeneralMethods.getPlayersAroundPoint(location, 1.5)) {
			target.sendTitle(" ", ChatColor.AQUA + borderOne() + ChatColor.DARK_AQUA + getMessage() + ChatColor.AQUA + borderTwo());
		}
	}
	
	public String getMessage() {
		return moistymessage;
	}
	
	public String borderOne() {
		return border1;
		
	}
	
	public String borderTwo() {
		return border2;
		
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
		return "MoistSpray";
	}
	@Override
	public String getDescription() {
		return "Feel the moisture intensify....";
	}
	
	@Override
	public String getInstructions() {
		return "Left-click to caress unexpecting targets with your intense moisture.";
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
		return false;
	}
	@Override
	public void load() {
	}
	@Override
	public void stop() {
		super.remove();
	}

}
