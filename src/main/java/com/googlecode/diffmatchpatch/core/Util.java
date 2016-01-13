package com.googlecode.diffmatchpatch.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by shegarty on 13/01/2016.
 */
public class Util{
	public static String readStream(InputStream stream) throws IOException{
		try(Scanner scanner = new Scanner(stream).useDelimiter("\\A")){
			if(scanner.hasNext())return scanner.next();
			throw new IOException("End of stream not found");
		}
	}
}
