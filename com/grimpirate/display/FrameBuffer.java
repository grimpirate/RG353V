package com.grimpirate.display;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public class FrameBuffer
{
	private enum Device {
		RG353V;
	}
	
	public static final Path DEVICE_PATH = Paths.get("/dev/fb0");

	private DataBufferByte bytes;
	private BufferedImage image;
	private int STRIDE;
	private int BYTES_PER_PIXEL;
	private FileChannel channel;

	private FrameBuffer()
	{
		try
		{
			channel = FileChannel.open(DEVICE_PATH, StandardOpenOption.WRITE);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		switch(Device.valueOf(System.getProperty("com.grimpirate.device")))
		{
		default:
			// Default values for ANBERNIC RG353V
			STRIDE = 2560;
			BYTES_PER_PIXEL = 4;
			image = new BufferedImage(new ComponentColorModel(
					ColorSpace.getInstance(ColorSpace.CS_sRGB),
					new int[] {8,8,8,8},	// Bits per channel
					true,
					false,
					Transparency.OPAQUE,
					DataBuffer.TYPE_BYTE),
					Raster.createInterleavedRaster(
							bytes = new DataBufferByte(1228800),
							640,
							480,
							STRIDE,
							BYTES_PER_PIXEL,
							new int[] {2,1,0,3},	// BGRA channel offsets
							null),
					false,
					null);
			break;
		}
	}

	private static class SingletonHelper {
		private static final FrameBuffer INSTANCE = new FrameBuffer();
	}

	public static FrameBuffer getInstance()
	{
		return SingletonHelper.INSTANCE;
	}

	public Graphics2D createGraphics()
	{
		final Graphics2D g2d = image.createGraphics();
		g2d.setClip(0, 0, image.getWidth(), image.getHeight());
		return g2d;
	}

	public void flush(int x, int y, int width, int height)
	{
		try
		{
			int offset = y * STRIDE + x * BYTES_PER_PIXEL;
			final int stride = width * BYTES_PER_PIXEL;
			ByteBuffer partialBuffer = ByteBuffer.allocate(stride);
			for(int i = 0; i < height; i++)
			{
				partialBuffer.put(bytes.getData(), offset, stride);
				partialBuffer.flip();
				channel.write(partialBuffer, offset);
				partialBuffer.rewind();
				offset += STRIDE;
			}
			channel.force(false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void flush()
	{
		flush(0, 0, image.getWidth(), image.getHeight());
	}

	public static void capture()
	{
		try
		{
			final ProcessBuilder builder = new ProcessBuilder();
			builder.command("sh", "-c", "fbgrab " + UUID.randomUUID() + ".png");
			builder.directory(Paths.get("/userdata/roms/anbernic/screenshots").toFile());
			final Process process = builder.start();
			process.waitFor();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		/*
		try
		{
			final BufferedImage capture = new BufferedImage(
					image.getColorModel(),
					image.copyData(null),
					false,
					null);
			capture.setData(Raster.createRaster(
					image.getSampleModel(),
					new DataBufferByte(Files.readAllBytes(DEVICE_PATH), image.getRaster().getDataBuffer().getSize()),
					null));
			ImageIO.write(capture, "png", Paths.get("/userdata/roms/anbernic/screenshots/" + UUID.randomUUID() + ".png").toFile());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		 */
	}

	public void close()
	{
		try
		{
			channel.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void clear()
	{
		System.out.print("\033[H\033[J");
	}
}