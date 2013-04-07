package net.undead.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;

public class ItemBandage extends ItemFood {
	
	public ItemBandage(int i) {
		super(i, 0, 0, false);
		maxStackSize = 5;
		setCreativeTab(UndeadMinecraft.tabUndead);
		setUnlocalizedName("itemBandage");
	}

	@Override
	public ItemStack onEaten(ItemStack itemStack, World world,EntityPlayer entityPlayer) {
		itemStack.stackSize--;
		//TODO fix
		//entityplayer.setBloodRate(entityplayer.getBloodRate() - 5);
		entityPlayer.heal(4);
		return super.onEaten(itemStack, world, entityPlayer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:bandage");
	}
}