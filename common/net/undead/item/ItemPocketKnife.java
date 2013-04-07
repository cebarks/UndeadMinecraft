package net.undead.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.undead.UndeadMinecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPocketKnife extends Item {
	public ItemPocketKnife(int i) {
		super(i);
		setMaxStackSize(1);
		setContainerItem(this);
		setUnlocalizedName("pocketKnife");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:pocketKnife");
	}
}