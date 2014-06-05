package com.timothyaveni.apcsfinal.networking;

/**
 * This is a class that works in big-endian to place integers into byte arrays
 * and read integers from byte arrays. This is used in place of ByteBuffer
 * because it allows for more flexibility in int size and will also shove values
 * straight into the array.
 * 
 * @author Timothy Aveni
 * 
 */
public class ByteArrayTools {

	/**
	 * Returns an int that's derived from the corresponding section of the byte
	 * array. Reads bytes in big-endian.
	 * 
	 * @param data
	 *            the byte array to analyze
	 * @param startIndex
	 *            the first index to consider in the byte array
	 * @param length
	 *            the length, in bytes, of the integer to analyze. MUST BE in
	 *            the range 1..4.
	 * @param signed
	 *            TODO
	 * @return the computed integer, or -1 if the length is > 4 or < 1 or
	 *         (startIndex + length) > data.length
	 */
	public static int readBytes(byte[] data, int startIndex, int length, boolean signed) {
		if (length < 1 || length > 4 || startIndex + length > data.length)
			return -1;

		int toReturn = 0;
		if (signed && (data[startIndex] & 0x80) == 0x80) // the first bit is on
			toReturn = ~toReturn; // -1
		for (int i = startIndex; i < startIndex + length; i++) {
			toReturn = toReturn << 8;
			toReturn = toReturn | (data[i] & 0xFF);
		}
		return toReturn;
	}

	/**
	 * Modifies the referenced array to include an integer at the specified
	 * location with the appropriate number of bytes. Will truncate an integer
	 * to its least-significant bytes if it is larger than the specified length.
	 * Sets bytes in big-endian.
	 * 
	 * @param data
	 *            the byte array to modify
	 * @param value
	 *            the integral value to place into the byte array
	 * @param startIndex
	 *            the first index in the byte array that should be overridden
	 * @param length
	 *            the number of bytes to use to store this number. MUST BE in
	 *            the range 1..4.
	 */
	public static void setBytes(byte[] data, int value, int startIndex, int length) {
		if (length < 1 || length > 4 || startIndex + length > data.length)
			return;

		int bits = (length - 1) * 8;
		for (int i = startIndex; i < startIndex + length; i++) {
			data[i] = (byte) ((value >> bits) & 0xFF);
			bits -= 8;
		}
	}

}
