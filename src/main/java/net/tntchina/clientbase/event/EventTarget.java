package net.tntchina.clientbase.event;

import java.lang.annotation.*;

/**
 * if you invoke the annotation, you can on that event invoke your void.(Your void has an event parma)
 * (如果你调用这个注释，你就能在那个事件调用你的方法(前提你要有一个事件的类型作为参数))
 * @author TNTChina
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventTarget {
	
}