package me.winter.project2d.gameobjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.javaws.exceptions.InvalidArgumentException;
import me.winter.project2d.GameScreen;
import me.winter.project2d.util.Box2DUtil;

import java.security.InvalidParameterException;


/**
 *
 *
 * Created by 1541869 on 2016-09-01.
 */
public class Ground extends GameObject implements Renderable, SingleBodied
{
	private Body body;

	private String level;
	private float tileSize;

	private static final int GROUND = 10;
	private static final int SLOPE = 5;
	private static final int AIR = 0;

	private TextureRegion center;
	private TextureRegion top, bottom, left, right;
	private TextureRegion topRightCorner, topLeftCorner, bottomRightCorner, bottomLeftCorner;
	private TextureRegion topRightInsideCorner, topLeftInsideCorner, bottomRightInsideCorner, bottomLeftInsideCorner;
	private TextureRegion slideLeft, slideRight, revSlideLeft, revSlideRight;

	/**
	 * Default constructor
	 * @param screen Screen instance
	 * @param level LevelImage name
	 * @param tileSize Size of a tile
	 */
	public Ground(GameScreen screen, String level, float tileSize)
	{
		super(screen);

		this.body = null;

		this.level = level;
		this.tileSize = tileSize;
	}

	@Override
	public void render()
	{
		for(int row = 0; row < getGround().length; row++)
		{
			for(int col = 0; col < getGround()[row].length; col++)
			{
				if(at(row, col) == AIR)
					continue;
				TextureRegion topLeft = null, topRight = null, bottomLeft = null, bottomRight = null;

				if(at(row, col) == SLOPE
				&&  (at(row - 1, col) == 10 ? 1 : 0) +
					(at(row + 1, col) == 10 ? 1 : 0) +
					(at(row, col - 1) == 10 ? 1 : 0) +
					(at(row, col + 1) == 10 ? 1 : 0) == 2)
				{
					if(at(row - 1, col) == GROUND && at(row, col - 1) == GROUND) //oriented top right
					{
						topLeft = slideRight;
						topRight = null;
						bottomLeft = topRightInsideCorner;
						bottomRight = slideRight;
					}
					else if(at(row - 1, col) == GROUND && at(row, col + 1) == GROUND) //oriented top left
					{
						topLeft = null;
						topRight = slideLeft;
						bottomLeft = slideLeft;
						bottomRight = topLeftInsideCorner;
					}
					else if(at(row + 1, col) == GROUND && at(row, col - 1) == GROUND) //oriented bottom right
					{
						topLeft = bottomRightInsideCorner;
						topRight = revSlideRight;
						bottomLeft = revSlideRight;
						bottomRight = null;
					}
					else if(at(row + 1, col) == GROUND && at(row, col + 1) == GROUND) //oriented bottom left
					{
						topLeft = revSlideLeft;
						topRight = bottomLeftInsideCorner;
						bottomLeft = null;
						bottomRight = revSlideLeft;
					}
				}
				else if(at(row, col) == GROUND)
				{
					topLeft = at(row, col - 1) == 0 ? at(row + 1, col) == 0 ? topLeftCorner : left : at(row + 1, col) == 0 ? top : at(row + 1, col - 1) == 0 ? topLeftInsideCorner : center;
					topRight = at(row, col + 1) == 0 ? at(row + 1, col) == 0 ? topRightCorner : right : at(row + 1, col) == 0 ? top : at(row + 1, col + 1) == 0 ? topRightInsideCorner : center;
					bottomLeft = at(row, col - 1) == 0 ? at(row - 1, col) == 0 ? bottomLeftCorner : left : at(row - 1, col) == 0 ? bottom : at(row - 1, col - 1) == 0 ? bottomLeftInsideCorner : center;
					bottomRight = at(row, col + 1) == 0 ? at(row - 1, col) == 0 ? bottomRightCorner : right : at(row - 1, col) == 0 ? bottom : at(row - 1, col + 1) == 0 ? bottomRightInsideCorner : center;
				}

				float baseX = col * tileSize;
				float baseY = row * tileSize;

				if(topLeft != null)
					getBatch().draw(topLeft, baseX, baseY + tileSize / 2, tileSize / 2, tileSize / 2);

				if(topRight != null)
					getBatch().draw(topRight, baseX + tileSize / 2, baseY + tileSize / 2, tileSize / 2, tileSize / 2);

				if(bottomLeft != null)
					getBatch().draw(bottomLeft, baseX, baseY, tileSize / 2, tileSize / 2);

				if(bottomRight != null)
					getBatch().draw(bottomRight, baseX + tileSize / 2, baseY, tileSize / 2, tileSize / 2);
			}
		}
	}

	private int at(int row, int col)
	{
		if(row < 0 || col < 0 || row >= getGround().length || col >= getGround()[row].length)
			return 0;

		return getGround()[row][col];
	}

	@Override
	public void spawn(World world, float locX, float locY)
	{
		BodyDef def = new BodyDef();
		def.position.x = locX;
		def.position.y = locY;

		body = world.createBody(def);

		int width = -1;

		for(int row = 0; row < getGround().length; row++)
		{
			if(getGround()[row].length > width)
				width = getGround()[row].length;

			for(int col = 0; col < getGround()[row].length; col++)
			{
				float[] vertices;

				if(at(row, col) == SLOPE
					&&  (at(row - 1, col) == 10 ? 1 : 0) +
						(at(row + 1, col) == 10 ? 1 : 0) +
						(at(row, col - 1) == 10 ? 1 : 0) +
						(at(row, col + 1) == 10 ? 1 : 0) == 2)
				{
					if(at(row - 1, col) == GROUND && at(row, col - 1) == GROUND) //oriented top right
					{
						vertices = new float[] {
								col * tileSize,         row * tileSize,
								(col + 1) * tileSize,   row * tileSize,
								col * tileSize,         (row + 1) * tileSize,
						};
					}
					else if(at(row - 1, col) == GROUND && at(row, col + 1) == GROUND) //oriented top left
					{
						vertices = new float[] {
								col * tileSize,         row * tileSize,
								(col + 1) * tileSize,   row * tileSize,
								(col + 1) * tileSize,   (row + 1) * tileSize,
						};
					}
					else if(at(row + 1, col) == GROUND && at(row, col - 1) == GROUND) //oriented bottom right
					{
						vertices = new float[] {
								col * tileSize,         row * tileSize,
								(col + 1) * tileSize,   (row + 1) * tileSize,
								col * tileSize,         (row + 1) * tileSize,
						};
					}
					else if(at(row + 1, col) == GROUND && at(row, col + 1) == GROUND) //oriented bottom left
					{
						vertices = new float[] {
								(col + 1) * tileSize,   row * tileSize,
								(col + 1) * tileSize,   (row + 1) * tileSize,
								col * tileSize,         (row + 1) * tileSize,
						};
					}
					else
						continue;
				}
				else if(at(row, col) == GROUND)
				{
					vertices = new float[] {
							col * tileSize,         row * tileSize,
							(col + 1) * tileSize,   row * tileSize,
							(col + 1) * tileSize,   (row + 1) * tileSize,
							col * tileSize,         (row + 1) * tileSize
					};
				}
				else
					continue;

				PolygonShape shape = new PolygonShape();
				shape.set(vertices);

				body.createFixture(Box2DUtil.newFixtureDef(shape, 1f, 0f, 0f));

				shape.dispose();
			}
		}

		getScreen().getCamera().setBounds(0, 0, width * tileSize, getGround().length * tileSize);
		loadTextures();
	}

	private void loadTextures()
	{
		top = getAssets().getTexture("ground.png", 70, 0, 70, 70);
		center = getAssets().getTexture("ground.png", 70, 70, 70, 70);
		bottom = getAssets().getTexture("ground.png", 70, 140, 70, 70);

		topRightCorner = getAssets().getTexture("ground.png", 140, 0, 70, 70);
		right = getAssets().getTexture("ground.png", 140, 70, 70, 70);
		bottomRightCorner = getAssets().getTexture("ground.png", 140, 140, 70, 70);

		topLeftCorner = getAssets().getTexture("ground.png", 0, 0, 70, 70);
		left = getAssets().getTexture("ground.png", 0, 70, 70, 70);
		bottomLeftCorner = getAssets().getTexture("ground.png", 0, 140, 70, 70);

		topLeftInsideCorner = getAssets().getTexture("ground.png", 0, 280, 70, 70);
		topRightInsideCorner = getAssets().getTexture("ground.png", 70, 280, 70, 70);
		bottomLeftInsideCorner = getAssets().getTexture("ground.png", 140, 210, 70, 70);
		bottomRightInsideCorner = getAssets().getTexture("ground.png", 210, 210, 70, 70);

		slideLeft = getAssets().getTexture("ground.png", 0, 210, 70, 70);
		slideRight = getAssets().getTexture("ground.png", 70, 210, 70, 70);
		revSlideLeft = getAssets().getTexture("ground.png", 140, 280, 70, 70);
		revSlideRight = getAssets().getTexture("ground.png", 210, 280, 70, 70);

	}

	@Override
	public void remove(World world)
	{

	}

	private int[][] getGround()
	{
		return getAssets().getLevel(level).getGround();
	}

	@Override
	public Vector2 getLocation()
	{
		return Vector2.Zero;
	}

	@Override
	public Body getMainBody()
	{
		return body;
	}

	@Override
	public Joint[] getJoints()
	{
		return null;
	}

	@Override
	public float getPriority()
	{
		return LOW_PRIORITY;
	}
}
