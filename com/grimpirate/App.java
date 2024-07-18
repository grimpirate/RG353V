package com.grimpirate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class App
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
	
	public static void main(String[] args)
	{
		new App();
		System.exit(0);
	}

	public App()
	{
		new JoystickExecutor(){
			@Override
			public void onInput(final JoystickEvent event, final JoystickEventRunnable queue)
			{
				final JoystickEventMapping map = event.getMap();
				System.out.println(map + ": " + (map.getValue() == JoystickEventValue.AXIS ? map.getValue().getValue() : map.getValue()));
				if(map == JoystickEventMapping.SELECT && map.getValue() == JoystickEventValue.PRESSED)
				{
					queue.systemHalt();
					FrameBuffer.clear();
				}
				if(map == JoystickEventMapping.START && map.getValue() == JoystickEventValue.PRESSED)
					FrameBuffer.clear();
				if(map == JoystickEventMapping.FUNCTION && map.getValue() == JoystickEventValue.PRESSED)
					drawRainbow();
				if(map == JoystickEventMapping.R1 && map.getValue() == JoystickEventValue.PRESSED)
					FrameBuffer.capture();
			}
		};
	}

	public static void drawRainbow()
	{
		final FrameBuffer buffer = FrameBuffer.getInstance();
		final Graphics2D g2d = buffer.createGraphics();
		final Rectangle dimensions = g2d.getClipBounds();
		int offset = 1;
		for(int i = 0; i < dimensions.width; i+=offset)
		{
			int red = ((i << 8) - i) / (dimensions.height - 1);
			for(int j = 0; j < dimensions.width; j+=offset)
			{
				int green = ((j << 8) - i) / (dimensions.width - 1);
				int blue = 128;
				g2d.setColor(new Color((red << 16) | (green << 8) | blue));
				g2d.fillRect(j, i, offset, offset);
			}
		}
		g2d.dispose();
		buffer.flush();
	}
}