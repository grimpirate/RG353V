package com.grimpirate;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FrameBuffer
{
	public static final Path DEVICE_PATH = Paths.get("/dev/fb0");
	public static final Path BPP_PATH = Paths.get("/sys/class/graphics/fb0/bits_per_pixel");
	public static final Path STRIDE_PATH = Paths.get("/sys/class/graphics/fb0/stride");
	public static final Path SIZE_PATH = Paths.get("/sys/class/graphics/fb0/virtual_size");
	
	private final DataBufferByte bytes;
	private final BufferedImage image;

	public FrameBuffer() throws IOException
	{
		final int BITS_PER_PIXEL = Integer.parseInt(Files.readString(BPP_PATH).trim(), 10);
		final int STRIDE = Integer.parseInt(Files.readString(STRIDE_PATH).trim(), 10);
		final String size = Files.readString(SIZE_PATH).trim();
		final int offset = size.indexOf(",");
		final int WIDTH = Integer.parseInt(size.substring(0, offset));
		final int HEIGHT = Integer.parseInt(size.substring(offset + 1));
		final int BITS_PER_CHANNEL = BITS_PER_PIXEL >> 2; // Division by 4, # of channels BGRA
		final int BYTES_PER_PIXEL = BITS_PER_PIXEL >> 3; // Divide by 8, # of bits in a byte

		image = new BufferedImage(new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB),
				new int[] {
						BITS_PER_CHANNEL,
						BITS_PER_CHANNEL,
						BITS_PER_CHANNEL,
						BITS_PER_CHANNEL},
				true, false,
				Transparency.OPAQUE,
				DataBuffer.TYPE_BYTE),
				Raster.createInterleavedRaster(
						bytes = new DataBufferByte(STRIDE * HEIGHT),
						WIDTH,
						HEIGHT,
						STRIDE,
						BYTES_PER_PIXEL,
						new int[] { // BGRA offsets
								2,
								1,
								0,
								3},
						null),
				false,
				null);
	}

	public final Graphics2D createGraphics()
	{
		final Graphics2D g2d = image.createGraphics();
		g2d.setClip(0, 0, image.getWidth(), image.getHeight());
		return g2d;
	}

	public void flush()
	{
		try
		{
			Files.write(DEVICE_PATH, bytes.getData(), StandardOpenOption.WRITE);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
