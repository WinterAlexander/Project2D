package me.winter.project2d.gameobjects;

import java.util.Comparator;

/**
 * <p>A class to represents a GameObject that can be rendered</p>
 *
 * <p>Created by Alexander Winter on 2016-08-27.</p>
 */
public interface Renderable
{
	void render();

	/**
	 * Used to sort Renderable objects in a collection. The resulting collection will be in reverse order.
	 */
	Comparator<Renderable> PRIORITY_COMPARATOR = (a, b) -> Float.compare(b.getPriority(), a.getPriority());

	float VERYLOW_PRIORITY = 0.3f;
	float LOW_PRIORITY = 0.4f;
	float MEDIUM_PRIORITY = 0.5f;
	float HIGH_PRIORITY = 0.6f;
	float VERYHIGH_PRIORITY = 0.7f;

	/**
	 * <p>
	 * The render priority is a float value between 0 and 1
	 * determining the priority over other Renderables this
	 * object has.
	 * <br />
	 * 1 means the object is the last thing to be rendered and 0
	 * means it's the first one.
	 * </p>
	 * @return render priority
	 */
	default float getPriority()
	{
		return Renderable.MEDIUM_PRIORITY;
	}


}
