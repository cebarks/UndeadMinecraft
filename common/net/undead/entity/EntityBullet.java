package net.undead.entity;

import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityBullet extends EntityArrow implements IProjectile{

	public EntityBullet(World par1World, EntityPlayer entityPlayer, float f) {
		super(par1World, entityPlayer, f);
	}
}