package com.grimpirate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.grimpirate.application.RG353V;
import com.grimpirate.display.FrameBuffer;
import com.grimpirate.input.JoystickEvent;
import com.grimpirate.input.JoystickQueue;
import com.grimpirate.input.enums.JoystickInput;
import com.grimpirate.input.enums.JoystickValue;

public class App extends RG353V
{	
	public static void main(String[] args)
	{
		new App();
		System.exit(0);
	}
	
	public App()
	{
		super();
	}

	@Override
	public void onInput(JoystickEvent event)
	{
		final JoystickInput map = event.getMap();
		System.out.println(map + ": " + (map.getValue() == JoystickValue.AXIS ? map.getValue().getValue() : map.getValue()));
		if(map == JoystickInput.SELECT && map.getValue() == JoystickValue.PRESSED)
		{
			JoystickQueue.getInstance().systemHalt();
			FrameBuffer.clear();
		}
		if(map == JoystickInput.START && map.getValue() == JoystickValue.PRESSED)
			FrameBuffer.clear();
		if(map == JoystickInput.FUNCTION && map.getValue() == JoystickValue.PRESSED)
			drawRainbow();
		if(map == JoystickInput.R1 && map.getValue() == JoystickValue.PRESSED)
			FrameBuffer.capture();
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