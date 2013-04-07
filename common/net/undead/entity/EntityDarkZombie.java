package net.undead.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDarkZombie extends EntityMob implements IMob{

	public EntityDarkZombie(World world) {
		super(world);
		texture = "/textures/ZombieMod/zombie3.png";
		moveSpeed = 0.7F;
		health = 10;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (entityToAttack == null) {
			List list = worldObj.getEntitiesWithinAABB(
					net.undead.entity.EntitySurvivor.class,
					AxisAlignedBB.getBoundingBox(posX, posY, posZ,
							posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D,
							4D, 16D));
			if (!list.isEmpty()) {
				setTarget((Entity) list.get(worldObj.rand.nextInt(list.size())));
			}
		}

		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);

		if (worldObj.getFullBlockLightValue(i, j, k) > 8
				&& worldObj.isDaytime() && worldObj.canBlockSeeTheSky(i, j, k)) {
			double d = (float) posX + worldObj.rand.nextFloat();
			double d2 = (float) posY + worldObj.rand.nextFloat();
			double d4 = (float) posZ + worldObj.rand.nextFloat();
			worldObj.spawnParticle("smoke", d, d2, d4, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("smoke", d, d2 + 1, d4, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("smoke", d, d2 + 2, d4, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("smoke", d, d2 + 1, d4, 0.0D, 0.0D, 0.0D);

			worldObj.playSoundAtEntity(this, "random.fizz", 0.2F, 1F);
			if (rand.nextInt(20) == 0) {
				damageEntity(DamageSource.inFire, 1);
			}
		}
	}

	@Override
	public int getMaxHealth() {
		return 10;
	}

	@Override
	protected int getDropItemId() {
		return Item.feather.itemID;
	}

	@Override
	protected String getLivingSound() {
		return "mob.cow";
	}

	@Override
	public String getHurtSound() {
		return "mob.zombiehurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.zombiedeath";
	}

	EntityPlayer entityplayer;
}
