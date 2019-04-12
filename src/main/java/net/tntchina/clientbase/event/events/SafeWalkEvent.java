package net.tntchina.clientbase.event.events;

import net.tntchina.clientbase.event.Event;

/**
 * set the player has safe walk.(设置玩家要不要防止从方块边缘掉落)
 * @author TNTChina
 */
public class SafeWalkEvent extends Event {
	
    private boolean safe;

    public SafeWalkEvent(boolean safe) {
        this.safe = safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
    
    public boolean getSafe() {
    	return this.safe;
    }
}
