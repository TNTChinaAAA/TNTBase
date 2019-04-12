package net.tntchina.clientbase.module.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.tntchina.clientbase.module.Module;
import net.tntchina.clientbase.module.ModuleCategory;
import net.tntchina.clientbase.module.ModuleManager;

public class Teams extends Module {

	public Teams(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onEnable() {
		Scoreboard scoreBoard = this.mc.theWorld.getScoreboard();
		ScorePlayerTeam team = scoreBoard.getPlayersTeam(this.mc.thePlayer.getName());
		
		if (team == null) {
			return;
		}
		
		logger.info(team.getTeamName());
	}
	
    public static boolean isEnemy(EntityPlayer e) {
        if (ModuleManager.getModule(Teams.class).getState()) {
            if (!Minecraft.getMinecraft().thePlayer.getTeam().equals(e.getTeam())) {
                if ((Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText().startsWith(new Character((char) (167)).toString())) && (e.getDisplayName().getFormattedText().startsWith(new Character((char) (167)).toString()))) {
                    if (!Minecraft.getMinecraft().thePlayer.getDisplayName().getFormattedText().substring(0, 2).equals(e.getDisplayName().getFormattedText().substring(0, 2))) {
                    	return false;
                    }
                }
            } else {
                return true;
            }
        }

        return false;
    }
    
	public static boolean isOnSameTeam(Entity entity) {
		if(!ModuleManager.getModuleState(Teams.class)) {
			return false;
		}
		
		if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("\247")) {
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2 || entity.getDisplayName().getUnformattedText().length() <= 2) {
                return false;
            }
            
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        }
		
		return false;
	}

    public static String getPrefix(EntityPlayer p) {
        String tname = p.getDisplayName().getFormattedText();
        String name = p.getName();

        if (tname.length() > 1 + name.length()) {
            return tname.substring(0, tname.indexOf(name));
        }

        return "";
    }
	
	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}
		
		if (!this.mc.thePlayer.getName().contains(((char) 167) + "")) {
			return;
		}
		
		synchronized (this.mc.theWorld.playerEntities) {
			for (int i = 0; i < this.mc.theWorld.playerEntities.size(); i++) {
				EntityPlayer player = this.mc.theWorld.playerEntities.get(i);
				
				if (this.mc.theWorld.entityList.contains(player) && !this.mc.theWorld.loadedEntityList.contains(player)) {
					continue;
				}
				
				if (player.equals(this.getPlayer())) {
					continue;
				}
				
				Scoreboard scoreBoard = this.mc.theWorld.getScoreboard();
				ScorePlayerTeam team = scoreBoard.getPlayersTeam(this.mc.thePlayer.getName());
				ScorePlayerTeam otherTeam = scoreBoard.getPlayersTeam(player.getName());
				
				if (otherTeam.equals(team) && (Teams.isOnSameTeam(player) | Teams.isEnemy(player))) {
					this.mc.theWorld.removeEntity(player);
					i--;
				}
			}
		}
	}
}
