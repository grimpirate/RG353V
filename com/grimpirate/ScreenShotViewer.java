package com.grimpirate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ScreenShotViewer
{
	static
	{
		tty();
	}
	
	public static void tty()
	{
		try
		{
			final PrintStream stream = new PrintStream(new FileOutputStream(Paths.get("/dev/tty0").toFile()), true, StandardCharsets.UTF_8);
			System.setOut(stream);
			System.setErr(stream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new ScreenShotViewer();
		System.exit(0);
	}
	
	private int current = 0;
	
	public ScreenShotViewer() throws Exception
	{
		Path[] screenshots = Files.list(Paths.get("/userdata/roms/anbernic/screenshots"))
				.filter(path -> !Files.isDirectory(path) && path.toString().endsWith(".png"))
				.toArray(Path[]::new);
		FrameBuffer fb = FrameBuffer.getInstance();
		new JoystickExecutor() {

			@Override
			public void onInput(JoystickEvent event, JoystickEventRunnable queue)
			{
				JoystickEventMapping map = event.getMap();
				if(map == JoystickEventMapping.FUNCTION && map.getValue() == JoystickEventValue.PRESSED)
					queue.systemHalt();
				if(
						map == JoystickEventMapping.LEFT && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.SELECT && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.X && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.Y && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.UP && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.L1 && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.L2 && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.LEFT_X_AXIS && map.getValue().getValue() < 0
						|| map == JoystickEventMapping.RIGHT_X_AXIS && map.getValue().getValue() < 0
						|| map == JoystickEventMapping.LEFT_Y_AXIS && map.getValue().getValue() < 0
						|| map == JoystickEventMapping.RIGHT_Y_AXIS && map.getValue().getValue() < 0
						)
				{
					if(screenshots.length > 0)
						drawImage(fb, screenshots[current = current == 0 ? screenshots.length - 1 : current - 1]);
				}
				if(
						map == JoystickEventMapping.RIGHT && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.START && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.B && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.A && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.DOWN && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.R1 && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.R2 && map.getValue() == JoystickEventValue.PRESSED
						|| map == JoystickEventMapping.LEFT_X_AXIS && map.getValue().getValue() > 0
						|| map == JoystickEventMapping.RIGHT_X_AXIS && map.getValue().getValue() > 0
						|| map == JoystickEventMapping.LEFT_Y_AXIS && map.getValue().getValue() > 0
						|| map == JoystickEventMapping.RIGHT_Y_AXIS && map.getValue().getValue() > 0
						)
				{
					if(screenshots.length > 0)
						drawImage(fb, screenshots[current = current == screenshots.length - 1 ? 0 : current + 1]);
				}
			}

		};
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
			buffer.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
