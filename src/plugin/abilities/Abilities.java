package plugin.abilities;

import plugin.RPGSystem;

public enum Abilities {
	EYEEXPLOSION,
	STUN,
	REPULSION;
	
	public static Ability createAbility(String abilityName, RPGSystem mainPlugin) {
		if (abilityName.equals(Abilities.EYEEXPLOSION.toString())) {
			return new EyeExplosion(mainPlugin);
		}
		if (abilityName.equals(Abilities.STUN.toString())) {
			return new Repulsion(mainPlugin);
		}
		if (abilityName.equals(Abilities.REPULSION.toString())) {
			return new Stun(mainPlugin);
		}
		return null;
	}
}
