package net.undead.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;

public class EntityCaveDweller extends EntityMob implements IMob{

	public EntityCaveDweller(World world) {
		super(world);
		texture = new StringBuilder().append("/textures/ZombieMod/CaveDweller")
				.append(rand.nextInt(1) + 1).append(".png").toString();
		moveSpeed = 0.4F;
	}

	@Override
	public int getMaxHealth() {
		return 10;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (entityToAttack == null) {
			List list = worldObj.getEntitiesWithinAABB(
					EntitySurvivor.class,
					AxisAlignedBB.getBoundingBox(posX, posY, posZ,
							posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D,
							4D, 16D));
			if (!list.isEmpty()) {
				setTarget((Entity) list.get(worldObj.rand.nextInt(list.size())));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		Entity entity = damagesource.getEntity();
		if (entity instanceof EntityPlayer) {
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					boundingBox.expand(32D, 32D, 32D));
			for (int j = 0; j < list.size(); j++) {
				Entity entity1 = (Entity) list.get(j);
				if (entity1 instanceof EntityPigZombie) {
				}
			}

			entityToAttack = entity;
		}
		return super.attackEntityFrom(damagesource, i);
	}

	@Override
	protected int getDropItemId() {
		entityDropItem(new ItemStack(UndeadMinecraft.clothes, 1, 0), 1.0F);
		entityDropItem(new ItemStack(UndeadMinecraft.clothes, 1, 1), 1.0F);
		return 0;
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);

		if (worldObj.canBlockSeeTheSky(i, j - 1, k)
				|| worldObj.getBlockId(i, j - 1, k) != Block.stone.blockID
				|| posY > 50) {
			return false;
		}
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "mob.zombie";
	}

	@Override
	public String getHurtSound() {
		if (rand.nextInt(2) == 1) {
			return "damage.fallbig";
		}
		return "mob.zombiehurt";
	}

	@Override
	protected String getDeathSound() {
		return "mob.zombiedeath";
	}

	EntityPlayer entityplayer;

}
