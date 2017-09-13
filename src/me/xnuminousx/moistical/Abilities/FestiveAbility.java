package me.xnuminousx.moistical.Abilities;

import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.SubAbility;

import me.xnuminousx.moistical.API.MoisticalElement;

public abstract class FestiveAbility  extends CoreAbility implements SubAbility {
	
	public FestiveAbility(Player player) {
		super(player);
	}
	
	@Override
	public Class<? extends Ability> getParentAbility() {
		return MoisticalAbility.class;
	}
	
	@Override
	public Element getElement() {
		return MoisticalElement.FESTIVE;
	}
}
