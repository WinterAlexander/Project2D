package me.winter.project2d.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import me.winter.project2d.AssetSupplier;
import me.winter.project2d.GameScreen;

/**
 * <p>Undocumented :(</p>
 *
 * <p>Created by Alexander Winter on 2016-08-27.</p>
 */
public abstract class GameObject
{
	private GameScreen screen;

	public GameObject(GameScreen screen)
	{
		this.screen = screen;
	}

	public abstract Vector2 getLocation();

	public GameScreen getScreen()
	{
		return screen;
	}

	public World getWorld()
	{
		return getScreen().getWorld();
	}

	public AssetSupplier getAssets()
	{
		return getScreen().getAssetManager();
	}

	public SpriteBatch getBatch()
	{
		return getScreen().getSpriteBatch();
	}
}
