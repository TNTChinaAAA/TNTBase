package net.tntchina.clientbase.event;

import java.lang.reflect.Method;

/**
 * the event method invoke util.(事件调用的工具)
 * Project: TNTChina Client Base
 * -----------------------------------------------------------
 * Copyright  2019 | TNTChina | All rights reserved.
 */
public class EventMethod {

    private EventListener eventListener;
    private Method method;

    public EventMethod(EventListener eventListener, Method method) {
        this.eventListener = eventListener;
        this.method = method;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public Method getMethod() {
        return method;
    }
}