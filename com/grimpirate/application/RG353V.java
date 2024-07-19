package com.grimpirate.application;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.grimpirate.input.JoystickEvent;
import com.grimpirate.input.JoystickQueue;

abstract public class RG353V implements PropertyChangeListener
{	
	static
	{
		tty();
	}
	
	public static void tty()
	{
		try
		{
			final PrintStream stream = new PrintStream(new FileOutputStream(Paths.get("/dev/tty0").toFile()), true, StandardCharsets.UTF_8);
			System.setOut(stream);
			System.setErr(stream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public RG353V()
	{
		JoystickQueue queue = JoystickQueue.getInstance();
		queue.addPropertyChangeListener(this);
		final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		final ScheduledFuture<?> future = executor.schedule(queue, 0, TimeUnit.SECONDS);
		while(true)
			if(future.isDone() || future.isCancelled())
				break;
		executor.shutdown();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event)
	{
		onInput((JoystickEvent)event.getNewValue());
	}
	
	abstract public void onInput(JoystickEvent event);
}
