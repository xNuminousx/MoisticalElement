package me.xnuminousx.moistical.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;

import me.xnuminousx.moistical.ability.moist.MoistSpray;
import me.xnuminousx.moistical.ability.moist.MoistyBarrier;
import me.xnuminousx.moistical.ability.festive.MerryMoistmas;
import me.xnuminousx.moistical.ability.festive.ScaredWet;

public class AbilityListener implements Listener {
	
	@EventHandler
	public void onSwing(PlayerAnimationEvent event) {
		
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		
		if (event.isCancelled() || bPlayer == null) {
			return;
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("MoistSpray")) {
			new MoistSpray(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("ScaredWet")) {
			new ScaredWet(player);	
			
		}
	}
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		
		if (event.isCancelled() || bPlayer == null) {
			return;
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("MoistyBarrier")) {
			new MoistyBarrier(player);
			
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("MerryMoistmas")) {
			new MerryMoistmas(player);
		}
	}
}
