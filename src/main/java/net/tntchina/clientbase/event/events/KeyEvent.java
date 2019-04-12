package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.*;

/**
 * on Keyboard has key press event.(�ڼ��������˰��°���ʱ���õ��¼�)
 */
public class KeyEvent extends Event {

    private int key;// key (����)

    public KeyEvent(int key) {
        this.key = key;
    }
    
    /**
     * @return the keyboard enter key(�����ϰ��µļ�)
     */
    public int getKey() {
        return this.key;
    }
}