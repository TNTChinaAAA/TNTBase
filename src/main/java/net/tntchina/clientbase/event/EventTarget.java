package net.tntchina.clientbase.event;

import java.lang.annotation.*;

/**
 * if you invoke the annotation, you can on that event invoke your void.(Your void has an event parma)
 * (�����������ע�ͣ���������Ǹ��¼�������ķ���(ǰ����Ҫ��һ���¼���������Ϊ����))
 * @author TNTChina
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventTarget {
	
}