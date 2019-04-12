package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.*;

/**
 * on Keyboard has key press event.(在键盘上有人按下按键时调用的事件)
 */
public class KeyEvent extends Event {

    private int key;// key (按键)

    public KeyEvent(int key) {
        this.key = key;
    }
    
    /**
     * @return the keyboard enter key(键盘上按下的键)
     */
    public int getKey() {
        return this.key;
    }
}