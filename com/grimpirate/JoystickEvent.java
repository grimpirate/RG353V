package com.grimpirate;

public class JoystickEvent
{
	private final long time;
	private final JoystickEventMapping map;
	
	public JoystickEvent(long time, JoystickEventMapping map)
	{
		this.time = time;
		this.map = map;
	}
	
	public long getTime()
	{
		return time;
	}
	
	public JoystickEventMapping getMap()
	{
		return map;
	}
}
