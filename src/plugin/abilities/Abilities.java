package plugin.abilities;

import plugin.RPGSystem;

public enum Abilities {
	EYEEXPLOSION,
	STUN,
	REPULSION;
	
	public static Ability createAbility(String abilityName, RPGSystem mainPlugin, int abilityLevel) {
		if (abilityName.equals(Abilities.EYEEXPLOSION.toString())) {
			return new EyeExplosion(mainPlugin, abilityLevel);
		}
		if (abilityName.equals(Abilities.STUN.toString())) {
			return new Repulsion(mainPlugin, abilityLevel);
		}
		if (abilityName.equals(Abilities.REPULSION.toString())) {
			return new Stun(mainPlugin, abilityLevel);
		}
		return null;
	}
}
