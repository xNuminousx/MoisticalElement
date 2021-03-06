package me.xnuminousx.moistical.ability.festive;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class ScaredWet extends AvatarAbility implements AddonAbility {
	
	private long cooldown;
	private Location origin;
	private Location location;
	private Vector direction;
	private Location start;
	private int range;
	private String spookyMessage;
	private String spookyTitle;
	private Material blockType;
	private byte blockByte;

	public ScaredWet(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		setFields();
		start();
		start = location.clone();
	}

	private void setFields() {
		this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.xNuminousx.ScaredWet.Cooldown");
		this.range = ConfigManager.getConfig().getInt("ExtraAbilities.xNuminousx.ScaredWet.Range");
		this.spookyTitle = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.ScaredWet.SpookyTitle");
		this.spookyMessage = ConfigManager.getConfig().getString("ExtraAbilities.xNuminousx.ScaredWet.SpookyMessage");
		
		this.origin = player.getLocation().clone().add(0, 1, 0);
		this.location = origin;
		this.direction = player.getLocation().getDirection().clone();
		this.blockType = Material.OBSIDIAN;
		
	}

	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline() || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			remove();
			return;
		}
		if (location.distanceSquared(start) > range * range) {
			bPlayer.addCooldown(this);
			remove();
			return;
		}
		blast();
	}
	@SuppressWarnings("deprecation")
	public void blast() {
		location.add(direction.multiply(1));
		ParticleEffect.FLAME.display(location, 0, 0, 0, 0.009F, 1);
		ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData) new ParticleEffect.BlockData(blockType, blockByte), 0.1F, 0.1F, 0.1F, 1, 5, location, 500);
		location.getWorld().playSound(location, Sound.ENTITY_BAT_AMBIENT, 0.05F, 1);
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1.5)) {
			if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
				show();
				bPlayer.addCooldown(this);
				remove();
				return;
			}
		}
		for (Player target : GeneralMethods.getPlayersAroundPoint(location, 1.5)) {
			if (target instanceof LivingEntity && target.getUniqueId() != player.getUniqueId()) {
				target.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + getTitle(), ChatColor.BLACK + "" + getMessage());
				target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1), true);
				show();
				bPlayer.addCooldown(this);
				remove();
				return;
			}
		}
		if (GeneralMethods.isSolid(location.getBlock())) {
			ParticleEffect.LAVA.display(location, 1, 3, 1, 0.2F, 5);
			ParticleEffect.EXPLOSION_LARGE.display(location, 0, 2, 0, 1, 2);
			player.getWorld().spawn(location.add(0, 1, 0), Witch.class).remove();
			location.getWorld().playSound(location, Sound.ENTITY_WITCH_AMBIENT, 2, 1);
			location.getWorld().playSound(location, Sound.ENTITY_ENDERMEN_STARE, 1, 0.5F);
			bPlayer.addCooldown(this);
			remove();
			return;
		}
	}
	
	public void show() {
		ParticleEffect.WITCH_MAGIC.display(location, 1, 1, 1, 0.05F, 5);
		location.getWorld().playSound(location, Sound.ENTITY_LIGHTNING_THUNDER, 1, 1.5F);
		return;
		
	}
	
	public String getMessage() {
		return spookyMessage;
		
	}
	
	public String getTitle() {
		return spookyTitle;
		
	}
	
	@Override
	public boolean isHiddenAbility() {
		return true;
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
		return "ScaredWet";
	}
	@Override
	public String getDescription() {
		return ChatColor.BLACK + "" + ChatColor.BOLD + "HALLOWEEN ABILITY:" + ChatColor.GOLD + " You might just spook the moistness right out of them!";
	}
	@Override
	public String getInstructions() {
		return ChatColor.DARK_GRAY + "Left-Click if you're brave enough...." + ChatColor.BLACK + "" + ChatColor.BOLD + " BOO";
	}

	@Override
	public String getAuthor() {
		return "SpookyEmpire";
	}

	@Override
	public String getVersion() {
		return "v1.BOO";
	}

	@Override
	public boolean isExplosiveAbility() {
		return false;
	}

	@Override
	public boolean isHarmlessAbility() {
		return true;
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
