package com.grimpirate.input;

import com.grimpirate.input.enums.JoystickInput;
import com.grimpirate.input.enums.JoystickType;
import com.grimpirate.input.enums.JoystickValue;

public class JoystickEvent
{
	private final long time;
	private final JoystickInput map;
	
	public JoystickEvent(int time, byte number, short value, byte type)
	{
		this.time = Integer.toUnsignedLong(time);
		final int _type = Byte.toUnsignedInt(type);
		final int _number = Byte.toUnsignedInt(number);
		map = JoystickInput.valueOf(_number, JoystickValue.valueOf(value, JoystickType.valueOf(_type)));
	}
	
	public long getTime()
	{
		return time;
	}
	
	public JoystickInput getMap()
	{
		return map;
	}
}
