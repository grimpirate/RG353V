package com.grimpirate;

import java.util.AbstractMap;
import java.util.Map;

public enum JoystickEventMapping
{
	B(JoystickEventValue.RELEASED),
	A(JoystickEventValue.RELEASED),
	X(JoystickEventValue.RELEASED),
	Y(JoystickEventValue.RELEASED),
	L1(JoystickEventValue.RELEASED),
	R1(JoystickEventValue.RELEASED),
	L2(JoystickEventValue.RELEASED),
	R2(JoystickEventValue.RELEASED),
	SELECT(JoystickEventValue.RELEASED),
	START(JoystickEventValue.RELEASED),
	FUNCTION(JoystickEventValue.RELEASED),
	L3(JoystickEventValue.RELEASED),
	R3(JoystickEventValue.RELEASED),
	UP(JoystickEventValue.RELEASED),
	DOWN(JoystickEventValue.RELEASED),
	LEFT(JoystickEventValue.RELEASED),
	RIGHT(JoystickEventValue.RELEASED),
	LEFT_X_AXIS(JoystickEventValue.AXIS),
	LEFT_Y_AXIS(JoystickEventValue.AXIS),
	RIGHT_X_AXIS(JoystickEventValue.AXIS),
	RIGHT_Y_AXIS(JoystickEventValue.AXIS);

	private final static Map<Integer, JoystickEventMapping> buttons = Map.ofEntries(
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(0, B),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(1, A),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(2, X),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(3, Y),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(4, L1),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(5, R1),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(6, L2),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(7, R2),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(8, SELECT),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(9, START),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(10, FUNCTION),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(11, L3),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(12, R3),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(13, UP),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(14, DOWN),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(15, LEFT),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(16, RIGHT)
			);

	private final static Map<Integer, JoystickEventMapping> axes = Map.ofEntries(
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(0, LEFT_X_AXIS),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(1, LEFT_Y_AXIS),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(2, RIGHT_X_AXIS),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickEventMapping>(3, RIGHT_Y_AXIS)
			);

	private JoystickEventValue value;

	JoystickEventMapping(JoystickEventValue value)
	{
		this.value = value;
	}

	public JoystickEventValue getValue()
	{
		return value;
	}
	
	public void setValue(JoystickEventValue value)
	{
		this.value = value;
	}

	public static JoystickEventMapping valueOf(int number, JoystickEventValue value)
	{
		JoystickEventMapping temp = buttons.get(number);
		if(value == JoystickEventValue.AXIS)
			temp = axes.get(number);
		temp.setValue(value);
		return temp;
	}
}
