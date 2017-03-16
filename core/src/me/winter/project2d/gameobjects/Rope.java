package me.winter.project2d.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import me.winter.project2d.GameScreen;
import me.winter.project2d.util.Box2DUtil;

/**
 * Created by 1541869 on 2016-08-31.
 */
public class Rope extends GameObject implements Renderable, Physical
{
	private Body[] bodies;
	private Joint[] joints;

	private int parts;

	public Rope(GameScreen screen, int parts)
	{
		super(screen);
		this.parts = parts;
	}

	@Override
	public void spawn(World world, float spawnX, float spawnY)
	{
		this.bodies = new Body[parts];

		PolygonShape segment = new PolygonShape();
		segment.set(new float[]{
				-0.2f, -2f,
				0.2f, -2f,
				0.2f, 0f,
				-0.2f, 0f,
		});

		for(int i = 0; i < parts; i++)
		{
			BodyDef def = new BodyDef();
			def.type = BodyDef.BodyType.DynamicBody;
			def.position.x = spawnX;
			def.position.y = spawnY - i * 3f;

			bodies[i] = world.createBody(def);
			bodies[i].createFixture(Box2DUtil.newFixtureDef(segment, 0.5f, 0.5f, 1f));

			if(i == 0)
				continue;

			RopeJointDef jointDef = new RopeJointDef();
			jointDef.bodyA = bodies[i];
			jointDef.bodyB =  bodies[i - 1];

			jointDef.localAnchorA.x = 0f;
			jointDef.localAnchorA.y = -2f;

			jointDef.localAnchorB.x = 0f;
			jointDef.localAnchorB.y = 0f;

			world.createJoint(jointDef);
		}

		segment.dispose();
	}

	@Override
	public void remove(World world)
	{
		if(this.bodies == null)
			return;

		for(Body body : bodies)
			world.destroyBody(body);

		for(Joint joint : joints)
			world.destroyJoint(joint);
	}

	@Override
	public void render()
	{
		Texture texture = getAssets().getTexture("rope.png").getTexture();
		TextureRegion textureRegion = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getWidth() * 5);

		for(Body body : bodies)
		{
			getScreen().getSpriteBatch().draw(textureRegion,
					body.getPosition().x - 0.2f, body.getPosition().y,
					0.2f, 0f,
					0.4f, 2f,
					1f, 1f,
					(float)Math.toDegrees(body.getAngle()) + 180);
		}
	}

	@Override
	public Vector2 getLocation()
	{
		return null;
	}

	/**
	 * @return The first rope segment, the one completly on top when spawned
	 */
	@Override
	public Body getMainBody()
	{
		return bodies[0];
	}

	@Override
	public Body[] getBodies()
	{
		return this.bodies;
	}

	@Override
	public Joint[] getJoints()
	{
		return this.joints;
	}

}

