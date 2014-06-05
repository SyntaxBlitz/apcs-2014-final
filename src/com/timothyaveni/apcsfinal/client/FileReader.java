package com.timothyaveni.apcsfinal.client;

import java.io.File;

public class FileReader {

	public static File getFileFromResourceString(String path) {
		return new File("res/" + path);
	}

}
