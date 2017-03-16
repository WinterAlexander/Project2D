package me.winter.socialplatformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import sun.plugin.dom.exception.InvalidStateException;

/**
 * An AssetManager with extra methods to supply specific assets
 *
 * Created by 1541869 on 2016-09-02.
 */
public class AssetSupplier extends AssetManager
{
	private String indexFile;

	public AssetSupplier()
	{
		this(null);
	}

	public AssetSupplier(FileHandleResolver resolver, boolean defaultLoaders)
	{
		this(null, resolver, defaultLoaders);
	}

	public AssetSupplier(String indexFile)
	{
		super();
		this.indexFile = indexFile;
	}

	public AssetSupplier(String indexFile, FileHandleResolver resolver, boolean defaultLoaders)
	{
		super(resolver, defaultLoaders);
		this.indexFile = indexFile;
	}

	public void loadFrom(String indexFile)
	{
		setIndexFile(indexFile);
		loadFromIndexFile();
	}

	public void loadFromIndexFile()
	{
		if(this.indexFile == null)
			throw new InvalidStateException("Cannot load from index file : index file hasn't been defined");

		try
		{
			for(JsonValue value : new JsonReader().parse(Gdx.files.internal(indexFile)))
			{
				try
				{
					this.load(value.name(), Class.forName(value.asString()), paramsFor(value.asString()));
				}
				catch(IllegalStateException ex)
				{
					System.err.println("Invalid asset in index file, skipping it. Name: " + value.name() + " Value: " + value.toString());
				}
				catch(ClassNotFoundException ex)
				{
					System.err.println("Couldn't find class for asset. Name: " + value.name() + " Class: " + value.asString());
					ex.printStackTrace();
				}
				catch(Exception ex)
				{
					System.err.println("An unexpected exception occurred while in AssetSupplier load from index file.");
					ex.printStackTrace();
				}
			}
		}
		catch(Exception ex)
		{
			System.err.println("An unexpected exception occurred while in AssetSupplier parsing index file.");
			ex.printStackTrace();
		}
	}

	private AssetLoaderParameters paramsFor(String className)
	{
		switch(className)
		{
			case "com.badlogic.gdx.graphics.Texture":
				return new TextureLoader.TextureParameter();

			default:
				return null;
		}
	}

	public TextureRegion getTexture(String name)
	{
		return new TextureRegion(get("textures/" + name, Texture.class));
	}

	public TextureRegion getTexture(String name, int srcX, int srcY, int srcW, int srcH)
	{
		return new TextureRegion(get("textures/" + name, Texture.class), srcX, srcY, srcW, srcH);
	}

	public String getIndexFile()
	{
		return indexFile;
	}

	public void setIndexFile(String indexFile)
	{
		this.indexFile = indexFile;
	}
}
