package com.grimpirate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.grimpirate.application.Skeleton;
import com.grimpirate.display.FrameBuffer;
import com.grimpirate.input.JoystickEvent;
import com.grimpirate.input.enums.JoystickInput;
import com.grimpirate.input.enums.JoystickValue;

public class ScreenShotViewer extends Skeleton
{	
	public static void main(String[] args) throws Exception
	{
		try
		{
			screenshots = Files.list(Paths.get("/userdata/roms/anbernic/screenshots"))
					.filter(path -> !Files.isDirectory(path) && path.toString().endsWith(".png"))
					.toArray(Path[]::new);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		new ScreenShotViewer();
	}
	
	private int current = 0;
	private static Path[] screenshots = new Path[0];
	
	@Override
	public void onInput(JoystickEvent event)
	{
		JoystickInput map = event.getMap();
		if(map == JoystickInput.FUNCTION && map.getValue() == JoystickValue.PRESSED)
			exit();
		if(
				map == JoystickInput.LEFT && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.SELECT && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.X && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.Y && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.UP && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.L1 && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.L2 && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.LEFT_X_AXIS && map.getValue().getValue() < 0
				|| map == JoystickInput.RIGHT_X_AXIS && map.getValue().getValue() < 0
				|| map == JoystickInput.LEFT_Y_AXIS && map.getValue().getValue() < 0
				|| map == JoystickInput.RIGHT_Y_AXIS && map.getValue().getValue() < 0
				)
		{
			if(screenshots.length > 0)
				drawImage(FrameBuffer.getInstance(), screenshots[current = current == 0 ? screenshots.length - 1 : current - 1]);
		}
		if(
				map == JoystickInput.RIGHT && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.START && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.B && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.A && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.DOWN && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.R1 && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.R2 && map.getValue() == JoystickValue.PRESSED
				|| map == JoystickInput.LEFT_X_AXIS && map.getValue().getValue() > 0
				|| map == JoystickInput.RIGHT_X_AXIS && map.getValue().getValue() > 0
				|| map == JoystickInput.LEFT_Y_AXIS && map.getValue().getValue() > 0
				|| map == JoystickInput.RIGHT_Y_AXIS && map.getValue().getValue() > 0
				)
		{
			if(screenshots.length > 0)
				drawImage(FrameBuffer.getInstance(), screenshots[current = current == screenshots.length - 1 ? 0 : current + 1]);
		}
	}

	public static void drawImage(FrameBuffer buffer, Path path)
	{
		try
		{
			BufferedImage image = ImageIO.read(path.toFile());
			Graphics2D g2d = buffer.createGraphics();
			Rectangle clip = g2d.getClipBounds();
			double h = clip.getWidth() / image.getWidth();
			double v = clip.getHeight() / image.getHeight();
			double scale = h < v ? h : v;
			g2d.drawImage(image, AffineTransform.getScaleInstance(scale, scale), null);
			g2d.dispose();
			buffer.flush(0, 0, image.getWidth(), image.getHeight());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
