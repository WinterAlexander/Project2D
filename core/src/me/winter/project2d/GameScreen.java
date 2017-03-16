package me.winter.project2d;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import me.winter.project2d.gameobjects.*;


/**
 * <p>Undocumented :(</p>
 *
 * <p>Created by winter on 19/05/16.</p>
 */
public class GameScreen implements Screen
{
	private SmoothCamera camera;

	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private AssetSupplier assetManager;
	private boolean finishedLoading;

	private World world;
	private float accumulator = 0;
	private boolean debugRender = false;

	private Body groundBody;

	private Array<GameObject> gameObjects = new Array<>();
	private Array<Renderable> tmpRenderArray = new Array<>();

	public GameScreen()
	{
		camera = new SmoothCamera();
		camera.setToOrtho(false, 80, 45);
		camera.zoom = 0.5f;
		camera.setMouseVision(true);

		batch = new SpriteBatch();
		finishedLoading = false;
		assetManager = new AssetSupplier();
		assetManager.setLoader(LevelImage.class, new LevelImageLoader(assetManager.getFileHandleResolver()));

		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
	}

	/**
	 * <p>Called just before application is shown</p>
	 *
	 */
	@Override
	public void show()
	{
		Box2D.init();
		Gdx.input.setInputProcessor(new GameInput(this));

		assetManager.loadFrom("index.json");
		assetManager.finishLoading();

		Ground ground = new Ground(this, "level2.lvl.png", 5f);
		ground.spawn(world, 0f, 0f);
		gameObjects.add(ground);
		groundBody = ground.getBodies()[0];

		Human human = new Human(this);
		human.spawn(world, 130f, 300f);
		gameObjects.add(human);
		camera.setTarget(human);
		getGameInput().setControlled(human);

		Rope rope = new Rope(this, 50);
		rope.spawn(world, 150f, 500f);
		gameObjects.add(rope);

		gameObjects.add(new Background(this, "background.jpg"));
		tmpRenderArray.setSize(gameObjects.size);

		camera.update();
		finishedLoading = true;
	}

	@Override
	public void render(float delta)
	{
		if(!finishedLoading)
			return;

		camera.update();

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		int i = 0;
		for(GameObject object : gameObjects)
			if(object instanceof Renderable)
				tmpRenderArray.set(i++, (Renderable)object);

		tmpRenderArray.sort(Renderable.PRIORITY_COMPARATOR);

		for(; i --> 0 ;)
			tmpRenderArray.get(i).render();

		batch.end();

		if(debugRender)
			debugRenderer.render(world, camera.combined);

		getGameInput().update(delta);

		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		accumulator += Math.min(Gdx.graphics.getDeltaTime(), 0.25f);
		while(accumulator >= 1 / 60f)
		{
			world.step(1 / 60f, 6, 2);
			accumulator -= 1 / 60f;
		}
	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void hide()
	{

	}

	@Override
	public void dispose()
	{
		world.dispose();
		batch.dispose();
	}

	public SmoothCamera getCamera()
	{
		return camera;
	}

	public World getWorld()
	{
		return world;
	}

	public Box2DDebugRenderer getDebugRenderer()
	{
		return debugRenderer;
	}

	public SpriteBatch getSpriteBatch()
	{
		return batch;
	}

	public AssetSupplier getAssetManager()
	{
		return assetManager;
	}

	public boolean isDebugRender()
	{
		return debugRender;
	}

	public void setDebugRender(boolean debugRender)
	{
		this.debugRender = debugRender;
	}

	public GameInput getGameInput()
	{
		if(Gdx.input.getInputProcessor() == null)
			return null;

		return (GameInput)Gdx.input.getInputProcessor();
	}

	public Body getGroundBody()
	{
		return groundBody;
	}

	public GameObject getCameraTarget()
	{
		return camera.getTarget();
	}

	public void setCameraTarget(GameObject cameraTarget)
	{
		this.camera.setTarget(cameraTarget);
	}
}
