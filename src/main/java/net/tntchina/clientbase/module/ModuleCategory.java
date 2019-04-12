package net.tntchina.clientbase.module;

/**
 * the modules categorys.
 * @author TNTChina
 */
public enum ModuleCategory {
	
	/**
	 * the categorys
	 */
    COMBAT("Combat"), PLAYER("Player"), MOVEMENT("Movement"), RENDER("Render"), WORLD("World"), MISC("Misc"), EXPLOIT("Exploit");
	
    private String name;

    ModuleCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}