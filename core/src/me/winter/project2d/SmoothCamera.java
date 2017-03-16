package me.winter.project2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.winter.project2d.gameobjects.GameObject;

/**
 * An OrthographicCamera with smooth camera updating
 *
 * Created by 1541869 on 2016-09-01.
 */
public class SmoothCamera extends OrthographicCamera
{
	private GameObject target;
	private boolean mouseVision = false;
	private float minX, minY, maxX, maxY;

	private Vector2 tmpVec2 = new Vector2();
	private Vector3 tmpVec3 = new Vector3();

	public SmoothCamera()
	{
		super();
		setBounds(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
	}

	@Override
	public void update(boolean updateFrustum)
	{
		if(target != null)
		{
			float lookX = target.getLocation().x;
			float lookY = target.getLocation().y;

			if(mouseVision && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
			{
				tmpVec3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				unproject(tmpVec3);
				tmpVec2.set(tmpVec3.x, tmpVec3.y);

				lookX = (lookX + tmpVec2.x) / 2;
				lookY = (lookY + tmpVec2.y) / 2;
			}
			smoothLookAt(lookX, lookY);
		}

		super.update(updateFrustum);
	}

	/**
	 * Sets the camera position smoothly integrating a part of the previous position
	 * @param x new position x
	 * @param y new position y
	 */
	public void smoothLookAt(float x, float y)
	{
		smoothLookAt(x, y, 0.5f);
	}

	/**
	 * Sets the camera position smoothly integrating a part of the previous position
	 * The smoothness is a value in ]0, 1[, 0 meaning not smooth and 1 meaning
	 * the camera will never move from previous location.
	 * @param x new position x
	 * @param y new position y
	 * @param smooth smooth ratio
	 */
	public void smoothLookAt(float x, float y, float smooth)
	{
		position.x = MathUtils.clamp(position.x * smooth + x * (1 - smooth), minX, maxX);
		position.y = MathUtils.clamp(position.y * smooth + y * (1 - smooth), minY, maxY);
	}

	public GameObject getTarget()
	{
		return target;
	}

	public void setTarget(GameObject target)
	{
		this.target = target;
		position.x = target.getLocation().x;
		position.y = target.getLocation().y;
	}

	public boolean isMouseVision()
	{
		return mouseVision;
	}

	public void setMouseVision(boolean mouseVision)
	{
		this.mouseVision = mouseVision;
	}

	public float getMinX()
	{
		return minX;
	}

	public float getMinY()
	{
		return minY;
	}

	public float getMaxX()
	{
		return maxX;
	}

	public float getMaxY()
	{
		return maxY;
	}

	public void setBounds(float minX, float minY, float maxX, float maxY)
	{
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
}
