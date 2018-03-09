package me.xnuminousx.moistical.config;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.xnuminousx.moistical.listener.AbilityListener;

public class Handler extends AvatarAbility implements AddonAbility {

	public Handler(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getCooldown() {
		
		return 0;
	}

	@Override
	public Location getLocation() {
		
		return null;
	}

	@Override
	public String getName() {
		
		return "Handler";
	}
	
	@Override
	public boolean isHiddenAbility() {
		return true;
	}

	@Override
	public boolean isHarmlessAbility() {
		
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		
		return false;
	}

	@Override
	public void progress() {
	}

	@Override
	public String getAuthor() {
		
		return null;
	}

	@Override
	public String getVersion() {
		
		return null;
	}

	@Override
	public void load() {
		FileConfiguration langConfig = ConfigManager.languageConfig.get();
		FileConfiguration config = ConfigManager.getConfig();
		
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilityListener(), ProjectKorra.plugin);
		ProjectKorra.log.info("Successfully loaded Moistical");
		
		langConfig.addDefault("Chat.Colors.Moistical", "BLUE");
		langConfig.addDefault("Chat.Colors.Festive", "DARK_PURPLE");
		langConfig.addDefault("Chat.Prefixes.Moistical", "[Moist]");
		
		config.addDefault("ExtraAbilities.xNuminousx.Cooldown", 5000);
		config.addDefault("ExtraAbilities.xNuminousx.Border 1", "---");
		config.addDefault("ExtraAbilities.xNuminousx.MoistyMessage", "Your body increases in moistiness");
		config.addDefault("ExtraAbilities.xNuminousx.Border 2", "---");
		config.addDefault("ExtraAbilities.xNuminousx.Speed", 1);
		
		config.addDefault("ExtraAbilities.xNuminousx.MoistyBarrier.Cooldown", 5000);
		config.addDefault("ExtraAbilities.xNuminousx.MoistyBarrier.MoistyMessage", "You've successfully made another being moist");
		
		config.addDefault("ExtraAbilities.xNuminousx.ScaredWet.Cooldown", 8000);
		config.addDefault("ExtraAbilities.xNuminousx.ScaredWet.Range", 20);
		config.addDefault("ExtraAbilities.xNuminousx.ScaredWet.SpookyTitle", "Jeepers creepers!");
		config.addDefault("ExtraAbilities.xNuminousx.ScaredWet.SpookyMessage", "I'm excreting moisture!!!");
		
		ConfigManager.defaultConfig.save();
		ConfigManager.languageConfig.save();
	}

	@Override
	public void stop() {
		ConfigManager.defaultConfig.save();
		ConfigManager.languageConfig.save();
		super.remove();
	}

}
