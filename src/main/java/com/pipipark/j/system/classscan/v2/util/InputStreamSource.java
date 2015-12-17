package com.pipipark.j.system.classscan.v2.util;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSource {

	InputStream getInputStream() throws IOException;
}
