package cardbattle;


public enum Skill {

	// Se não leva dano, aumenta V+1 para outros golpes
	SLASH(3, Speed.B),
	TRIPLE_SLASH(7, Speed.B),
	// Se acertar, diminui V-1 para o oponente no próximo turno
	HEAVY_SLASH(5, Speed.C),

	ATTACK(10, Speed.B),

	ATTACK_QUICK(5, Speed.A),
	ATTACK_SLOW(15, Speed.C),
	ATTACK_D_SPEED(15, Speed.D),
	ATTACK_E_SPEED(15, Speed.E),
//	SKILL_FIREBALL(12, Speed.AVERAGE, PROJECTILE),

	METEOR(0, Speed.B, Property.DELAYED_EFFECT_2),

	NONE(0, Speed.E);

	public final Integer damage;
	public final Integer speed;
	public final Integer effectDelay;

	private Skill(Integer damage, Speed speed, Property... properties) {
		this.damage = damage;
		this.speed = speed.n;
		Integer effectDelay = null;
		for (Property property : properties) {
			if (property.type == Property.Type.DELAYED_EFFECT) {
				effectDelay = property.arg;
			}
		}
		this.effectDelay = effectDelay;
	}
}
