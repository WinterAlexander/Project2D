package me.winter.project2d.gameobjects;

/**
 *
 * Created by 1541869 on 2016-09-09.
 */
public interface Controllable
{
	/**
	 * Called on every updates when left is pressed
	 */
	void moveLeft(float delta);

	/**
	 * Called on every updates when right is pressed
	 */
	void moveRight(float delta);

	/**
	 * Called when jump button is pressed
	 */
	void jump();
}
