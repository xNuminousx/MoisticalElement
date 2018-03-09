package me.xnuminousx.moistical.api;

import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.ElementalAbility;

public abstract class FestiveAbility  extends ElementalAbility {
	
	public FestiveAbility(Player player) {
		super(player);
	}
	
	public final Element getElement() {
		return MoisticalElement.FESTIVE;
	}
}
