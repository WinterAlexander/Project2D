package me.winter.project2d.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import me.winter.project2d.GameScreen;
import me.winter.project2d.util.Box2DUtil;

/**
 * <p>Undocumented :(</p>
 *
 * <p>Created by Alexander Winter on 2016-08-28.</p>
 */
public class Car extends GameObject implements Physical, Renderable, Controllable
{
	private Body[] bodies;
	private Joint[] joints;

	public Car(GameScreen screen)
	{
		super(screen);
		this.bodies = null;
	}

	/**
	 * Renders the car
	 */
	@Override
	public void render()
	{
		TextureRegion wheel = getAssets().getTexture("wheel.png");
		float lwRadius = bodies[1].getFixtureList().get(0).getShape().getRadius();

		getScreen().getSpriteBatch().draw(wheel.getTexture(),
				bodies[1].getPosition().x - lwRadius, bodies[1].getPosition().y - lwRadius,
				lwRadius, lwRadius,
				lwRadius * 2, lwRadius * 2,
				1f, 1f,
				(float)Math.toDegrees(bodies[1].getAngle()),
				0, 0,
				wheel.getTexture().getWidth(), wheel.getTexture().getHeight(),
				false, false);

		float rwRadius = bodies[2].getFixtureList().get(0).getShape().getRadius();

		getScreen().getSpriteBatch().draw(wheel.getTexture(),
				bodies[2].getPosition().x - rwRadius, bodies[2].getPosition().y - rwRadius,
				rwRadius, rwRadius,
				rwRadius * 2, rwRadius * 2,
				1f, 1f,
				(float)Math.toDegrees(bodies[2].getAngle()),
				0, 0,
				wheel.getTexture().getWidth(), wheel.getTexture().getHeight(),
				false, false);

		TextureRegion hull = getAssets().getTexture("hull.png");

		getScreen().getSpriteBatch().draw(hull.getTexture(),
				bodies[0].getPosition().x - 6f, bodies[0].getPosition().y,
				6f, 0f,
				12f, 5f,
				1f, 1f,
				(float)Math.toDegrees(bodies[0].getAngle()),
				0, 0,
				hull.getTexture().getWidth(), hull.getTexture().getHeight(),
				false, false);
	}

	@Override
	public void spawn(World world, float spawnX, float spawnY)
	{
		if(this.bodies != null)
			remove(world);

		this.bodies = new Body[3];
		this.joints = new Joint[2];

		BodyDef hull = new BodyDef();
		hull.type = BodyDef.BodyType.DynamicBody;
		hull.position.x = spawnX;
		hull.position.y = spawnY;

		this.bodies[0] = world.createBody(hull);

		//hull, the car "body"

		PolygonShape hullShape1 = new PolygonShape();
		hullShape1.set(new float[]{
				-6f, 0f,
				6f, 0f,
				6f, 2.6f,
				4f, 3f,
				-6f, 3f
		});
		this.bodies[0].createFixture(Box2DUtil.newFixtureDef(hullShape1, 0.9f, 0f, 10f));

		hullShape1.dispose();

		PolygonShape hullShape2 = new PolygonShape();
		hullShape2.set(new float[]{
				-6f, 3f,
				4f, 3f,
				3.2f, 5f,
				-5.6f, 5f
		});

		this.bodies[0].createFixture(Box2DUtil.newFixtureDef(hullShape2, 0.9f, 0f, 10f));

		hullShape2.dispose();

		//left wheel

		BodyDef leftWheelBody = new BodyDef();
		leftWheelBody.type = BodyDef.BodyType.DynamicBody;
		leftWheelBody.position.x = spawnX - 4f;
		leftWheelBody.position.y = spawnY - 1f;

		this.bodies[1] = world.createBody(leftWheelBody);

		CircleShape leftWheel = new CircleShape();
		leftWheel.setRadius(1.5f);

		this.bodies[1].createFixture(Box2DUtil.newFixtureDef(leftWheel, 1f, 0.1f, 8f));

		leftWheel.dispose();

		//right wheel

		BodyDef rightWheelBody = new BodyDef();
		rightWheelBody.type = BodyDef.BodyType.DynamicBody;
		rightWheelBody.position.x = spawnX + 4f;
		rightWheelBody.position.y = spawnY - 1f;

		this.bodies[2] = world.createBody(rightWheelBody);

		CircleShape rightWheel = new CircleShape();
		rightWheel.setRadius(1.5f);

		this.bodies[2].createFixture(Box2DUtil.newFixtureDef(rightWheel, 1f, 0.1f, 8f));

		rightWheel.dispose();

		RevoluteJointDef leftWheelJoint = new RevoluteJointDef();
		leftWheelJoint.initialize(this.bodies[0], this.bodies[1], leftWheelBody.position);

		this.joints[0] = world.createJoint(leftWheelJoint);

		RevoluteJointDef rightWheelJoint = new RevoluteJointDef();
		rightWheelJoint.initialize(this.bodies[0], this.bodies[2], rightWheelBody.position);

		this.joints[1] = world.createJoint(rightWheelJoint);
	}

	public void jump()
	{
		this.bodies[0].applyForceToCenter(0, 200_000f, true);
	}

	@Override
	public void moveLeft(float delta)
	{
		this.bodies[0].applyForceToCenter(-500_000f * delta, 0f, true);
	}

	@Override
	public void moveRight(float delta)
	{
		this.bodies[0].applyForceToCenter(500_000f * delta, 0f, true);
	}

	@Override
	public void remove(World world)
	{
		if(this.bodies != null)
			for(Body body : this.bodies)
				world.destroyBody(body);

		if(this.joints != null)
			for(Joint joint : this.joints)
				world.destroyJoint(joint);

		this.joints = null;
		this.bodies = null;
	}

	@Override
	public Body getMainBody()
	{
		return bodies[0];
	}

	@Override
	public Body[] getBodies()
	{
		return bodies;
	}

	@Override
	public Joint[] getJoints()
	{
		return joints;
	}

	@Override
	public Vector2 getLocation()
	{
		return this.bodies[0].getPosition();
	}
}
