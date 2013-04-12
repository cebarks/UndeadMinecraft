package net.undead.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.undead.ContainerSurviralInv;
import net.undead.entity.EntitySurvivor;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        
        switch(ID) {
            case 100:
                EntitySurvivor es = (EntitySurvivor) world.getEntityByID(x);
                return new ContainerSurviralInv(player.inventory, es.inventory);
        }
        
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        
        switch(ID) {
            case 100:
                EntitySurvivor es = (EntitySurvivor) world.getEntityByID(x);
                return new GuiSurvivalInventory(player, es);
        }
        
        return null;
    }

}
