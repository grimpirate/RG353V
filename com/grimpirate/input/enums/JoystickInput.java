package com.grimpirate.input.enums;

import java.util.AbstractMap;
import java.util.Map;

public enum JoystickInput
{
	B(JoystickValue.INIT),
	A(JoystickValue.INIT),
	X(JoystickValue.INIT),
	Y(JoystickValue.INIT),
	L1(JoystickValue.INIT),
	R1(JoystickValue.INIT),
	L2(JoystickValue.INIT),
	R2(JoystickValue.INIT),
	SELECT(JoystickValue.INIT),
	START(JoystickValue.INIT),
	FUNCTION(JoystickValue.INIT),
	L3(JoystickValue.INIT),
	R3(JoystickValue.INIT),
	UP(JoystickValue.INIT),
	DOWN(JoystickValue.INIT),
	LEFT(JoystickValue.INIT),
	RIGHT(JoystickValue.INIT),
	LEFT_X_AXIS(JoystickValue.INIT),
	LEFT_Y_AXIS(JoystickValue.INIT),
	RIGHT_X_AXIS(JoystickValue.INIT),
	RIGHT_Y_AXIS(JoystickValue.INIT);

	private final static Map<Integer, JoystickInput> buttons = Map.ofEntries(
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(0, B),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(1, A),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(2, X),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(3, Y),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(4, L1),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(5, R1),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(6, L2),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(7, R2),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(8, SELECT),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(9, START),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(10, FUNCTION),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(11, L3),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(12, R3),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(13, UP),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(14, DOWN),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(15, LEFT),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(16, RIGHT)
			);

	private final static Map<Integer, JoystickInput> axes = Map.ofEntries(
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(0, LEFT_X_AXIS),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(1, LEFT_Y_AXIS),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(2, RIGHT_X_AXIS),
			new AbstractMap.SimpleImmutableEntry<Integer, JoystickInput>(3, RIGHT_Y_AXIS)
			);

	private JoystickValue value;

	JoystickInput(JoystickValue value)
	{
		this.value = value;
	}

	public JoystickValue getValue()
	{
		return value;
	}
	
	public void setValue(JoystickValue value)
	{
		this.value = value;
	}

	public static JoystickInput valueOf(int number, JoystickValue value)
	{
		JoystickInput temp = buttons.get(number);
		if(value == JoystickValue.AXIS)
			temp = axes.get(number);
		temp.setValue(value);
		return temp;
	}
}
