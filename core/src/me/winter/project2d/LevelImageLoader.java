package me.winter.project2d;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.Array;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Created by 1541869 on 2016-09-08.
 */
public class LevelImageLoader extends AsynchronousAssetLoader<LevelImage, LevelImageLoader.LevelParameter>
{
	private LevelImage value;

	public LevelImageLoader(FileHandleResolver resolver)
	{
		super(resolver);
		this.value = null;
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, LevelParameter parameter)
	{
		try
		{
			if(parameter != null && parameter.isTextual())
				value = new LevelImage(fileName, new String(Files.readAllBytes(file.file().toPath()), StandardCharsets.UTF_8));
			else
				value = new LevelImage(fileName, TextureData.Factory.loadFromFile(file, null, false));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			value = new LevelImage("Failed to load", "X X\n X \nX X");
		}
	}

	@Override
	public LevelImage loadSync(AssetManager manager, String fileName, FileHandle file, LevelParameter parameter)
	{
		loadAsync(manager, fileName, file, parameter);
		return value;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, LevelParameter parameter)
	{
		return null;
	}

	public static class LevelParameter extends AssetLoaderParameters<LevelImage>
	{
		private boolean textual = false;

		public LevelParameter(boolean textual)
		{
			this.textual = textual;
		}

		public boolean isTextual()
		{
			return textual;
		}

		public void setTextual(boolean textual)
		{
			this.textual = textual;
		}
	}
}
