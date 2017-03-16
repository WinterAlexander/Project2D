package me.winter.project2d.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import me.winter.project2d.GameScreen;
import me.winter.project2d.util.Box2DUtil;

/**
 * <p>Undocumented :(</p>
 *
 * <p>Created by Alexander Winter on 2016-09-08.</p>
 */
public abstract class SimpleCreature extends GameObject implements Living, Controllable, SingleBodied
{
	private Body body;

	private float health;

	public SimpleCreature(GameScreen screen)
	{
		super(screen);
		this.health = 1f;
	}

	abstract float getWidth();
	abstract float getHeight();

	@Override
	public Vector2 getLocation()
	{
		return body.getPosition();
	}

	@Override
	public float getHealth()
	{
		return health;
	}

	protected void setHealth(float health)
	{
		this.health = health;
	}

	@Override
	public void hit(float attack, Vector2 knockback)
	{
		health -= attack * (1f - getDefense());
		body.applyForceToCenter(knockback.scl(attack * (1f - getDefense())), true);
	}

	@Override
	public void spawn(World world, float spawnX, float spawnY)
	{
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.position.x = spawnX;
		def.position.y = spawnY;
		def.fixedRotation = true;

		this.body = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.set(new float[] {
				getWidth() / -2, 0f,
				getWidth() / 2, 0f,
				getWidth() / 2, getHeight(),
				getWidth() / -2, getHeight(),
		});

		this.body.createFixture(Box2DUtil.newFixtureDef(shape, 0f, 0f, 10f));

		shape.dispose();
	}

	@Override
	public void moveLeft(float delta)
	{
		getMainBody().applyForceToCenter(delta * getMainBody().getMass() * -1_000f, 0f, true); // TODO: 2016-09-10 Tweak values
	}

	@Override
	public void moveRight(float delta)
	{
		getMainBody().applyForceToCenter(delta * getMainBody().getMass() * 1_000f, 0f, true); // TODO: 2016-09-10 Tweak values
	}

	@Override
	public void jump()
	{
		getMainBody().applyForceToCenter(0f, getMainBody().getMass() * 1_000f, true); // TODO: 2016-09-10 Tweak values
	}

	@Override
	public float getDefense()
	{
		return 0;
	}

	@Override
	public void remove(World world)
	{
		world.destroyBody(body);
		body = null;
	}

	@Override
	public Body getMainBody()
	{
		return body;
	}

	@Override
	public float getPriority()
	{
		return Renderable.MEDIUM_PRIORITY;
	}
}
