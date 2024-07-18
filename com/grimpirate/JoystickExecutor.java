package com.grimpirate;

import java.beans.PropertyChangeListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class JoystickExecutor
{	
	public JoystickExecutor(PropertyChangeListener listener)
	{
		JoystickEventRunnable queue = JoystickEventRunnable.getInstance();
		queue.addPropertyChangeListener(listener);
		final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		final ScheduledFuture<?> future = executor.schedule(queue, 0, TimeUnit.SECONDS);
		while(true)
			if(future.isDone() || future.isCancelled())
				break;
		executor.shutdown();
	}
}
