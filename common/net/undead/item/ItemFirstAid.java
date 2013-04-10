package net.undead.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;

public class ItemFirstAid extends Item {

	public ItemFirstAid(int i) {
		super(i);
		setMaxStackSize(16);
		setUnlocalizedName("firstAid");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}
	
	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if(entityPlayer.getHealth()<entityPlayer.getMaxHealth()) {
			itemStack.stackSize--;
			entityPlayer.heal(6);
		}
		return super.onEaten(itemStack, world, entityPlayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:firstAid");
	}
}