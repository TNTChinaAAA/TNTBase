package net.tntchina.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T invoke(String methodName, Object instance, Object... parmas) {
		try {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			
			for (Object o : parmas) {
				classes.add(o.getClass());
			}
			
			Method m = instance.getClass().getDeclaredMethod(methodName, classes.toArray(new Class<?>[0]));
			
			if (!m.isAccessible()) {
				m.setAccessible(true);
			}
			
			Object o = m.invoke(instance, parmas);
			return (T) o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
