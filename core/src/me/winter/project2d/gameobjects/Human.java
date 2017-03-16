package me.winter.project2d.gameobjects;

import me.winter.project2d.GameScreen;

/**
 * <p>Undocumented :(</p>
 *
 * <p>Created by Alexander Winter on 2016-09-10.</p>
 */
public class Human extends SimpleCreature
{
	public Human(GameScreen screen)
	{
		super(screen);
	}

	@Override
	float getWidth()
	{
		return 2f;
	}

	@Override
	float getHeight()
	{
		return 4f;
	}

	@Override
	public void render()
	{
		getBatch().draw(getAssets().getTexture("player.png"), getLocation().x - getWidth() / 2, getLocation().y, getWidth(), getHeight());
	}
}
