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
		setMaxStackSize(8);
		setUnlocalizedName("firstAid");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		itemstack.stackSize--;
		entityplayer.heal(10);
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:firstAid");
	}
}