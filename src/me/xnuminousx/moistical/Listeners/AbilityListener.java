package me.xnuminousx.moistical.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;

import me.xnuminousx.moistical.API.MoisticalElement;
import me.xnuminousx.moistical.Abilities.Festive.ScaredWet;
import me.xnuminousx.moistical.Abilities.Moist.MoistSpray;
import me.xnuminousx.moistical.Abilities.Moist.MoistyBarrier;

public class AbilityListener implements Listener {
	@EventHandler
	public void onSwing(PlayerAnimationEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		if (bPlayer == null) {
			return;
		}
		if (bPlayer.isElementToggled(MoisticalElement.MOISTICAL)) {
			if (bPlayer.getBoundAbility().getName().equalsIgnoreCase("MoistSpray")) {
				new MoistSpray(player);
			} else if (bPlayer.getBoundAbility().getName().equalsIgnoreCase("ScaredWet")) {
				new ScaredWet(player);
			}
		}
	}
	@EventHandler (ignoreCancelled = true)
	public void onSneak(PlayerToggleSneakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		if (bPlayer == null) {
			return;
		}
		if (bPlayer.isElementToggled(MoisticalElement.MOISTICAL)) {
			if (bPlayer.getBoundAbility().getName().equalsIgnoreCase("MoistyBarrier")) {
				new MoistyBarrier(player);
			}
		}
	}
}
