package me.xnuminousx.moistical.Abilities.Moist;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.xnuminousx.moistical.Abilities.MoisticalAbility;
import me.xnuminousx.moistical.Listeners.AbilityListener;

public class MoistSpray extends MoisticalAbility implements AddonAbility{
	
	private long cooldown;
	private double range;
	
	
	private Location origin;
	private Location location;
	private Location start;
	private Vector direction;
	private String moistymessage;
	private String border1;
	private String border2;
	private Permission perm;

	public MoistSpray(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		setFields();
		start();
		
		start = player.getLocation();
		start = location.clone();
		location = start.clone();
	}
	public void setFields() {
		this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.xNuminousx.MoistSpray.Cooldown");
		this.range = ConfigManager.getConfig().getDouble("ExtraAbilities.xNuminousx.MoistSpray.Range");
		this.border1 = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.MoistSpray.Border 1");
		this.moistymessage = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.MoistSpray.MoistyMessage");
		this.border2 = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.MoistSpray.Border 2");
		this.origin = player.getLocation().clone().add(0, 1.3, 0);
		this.location = origin;
		this.direction = player.getLocation().getDirection().clone();
	}
	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		blast();
	}
	
	public void blast() {
		if (location.distanceSquared(start) > range * range) {
			remove ();
			return;
		}
		
		location.add(direction.multiply(1));
		ParticleEffect.SPLASH.display(location, 0.2F, 0.2F, 0.5F, 1, 3);
		ParticleEffect.DRIP_WATER.display(location, 0.3F, 0.2F, 0.3F, 0, 1);
		GeneralMethods.displayColoredParticle(location, "0081FF");
		location.getWorld().playSound(location, Sound.ITEM_BUCKET_FILL, 0.1F, 1);
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1.5)) {
			if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
				show();
				player.sendMessage(ChatColor.AQUA + borderOne() + ChatColor.DARK_AQUA + getMessage() + ChatColor.AQUA + borderTwo());
				remove();
				return;
			}
		}
		if (GeneralMethods.isSolid(location.getBlock())) {
			ParticleEffect.FLAME.display(location, 0, 1, 0, 0.3F, 5);
			remove();
			return;
		}
	}
	
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
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilityListener(), ProjectKorra.plugin);
		
		perm = new Permission("bending.ability.moistspray");
		ProjectKorra.plugin.getServer().getPluginManager().addPermission(perm);
		perm.setDefault(PermissionDefault.TRUE);
		
		//Element console load message
		ProjectKorra.log.info("Successfully loaded Moistical Element");
		
		//Element config options
		ConfigManager.languageConfig.get().addDefault("Chat.Colors.Moistical", "BLUE");
		ConfigManager.languageConfig.get().addDefault("Chat.Colors.MoisticalSub", "DARK_PURPLE");
		ConfigManager.languageConfig.get().addDefault("Chat.Prefixes.Moistical", "[Moist]");
		
		//MoistSpray config options
		ConfigManager.getConfig().addDefault("ExtraAbilities.xNuminousx.Cooldown", 5000);
		ConfigManager.getConfig().addDefault("ExtraAbilities.xNuminousx.Range", 20);
		ConfigManager.getConfig().addDefault("ExtraAbilities.xNuminousx.Border 1", "---");
		ConfigManager.getConfig().addDefault("ExtraAbilities.xNuminousx.MoistyMessage", "Your body increases in moistiness");
		ConfigManager.getConfig().addDefault("ExtraAbilities.xNuminousx.Border 2", "---");
		ConfigManager.getConfig().addDefault("ExtraAbilities.xNuminousx.Speed", 1);
		ConfigManager.defaultConfig.save();
	}
	@Override
	public void stop() {
		ProjectKorra.plugin.getServer().getPluginManager().removePermission(this.perm);
		super.remove();
	}

}
