package com.grimpirate;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

abstract class RG353V
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
}
