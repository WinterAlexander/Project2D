package me.winter.project2d.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

/**
 * <p>Something that has at least one body</p>
 *
 * <p>Created by Alexander Winter on 2016-08-28.</p>
 */
public interface Physical
{
	void spawn(World world, float posX, float posY);

	default void spawn(World world, Vector2 pos)
	{
		spawn(world, pos.x, pos.y);
	}

	void remove(World world);

	default void remove()
	{
		remove(getWorld());
	}

	/**
	 * <p>
	 *     The main body of a Physical object hasn't any obligations but might be used to
	 *     apply force to the Physical or to any internal class usage.
	 *     <br />
	 *     Can be null if the Physical isn't a SingleBodied object
	 * </p>
	 *
	 * @return main body
	 */
	Body getMainBody();
	Body[] getBodies();
	Joint[] getJoints();

	/**
	 * Retrieves the world from bodies.
	 * @return the current world this object has been spawned in, otherwise null
	 */
	default World getWorld()
	{
		if(getMainBody() != null)
			return getMainBody().getWorld();

		if(getBodies() == null)
			return null;

		if(getBodies()[0] != null)
			return getBodies()[0].getWorld();

		return null;
	}
}
