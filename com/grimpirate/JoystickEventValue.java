package com.grimpirate;

import java.util.AbstractMap;
import java.util.Map;

public enum JoystickEventValue {
	RELEASED(0),
	PRESSED(1),
	AXIS(0);
	
	private final static Map<Integer, JoystickEventValue> map = Map.ofEntries(
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventValue>(0, RELEASED),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventValue>(1, PRESSED)
			);
	
	private int value;
	
	JoystickEventValue(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public static JoystickEventValue valueOf(int value, JoystickEventType type)
	{
		if(type == JoystickEventType.INIT)
			return RELEASED;
		if(type == JoystickEventType.BUTTON)
			return map.get(value);
		JoystickEventValue temp = AXIS;
		temp.setValue(value);
		return temp;
	}
}
