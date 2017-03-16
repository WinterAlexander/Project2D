package me.winter.project2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

/**
 * Represents the image of a level to be loaded and played.
 *
 * Created by 1541869 on 2016-09-08.
 */
public class LevelImage
{
	private String name;
	private int[][] ground;

	private final int full = Color.rgba8888(Color.BLACK);
	private final int mid = Color.rgba8888(new Color(0.5f, 0.5f, 0.5f, 1f));
	private final int empty = Color.rgba8888(new Color(1f, 1f, 1f, 1f));

	public LevelImage(String name, String textualImage)
	{
		String[] lines = textualImage.split("\n");

		this.name = name;
		ground = new int[lines.length][];

		for(int row = 0; row < lines.length; row++)
		{
			ground[lines.length - row - 1] = new int[lines[row].length()];

			for(int col = 0; col < lines[row].length(); col++)
				ground[lines.length - row - 1][col] = lines[row].charAt(col) == 'X' ? 1 : 0;
		}
	}

	public LevelImage(String name, TextureData visualImage)
	{
		this.name = name;
		this.ground = new int[visualImage.getHeight()][visualImage.getWidth()];
		visualImage.prepare();
		Pixmap map = visualImage.consumePixmap();

		for(int y = 0; y < map.getHeight(); y++)
		{
			for(int x = 0; x < map.getWidth(); x++)
			{
				ground[map.getHeight() - y - 1][x] = parseGround(map.getPixel(x, y));
			}
		}

		visualImage.disposePixmap();
	}

	/**
	 * Parses the pixel into a ground value used by the Ground object
	 * @param pixel pixel to parse
	 * @return a ground value
	 */
	private int parseGround(int pixel)
	{
		pixel |= 0x000000FF; //alpha to 255

		if(pixel == full)
			return 10;

		if(pixel == mid)
			return 5;

		if(pixel == empty)
			return 0;

		System.out.println("Warning, bad pixel on level");
		return 0;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int[][] getGround()
	{
		return ground;
	}

	public void setGround(int[][] ground)
	{
		this.ground = ground;
	}
}
