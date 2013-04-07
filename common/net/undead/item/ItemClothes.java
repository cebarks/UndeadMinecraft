package net.undead.item;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.undead.UndeadMinecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemClothes extends Item {

	protected HashMap<Integer, Icon> icons = new HashMap<Integer, Icon>();
	
	public ItemClothes(int par1) {
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(UndeadMinecraft.tabUndead);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	
	public Icon getIconFromDamage(int damage) {
		return icons.get(damage);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        switch(par1ItemStack.getItemDamage()) {
        	case 0:
        		return "cap";
        	case 1:
        		return "shirt";
        	case 2:
        		return "pants";
        	case 3:
        		return "shoes";
        }
        return null;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int par1, CreativeTabs creativeTabs, List list)
    {
		list.add(new ItemStack(par1, 1, 0));
        list.add(new ItemStack(par1, 1, 1));
        list.add(new ItemStack(par1, 1, 2));
        list.add(new ItemStack(par1, 1, 3));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		icons.put(0,iconIndex = iconRegister.registerIcon("UndeadMinecraft:cap"));
		icons.put(1,iconIndex = iconRegister.registerIcon("UndeadMinecraft:shirt"));
		icons.put(2,iconIndex = iconRegister.registerIcon("UndeadMinecraft:pants"));
		icons.put(3,iconIndex = iconRegister.registerIcon("UndeadMinecraft:shoes"));
	}
}