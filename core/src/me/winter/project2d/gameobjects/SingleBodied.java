package me.winter.project2d.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;

/**
 * <p>A Physical object that have only one body</p>
 *
 * <p>Created by Alexander Winter on 2016-09-10.</p>
 */
public interface SingleBodied extends Physical
{
	/**
	 * @return An array containing the only body of this physical object
	 */
	@Override
	default Body[] getBodies()
	{
		return new Body[] { getMainBody() };
	}

	/**
	 * A single bodied physical object shouldn't have joints.
	 * @return null
	 */
	@Override
	default Joint[] getJoints()
	{
		return null;
	}
}
