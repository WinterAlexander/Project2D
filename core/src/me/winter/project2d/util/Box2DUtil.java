package me.winter.project2d.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * <p>Undocumented :(</p>
 *
 * <p>Created by Alexander Winter on 2016-08-28.</p>
 */
public class Box2DUtil
{
	private Box2DUtil() {}

	/**
	 * <p>Makes a fixture definition from arguments</p>
	 *
	 * @param shape Shape of the fiction
	 * @param friction Friction coefficient, 0 = slide 1 = sticky
	 * @param restitution elasticity
	 * @param density usually in kg/m^2
	 * @param isSensor A sensor shape collects contact information but never generates a collision response.
	 * @return the new fixture
	 */
	public static FixtureDef newFixtureDef(Shape shape, float friction, float restitution, float density, boolean isSensor)
	{
		FixtureDef def = new FixtureDef();
		def.shape = shape;
		def.friction = friction;
		def.restitution = restitution;
		def.density = density;
		def.isSensor = isSensor;
		return def;
	}

	/**
	 * @see Box2DUtil#newFixtureDef(Shape, float, float, float, boolean)
	 */
	public static FixtureDef newFixtureDef(Shape shape, float friction, float restitution, float density)
	{
		return newFixtureDef(shape, friction, restitution, density, false);
	}

	public static float[] merge(float[] poly1, float[] poly2)
	{
		return poly1;
	}
}
