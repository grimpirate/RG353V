package com.grimpirate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class App implements PropertyChangeListener
{	
	public App()
	{
		new JoystickExecutor(this);
	}

	static
	{
		tty();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		JoystickEvent event = (JoystickEvent) evt.getNewValue();
		JoystickEventMapping map = event.getMap();
		System.out.println(map + ": " + (map.getValue() == JoystickEventValue.AXIS ? map.getValue().getValue() : map.getValue()));
		if(map == JoystickEventMapping.SELECT && map.getValue() == JoystickEventValue.PRESSED)
			event.getRunnable().systemHalt();
		if(map == JoystickEventMapping.START && map.getValue() == JoystickEventValue.PRESSED)
			clear();
		if(map == JoystickEventMapping.FUNCTION && map.getValue() == JoystickEventValue.PRESSED)
			drawRainbow();
	}

	public static void main(String[] args)
	{
		new App();
		System.exit(0);
	}

	public static void tty()
	{
		try
		{
			final File file = Paths.get("/dev/tty0").toFile();
			final FileOutputStream output = new FileOutputStream(file);
			final PrintStream stream = new PrintStream(output, true, StandardCharsets.UTF_8);
			System.setOut(stream);
			System.setErr(stream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void clear()
	{
		System.out.print("\033\143");
	}
	
	public static void drawRainbow()
	{
		try
		{
			final FrameBuffer buffer = new FrameBuffer();
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
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
