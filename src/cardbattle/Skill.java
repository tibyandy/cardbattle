package cardbattle;

import static cardbattle.Property.DELAYED_EFFECT_2;
import static cardbattle.Property.SLASH_BOOSTED;


public enum Skill {

	// Se não leva dano, aumenta V+1 para outros golpes
	SLASH(3, Speed.B, Property.BOOST_SLASH),
	TRIPLE_SLASH(7, Speed.B, SLASH_BOOSTED),
	// Se acertar, diminui V-1 para o oponente no próximo turno
	HEAVY_SLASH(5, Speed.C, SLASH_BOOSTED, Property.KNOCKDOWN),

	ATTACK(10, Speed.B),

	ATTACK_QUICK(5, Speed.A),
	ATTACK_SLOW(15, Speed.C),
	ATTACK_D_SPEED(15, Speed.D),
	ATTACK_E_SPEED(15, Speed.E),
//	SKILL_FIREBALL(12, Speed.AVERAGE, PROJECTILE),

	METEOR(0, Speed.B, DELAYED_EFFECT_2),

	NONE(0, Speed.E);

	public final Integer damage;
	public final Integer speed;
	public final Integer effectDelay;

	public final boolean slashBoosted;
	public final boolean boostSlash;
	public final boolean knockdown;
	
	private Skill(Integer damage, Speed speed, Property... properties) {
		this.damage = damage;
		this.speed = speed.n;

		Integer effectDelay = null;
		boolean slashBoosted = false;
		boolean boostSlash = false;
		boolean knockdown = false;
		for (Property property : properties) {
			switch (property) {
			case DELAYED_EFFECT_2:
				effectDelay = 2;
				break;
			case SLASH_BOOSTED:
				slashBoosted = true;
				break;
			case BOOST_SLASH:
				boostSlash = true;
				break;
			case KNOCKDOWN:
				knockdown = true;
				break;
			}
		}
		this.effectDelay = effectDelay;
		this.slashBoosted = slashBoosted;
		this.boostSlash = boostSlash;
		this.knockdown = knockdown;
	}
}
