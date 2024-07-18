package com.grimpirate;

public class JoystickEvent
{
	private final long time;
	private final JoystickEventMapping map;
	private final JoystickEventRunnable runnable;
	
	public JoystickEvent(long time, JoystickEventMapping map, JoystickEventRunnable runnable)
	{
		this.time = time;
		this.map = map;
		this.runnable = runnable;
	}
	
	public final long getTime()
	{
		return time;
	}
	
	public final JoystickEventMapping getMap()
	{
		return map;
	}
	
	public final JoystickEventRunnable getRunnable()
	{
		return runnable;
	}
}
