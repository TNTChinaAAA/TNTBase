package net.tntchina.util;

public interface Logger {

	void info(Object... objects);
	void error(Object... objects);
	void warning(Object... objects);
	void debug(Object... objects);
}
