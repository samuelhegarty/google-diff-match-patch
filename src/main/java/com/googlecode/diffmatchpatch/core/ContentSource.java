package com.googlecode.diffmatchpatch.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by shegarty on 13/01/2016.
 */
public interface ContentSource{
	public boolean isEditable();
	public String getContent() throws IOException;
	public void setContent(String content) throws IOException;

}
