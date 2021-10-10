package plugin.abilities;

public enum Abilities {
	EYEEXPLOSION,
	STUN,
	REPULSION,
	PULL;
	
	public static Ability createAbility(String abilityName, int abilityLevel) {
		if (abilityName.equals(Abilities.EYEEXPLOSION.toString())) {
			return new EyeExplosion(abilityLevel);
		}
		if (abilityName.equals(Abilities.STUN.toString())) {
			return new Repulsion(abilityLevel);
		}
		if (abilityName.equals(Abilities.REPULSION.toString())) {
			return new Stun(abilityLevel);
		}
		if (abilityName.equals(Abilities.PULL.toString())) {
			return new Pull(abilityLevel);
		}
		return null;
	}
}
