package net.tntchina.inputFix.utils;

public class OSDetector {

	public static enum OS {
		Windows, Linux, Mac, Unknown;
	}

	public static OS detectOS() {
		OSDetector detector = new OSDetector();
		
		if (detector.isWindows())
			return OS.Windows;
		else if (detector.isLinux())
			return OS.Linux;
		else if (detector.isMac())
			return OS.Mac;
		else
			return OS.Unknown;
	}

	String osName = System.getProperty("os.name");

	private OSDetector() {
	
	}

	boolean isLinux() {
		return this.osName.startsWith("Linux") || this.osName.startsWith("FreeBSD") || this.osName.startsWith("SunOS") || this.osName.startsWith("Unix");
	}

	boolean isMac() {
		return this.osName.startsWith("Mac OS X") || this.osName.startsWith("Darwin");
	}

	boolean isWindows() {
		return this.osName.startsWith("Windows");
	}
}