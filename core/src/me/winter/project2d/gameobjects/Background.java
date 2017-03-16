package me.winter.project2d.gameobjects;

import com.badlogic.gdx.math.Vector2;
import me.winter.project2d.GameScreen;

/**
 * <p>Undocumented :(</p>
 *
 * <p>Created by Alexander Winter on 2016-08-29.</p>
 */
public class Background extends GameObject implements Renderable
{
	private String texture;
	private Vector2 tmpVector2 = new Vector2();

	public Background(GameScreen screen, String texture)
	{
		super(screen);
		this.texture = texture;
	}

	@Override
	public Vector2 getLocation()
	{
		//if(getScreen().getTarget() == this)
		//	return tmpVector2.set(0, 0);

		return tmpVector2.set(0, 0);
	}

	@Override
	public void render()
	{
		float gameWidth = getScreen().getCamera().viewportWidth * getScreen().getCamera().zoom;
		float gameHeight = getScreen().getCamera().viewportHeight * getScreen().getCamera().zoom;

		getBatch().draw(getAssets().getTexture(texture),
				getScreen().getCamera().position.x - gameWidth / 2,
				getScreen().getCamera().position.y - gameHeight / 2,
				gameWidth,
				gameHeight);
	}

	@Override
	public float getPriority()
	{
		return 0.01f;
	}
}
