package com.grimpirate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class App extends RG353V
{	
	public static void main(String[] args)
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
		System.exit(0);
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