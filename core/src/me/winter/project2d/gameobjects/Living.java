package me.winter.project2d.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * <p>Represents a living thing !</p>
 *
 * <p>Created by Alexander Winter on 2016-09-08.</p>
 */
public interface Living extends Physical, Renderable
{
	/**
	 * The health of a living thing is from 0 to 1, if 0 the living is death.
	 * All living has max 1 health, defense is the only factor to increase life.
	 * @return health, from 0 to 1
	 */
	float getHealth();

	/**
	 * Defense of the Living, from 0 to 1.
	 * 0 means the attacked is taking 100% of damage and knockback,
	 * 1 means is taking 0%.
	 * @return defense, from 0 to 1
	 */
	float getDefense();

	/**
	 * Hits the Living by specified attack and knockback
	 * @param attack from 0 to 1, 1 insta kill a 0% defense ennemy
	 * @param knockback a vector expressing knockback, force is increased by attack
	 */
	void hit(float attack, Vector2 knockback);
}
