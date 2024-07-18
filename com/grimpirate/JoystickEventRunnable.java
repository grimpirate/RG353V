package com.grimpirate;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JoystickEventRunnable implements Runnable
{
	private boolean poll = true;
	private JoystickEvent oldValue;
	
	private PropertyChangeSupport support;
	
	private JoystickEventRunnable()
	{
		support = new PropertyChangeSupport(this);
	}
	
	private static class SingletonHelper {
		private static final JoystickEventRunnable INSTANCE = new JoystickEventRunnable();
	}
	
	public static JoystickEventRunnable getInstance()
	{
		return SingletonHelper.INSTANCE;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		support.removePropertyChangeListener(listener);
	}
	
	public void notifyObservers(int time, short value, byte type, byte number)
	{
		final long _time = Integer.toUnsignedLong(time);
		final int _type = Byte.toUnsignedInt(type);
		final int _number = Byte.toUnsignedInt(number);
		final JoystickEvent newValue = new JoystickEvent(_time, JoystickEventMapping.valueOf(_number, JoystickEventValue.valueOf(value, JoystickEventType.valueOf(_type))), this);
		support.firePropertyChange("JoystickEvent", oldValue, newValue);
		oldValue = newValue;
	}
	
	public void systemHalt()
	{
		poll = false;
	}
	
	@Override
	public void run()
	{
		try(final ReadableByteChannel channel = Files.newByteChannel(Paths.get("/dev/input/js0"), StandardOpenOption.READ)) {
			final ByteBuffer buffer = ByteBuffer.allocateDirect(8);
			buffer.order(ByteOrder.nativeOrder());
			while(poll)
			{
				channel.read(buffer);
				buffer.flip();
				notifyObservers(buffer.getInt(), buffer.getShort(), buffer.get(), buffer.get());
				buffer.rewind();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
