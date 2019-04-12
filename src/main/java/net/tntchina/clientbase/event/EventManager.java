package net.tntchina.clientbase.event;

import java.lang.reflect.*;
import java.util.*;

/**
 * the all event manager.(所有事件的处理者)
 */
public class EventManager {

	public static final Map<Class<Event>, List<EventMethod>> EVENT_MAP = new HashMap<>();

	/**
	 * register the event listener.(注册事件的调用者)
	 * @param eventListener
	 */
	@SuppressWarnings("unchecked")
	public static void registerListener(final EventListener eventListener) {
		try {
			for (final Method method : eventListener.getClass().getDeclaredMethods()) {
				if (method.isAnnotationPresent(EventTarget.class) && method.getParameterTypes().length == 1) {
					if (!method.isAccessible()) {
						method.setAccessible(true);
					}

					Class<Event> eventClass = (Class<Event>) method.getParameterTypes()[0];
					List<EventMethod> eventMethods = EventManager.EVENT_MAP.getOrDefault(eventClass, new ArrayList<EventMethod>());
					eventMethods.add(new EventMethod(eventListener, method));
					EventManager.EVENT_MAP.put(eventClass, eventMethods);
				}
			}
		} catch (Exception e) {
			
		}
	}

	/**
	 * invoke event(调用事件)
	 * @param event
	 */
	public static void callEvent(Event event) {
		if (event == null) {
			return;
		}

		List<EventMethod> eventMethods = EventManager.EVENT_MAP.getOrDefault(event.getClass(), null);

		if (eventMethods == null) {
			return;
		}
		
		for (EventMethod eventMethod : eventMethods) {
			try {
				eventMethod.getMethod().invoke(eventMethod.getEventListener(), event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}