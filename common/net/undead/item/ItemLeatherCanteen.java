package net.undead.item;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLeatherCanteen extends Item {

	public ItemLeatherCanteen(int i) {
		super(i);
		setUnlocalizedName("leatherCanteen");
		setCreativeTab(UndeadMinecraft.tabUndead);
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(
				par2World, par3EntityPlayer, true);

		if (var4 == null) {
			return par1ItemStack;
		} else {
			if (var4.typeOfHit == EnumMovingObjectType.TILE) {
				int var5 = var4.blockX;
				int var6 = var4.blockY;
				int var7 = var4.blockZ;

				if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7)) {
					return par1ItemStack;
				}

				if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var7, par1ItemStack)) {
					return par1ItemStack;
				}

				if (par2World.getBlockMaterial(var5, var6, var7) == Material.water) {
					--par1ItemStack.stackSize;

					if (par1ItemStack.stackSize <= 0) {
						return new ItemStack(UndeadMinecraft.leatherCanteenWithWater);
					}
				}
			}

			return par1ItemStack;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister iconRegister) {
		iconIndex = iconRegister.registerIcon("UndeadMinecraft:waterCanteen");
	}
}
