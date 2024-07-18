package com.grimpirate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

abstract class JoystickExecutor implements PropertyChangeListener
{
	public static final JoystickEventRunnable queue = JoystickEventRunnable.getInstance();
	
	public JoystickExecutor()
	{
		queue.addPropertyChangeListener(this);
		final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		final ScheduledFuture<?> future = executor.schedule(queue, 0, TimeUnit.SECONDS);
		while(true)
			if(future.isDone() || future.isCancelled())
				break;
		executor.shutdown();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		onInput((JoystickEvent)evt.getNewValue(), queue);
	}

	abstract public void onInput(JoystickEvent evt, JoystickEventRunnable run);
}
