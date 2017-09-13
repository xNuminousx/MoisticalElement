package me.xnuminousx.moistical.Abilities;

import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.ElementalAbility;

import me.xnuminousx.moistical.API.MoisticalElement;

public abstract class MoisticalAbility extends ElementalAbility {

	public MoisticalAbility(Player player) {
		super(player);
	}
	
	public final Element getElement() {
		return MoisticalElement.MOISTICAL;
		
	}
}