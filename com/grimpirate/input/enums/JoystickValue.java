package com.grimpirate.input.enums;

import java.util.AbstractMap;
import java.util.Map;

public enum JoystickValue {
	INIT(0),
	RELEASED(0),
	PRESSED(1),
	AXIS(0);
	
	private final static Map<Integer, JoystickValue> map = Map.ofEntries(
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickValue>(0, RELEASED),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickValue>(1, PRESSED)
			);
	
	private int value;
	
	JoystickValue(int value)
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
	
	public static JoystickValue valueOf(int value, JoystickType type)
	{
		if(type == JoystickType.INIT)
			return INIT;
		if(type == JoystickType.BUTTON)
			return map.get(value);
		JoystickValue temp = AXIS;
		temp.setValue(value);
		return temp;
	}
}
