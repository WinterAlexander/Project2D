package me.winter.project2d.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import me.winter.project2d.GameScreen;

/**
 *
 * Created by 1541869 on 2016-08-30.
 */
public class Ramp extends GameObject implements SingleBodied, Renderable
{
	private Body body;

	public Ramp(GameScreen screen)
	{
		super(screen);
	}

	@Override
	public void render()
	{
		getBatch().draw(getAssets().getTexture("ramp.png"), body.getPosition().x - 10f, body.getPosition().y, 20f, 6f);
	}

	@Override
	public void spawn(World world, float spawnX, float spawnY)
	{
		if(body != null)
			return;

		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.StaticBody;
		def.position.x = spawnX;
		def.position.y = spawnY;

		body = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.set(new float[] {
				-10f, 0f,
				10f, 0f,
				10f, 6f,
		});

		body.createFixture(shape, 100f);

		shape.dispose();
	}

	@Override
	public void remove(World world)
	{
		if(body == null)
			return;

		world.destroyBody(body);
		body = null;
	}

	@Override
	public Vector2 getLocation()
	{
		return body.getPosition();
	}

	@Override
	public Body getMainBody()
	{
		return body;
	}

	@Override
	public float getPriority()
	{
		return LOW_PRIORITY;
	}
}
