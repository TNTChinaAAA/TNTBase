package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.Event;

/**
 * the event can cellable(����¼�����ȡ��)
 * @author TNTChina
 */
public class CancellableEvent extends Event {

    private boolean cancel;

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isCancelled() {
        return cancel;
    }
}