package net.tntchina.util;

public class ColorUtil {
	
	public static final int CANCEL = 0;
	public static final int BLACK = 30;
	public static final int RED = 31;
	public static final int GREEN = 32;
	public static final int YELLOW = 33;
	public static final int BLUE = 34;
	public static final int PURPLE = 35;
	public static final int DARK_GREEN = 36;
	public static final int WHITE = 37;
	public static final int BACKGROUND_BLACK = 40;
	public static final int BACKGROUND_DARK_RED = 41;
	public static final int BACKGROUND_GREEN = 42;
	public static final int BACKGROUND_YELLOW = 43;
	public static final int BACKGROUND_BLUE = 44;
	public static final int BACKGROUND_PURPLE = 45;
	public static final int BACKGROUND_DARK_GREEN = 46;
	public static final int BACKGROUND_WHITE = 47;
	
	public static final String getColorString(String text, int color) {
		String colorMessage = "";
		
		switch (color) {
			case 0: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 30: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 31: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 32: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 33: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 34: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 35: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 36: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 37: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 40: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 41: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 42: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 43: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 44: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 45: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 46: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
			
			case 47: {
				colorMessage = "\u001b[" + color + "m";
				break;
			}
		
			default: {
				break;
			}
		}
		
		return colorMessage + text;
	}
}
