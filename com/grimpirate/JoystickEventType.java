package com.grimpirate;

import java.util.AbstractMap;
import java.util.Map;

public enum JoystickEventType {
	BUTTON,
	AXIS,
	INIT;
	
	private final static Map<Integer, JoystickEventType> map = Map.ofEntries(
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventType>(1, BUTTON),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventType>(2, AXIS),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventType>(128, INIT),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventType>(129, INIT)
			);
	
	public static JoystickEventType valueOf(int type)
	{
		return map.get(type);
	}
}
