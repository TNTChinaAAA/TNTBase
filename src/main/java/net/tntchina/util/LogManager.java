package net.tntchina.util;

import java.text.*;
import java.util.*;
import org.apache.commons.lang3.*;

public class LogManager {
	
	public static final Logger getLogger() {
		return new LogEntry("", false);
	}
	
	public static final Logger getLogger(String name) {
		return new LogEntry(name, true);
	}
	
	public static final class LogEntry implements Logger {

		private final String name;
		private final boolean flag;
		
		public LogEntry(String name, boolean flag) {
			this.name = name;
			this.flag = flag;
		}

		public void info(Object... objects) {
			//System.out.print(ColorUtil.getColorString("", ColorUtil.GREEN));
			System.out.print(new SimpleDateFormat("[HH:mm:ss] ").format(new Date()));
			System.out.print("[" + Thread.currentThread().getName() + "/INFO]");
			
			if (this.getFlag() && !StringUtils.isEmpty(this.getName())) {
				System.out.print(" [" + this.getName() + "]");
			}
			
			System.out.print(": ");
			
			for (Object o : objects) {
				System.out.print(o != null ? o : "null");
			}
			
			//System.out.print(ColorUtil.getColorString("", ColorUtil.CANCEL));
			System.out.println();
		}

		public void error(Object... objects) {
			//System.out.print(ColorUtil.getColorString("", ColorUtil.RED));
			System.out.print(new SimpleDateFormat("[HH:mm:ss] ").format(new Date()));
			System.out.print("[" + Thread.currentThread().getName() + "/ERROR]");
			
			if (this.getFlag() && !StringUtils.isEmpty(this.getName())) {
				System.out.print(" [" + this.getName() + "]");
			}
			
			System.out.print(": ");
			
			for (Object o : objects) {
				System.out.print(o != null ? o : "null");
			}
			
			//System.out.print(ColorUtil.getColorString("", ColorUtil.CANCEL));
			System.out.println();
		}

		public void warning(Object... objects) {
			//System.out.print(ColorUtil.getColorString("", ColorUtil.YELLOW));
			System.out.print(new SimpleDateFormat("[HH:mm:ss] ").format(new Date()));
			System.out.print("[" + Thread.currentThread().getName() + "/WARN]");
			
			if (this.getFlag() && !StringUtils.isEmpty(this.getName())) {
				System.out.print(" [" + this.getName() + "]");
			}
			
			System.out.print(": ");
			
			for (Object o : objects) {
				System.out.print(o != null ? o : "null");
			}
			
			//System.out.print(ColorUtil.getColorString("", ColorUtil.CANCEL));
			System.out.println();
		}

		public void debug(Object... objects) {
			//System.out.print(ColorUtil.getColorString("", ColorUtil.WHITE));
			System.out.print(new SimpleDateFormat("[HH:mm:ss] ").format(new Date()));
			System.out.print("[" + Thread.currentThread().getName() + "/DEBUG]");
			
			if (this.getFlag() && !StringUtils.isEmpty(this.getName())) {
				System.out.print(" [" + this.getName() + "]");
			}
			
			System.out.print(": ");
			
			for (Object o : objects) {
				System.out.print(o != null ? o : "null");
			}
			
			//System.out.print(ColorUtil.getColorString("", ColorUtil.CANCEL));
			System.out.println();
		}
		
		public boolean getFlag() {
			return this.flag;
		}
		
		public String getName() {
			return this.name;
		}
	}
}
