package me.winter.project2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
import me.winter.project2d.gameobjects.Car;
import me.winter.project2d.gameobjects.Controllable;

/**
 * <p>Edit the world with one click !</p>
 *
 * <p>Created by Alexander Winter on 2016-05-24.</p>
 */
public class GameInput extends InputAdapter
{
	private GameScreen game;
	private Controllable controlled;

	public GameInput(GameScreen game)
	{
		this.game = game;
		controlled = null;
	}

	public void update(float delta)
	{
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			if(controlled != null)
				controlled.moveRight(delta);

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			if(controlled != null)
				controlled.moveLeft(delta);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		switch(keycode)
		{
			case Input.Keys.F12:
				game.setDebugRender(!game.isDebugRender());
				break;

			case Input.Keys.SPACE:
				if(controlled != null)
					controlled.jump();
				break;
		}
		return true;
	}

	@Override
	public boolean scrolled(int amount)
	{
		game.getCamera().zoom *= Math.pow(1.1, amount);
		return true;
	}

	public Controllable getControlled()
	{
		return controlled;
	}

	public void setControlled(Controllable controlled)
	{
		this.controlled = controlled;
	}
}
