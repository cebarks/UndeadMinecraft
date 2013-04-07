package net.undead.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.undead.UndeadMinecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemZombieLimb extends Item {

	public ItemZombieLimb(int par1) {
		super(par1);
		setMaxStackSize(64);
		setUnlocalizedName("zombieLimb");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:zombieLimb");
	}
}