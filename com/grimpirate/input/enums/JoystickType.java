package com.grimpirate.input.enums;

import java.util.AbstractMap;
import java.util.Map;

public enum JoystickType {
	BUTTON,
	AXIS,
	INIT;
	
	private final static Map<Integer, JoystickType> map = Map.ofEntries(
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickType>(1, BUTTON),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickType>(2, AXIS),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickType>(128, INIT),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickType>(129, INIT)
			);
	
	public static JoystickType valueOf(int type)
	{
		return map.get(type);
	}
}
