package net.undead.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.undead.UndeadMinecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPot extends Item
{
	public ItemPot(int i) {
		super(i);
		setMaxStackSize(1);
		setUnlocalizedName("pot");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:pot");
	}
}