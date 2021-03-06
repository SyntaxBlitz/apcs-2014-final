package com.timothyaveni.apcsfinal.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.timothyaveni.apcsfinal.networking.ByteArrayTools;

public class ByteArrayToolsTest {

	@Test
	public void testReadBytes() {
		byte[] data1 = { (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		assertEquals("0", 0, ByteArrayTools.readBytes(data1, 0, 3, false));

		byte[] data2 = { (byte) 0xF0, (byte) 0x00, (byte) 0x00 };
		assertEquals("0xF00000", 15728640, ByteArrayTools.readBytes(data2, 0, 3, false));

		byte[] data3 = { (byte) 0x45, (byte) 0x01, (byte) 0xD5, (byte) 0xA4, (byte) 0x8B, (byte) 0x00 };
		assertEquals("failure on length > 5", -1, ByteArrayTools.readBytes(data3, 0, 5, false));

		assertEquals("failure on length < 1", -1, ByteArrayTools.readBytes(data3, 0, 0, false));
		assertEquals("failure on length < 1", -1, ByteArrayTools.readBytes(data3, 0, -1, false));

		assertEquals("startIndex = 0, length = 4", 1157748132, ByteArrayTools.readBytes(data3, 0, 4, false));
		assertEquals("startIndex = 2, length = 2", 54692, ByteArrayTools.readBytes(data3, 2, 2, false));
		assertEquals("startIndex = 4, length = 2", 35584, ByteArrayTools.readBytes(data3, 4, 2, false));

		assertEquals("failure on length outside of bounds", -1, ByteArrayTools.readBytes(data3, 4, 3, false));
		assertEquals("failure on startIndex outside of bounds", -1, ByteArrayTools.readBytes(data3, 6, 1, false));
	}

	@Test
	public void testSetBytes() {
		byte[] data1 = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		byte[] data1Expected = { (byte) 0x00, (byte) 0xBC, (byte) 0x61, (byte) 0x4E };

		ByteArrayTools.setBytes(data1, 12345678, 0, 4);

		assertArrayEquals("byte 1", data1Expected, data1);

		byte[] data = { 0, 0, 0, 0, 0, 0, 0, 0 };

		ByteArrayTools.setBytes(data, 0, 7, 2);
		System.out.println(data[0]);
		System.out.println(data[1]);
		System.out.println(data[2]);
		System.out.println(data[3]);
		System.out.println(data[4]);
		System.out.println(data[5]);
		System.out.println(data[6]);
		System.out.println(data[7]);
		System.out.println(data[8]);
		System.out.println(Integer.toBinaryString(ByteArrayTools.readBytes(data, 7, 2, false)));
		System.out.println(ByteArrayTools.readBytes(data1, 0, 2, false));
		assertEquals("uh", 0, ByteArrayTools.readBytes(data1, 0, 2, false));

		// you're not actually going to make me test all that boring stuff, are
		// you?

		byte[] testArray = new byte[4];
		int n = (int) (Math.pow(256, 4) / 2);
		for (int i = 0; i < 10000000; i++) { // the fun part.
			int toSet = (int) (Math.random() * n); // 0..2.147B
			ByteArrayTools.setBytes(testArray, toSet, 0, 4);

			assertEquals("self-reference test", ByteArrayTools.readBytes(testArray, 0, 4, false), toSet);
		}
	}

	@Test
	public void testSignedReadBytes() {
		// pretty casual test.
		byte[] data = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

		ByteArrayTools.setBytes(data, 1, 0, 4);
		assertEquals(1, ByteArrayTools.readBytes(data, 0, 4, true));
		ByteArrayTools.setBytes(data, -1, 0, 4);
		assertEquals(-1, ByteArrayTools.readBytes(data, 0, 4, true));
		assertEquals(-1, ByteArrayTools.readBytes(data, 1, 3, true));
		assertEquals(16777215, ByteArrayTools.readBytes(data, 1, 3, false));

		ByteArrayTools.setBytes(data, 8388608, 0, 3);
		assertEquals(-8388608, ByteArrayTools.readBytes(data, 0, 3, true));
		assertEquals(8388608, ByteArrayTools.readBytes(data, 0, 3, false));

		ByteArrayTools.setBytes(data, 8388610, 0, 3);
		assertEquals(-8388606, ByteArrayTools.readBytes(data, 0, 3, true));
		assertEquals(8388610, ByteArrayTools.readBytes(data, 0, 3, false));

		ByteArrayTools.setBytes(data, 2147483647, 0, 4);
		assertEquals(2147483647, ByteArrayTools.readBytes(data, 0, 4, true));
		assertEquals(2147483647, ByteArrayTools.readBytes(data, 0, 4, false));

		ByteArrayTools.setBytes(data, 2147483647, 0, 4);
		assertEquals(-1, ByteArrayTools.readBytes(data, 3, 1, true));
		assertEquals(255, ByteArrayTools.readBytes(data, 3, 1, false));
	}
}
