package net.undead.entity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.undead.UndeadMinecraft;

public class EntityNewZombie extends EntityMob implements IMob{
	
	public EntityNewZombie(World world) {
		super(world);
		setSize(0.6F, 1.8F);
		texture = UndeadMinecraft.EntityTextureLocation + "zombie" + (rand.nextInt(7) + 1) + ".png";
		moveSpeed = .3F;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this,
				net.minecraft.entity.player.EntityPlayer.class, moveSpeed, false));
		tasks.addTask(2, new EntityAIAttackOnCollide(this,
				net.minecraft.entity.passive.EntityAnimal.class, moveSpeed, true));
		tasks.addTask(3, new EntityAIWander(this, moveSpeed));
		tasks.addTask(4, new EntityAIWatchClosest(this,
				net.minecraft.entity.player.EntityPlayer.class, 8F));
		tasks.addTask(5, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				net.minecraft.entity.player.EntityPlayer.class, 32F, 0, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				net.minecraft.entity.passive.EntityAnimal.class, 16F, 0, false));
	}
	
	public EntityNewZombie setEntityPosition(double x, double y, double z)
	{
		setPosition(x, y, z);
		return this;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		wanderToLight();
		if (entityToAttack == null) {
			breakBlocks();
		}
		if (entityToAttack instanceof EntityItem) {
			float f = entityToAttack.getDistanceToEntity(this);
			if (f < 1.0F) {
				entityToAttack.setDead();
				worldObj.playSoundAtEntity(this, "random.pop",
						0.5F + 0.5F * rand.nextInt(2),
						(rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			}
		}

	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	private boolean playerLookingAtHead(EntityPlayer par1EntityPlayer) {
		Vec3 vec3d = par1EntityPlayer.getLook(1.0F);
		vec3d.normalize();
		Vec3 vec3d1 = Vec3.createVectorHelper(posX - par1EntityPlayer.posX,
				((boundingBox.contract(0D, 0.1D, 0D).minY + 1D) + height / 2.0F)
				- (par1EntityPlayer.posY + par1EntityPlayer
						.getEyeHeight()), posZ
				- par1EntityPlayer.posZ
						);
		double d = vec3d1.lengthVector();
		double d1 = vec3d.dotProduct(vec3d1);

		if (d1 > 1.0D - 0.025000000000000001D / d) {
			return par1EntityPlayer.canEntityBeSeen(this);
		} else {
			return false;
		}
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	private Vec3 findPossibleLight() {

		for (int i = 0; i < 125; i++) {
			int j = MathHelper
					.floor_double((posX + rand.nextInt(100)) - 50D);
			int k = MathHelper.floor_double((boundingBox.minY + rand
					.nextInt(50)) - 25D);
			int l = MathHelper
					.floor_double((posZ + rand.nextInt(100)) - 50D);

			if (worldObj.getFullBlockLightValue(j, k, l) > 10F
					&& getBlockPathWeight(j, k, l) < 0.0F) {
				return Vec3.createVectorHelper(j, k, l);
			}
		}

		return null;
	}

	public void wanderToLight() {
		if (!worldObj.isDaytime()) {
			Vec3 vec = findPossibleLight();
			if (vec != null) {
				getNavigator().tryMoveToXYZ(vec.xCoord, vec.yCoord, vec.zCoord,
						moveSpeed);
				getLookHelper().setLookPosition(vec.xCoord, vec.yCoord,
						vec.zCoord, 4F, 4F);
			}
		}
	}

	public boolean canBreakBlock(int s, int d, int f) {
		if (worldObj.getBlockId(s, d, f) != Block.cobblestone.blockID
				&& worldObj.getBlockId(s, d, f) != Block.stone.blockID
				&& worldObj.getBlockId(s, d, f) != Block.lavaStill.blockID
				&& worldObj.getBlockId(s, d, f) != Block.waterStill.blockID
				&& worldObj.getBlockId(s, d, f) != Block.obsidian.blockID
				&& worldObj.getBlockId(s, d, f) != Block.bedrock.blockID
				&& worldObj.getBlockId(s, d, f) != Block.doorSteel.blockID
				&& worldObj.getBlockId(s, d, f) != Block.doorWood.blockID
				&& worldObj.getBlockId(s, d, f) != 0
				&& entityToAttack instanceof EntityPlayer) {
			return true;
		}
		return false;
	}

	public int getDistanceToVector(Vec3 v3d) {
		int xDif = (int) (v3d.xCoord - posX);
		int yDif = (int) (v3d.yCoord - posY);
		int zDif = (int) (v3d.zCoord - posZ);
		return (int) Math.sqrt(xDif * xDif + yDif * yDif + zDif + zDif);
	}

	public int timeToBreak(Block block) {
		return 20;
	}

	public void breakBlocks() {
		int x = MathHelper.floor_double(posX) + rand.nextInt(4)
				- rand.nextInt(4);
		int y = MathHelper.floor_double(posY) + rand.nextInt(4)
				- rand.nextInt(4);
		int z = MathHelper.floor_double(posX) + rand.nextInt(4)
				- rand.nextInt(4);

		Block block = Block.blocksList[worldObj.getBlockId(x, y, z)];
		if (canBreakBlock(x, y, z)) {
			if (ticksExisted % 10 == 0) {
				destroyBlock(block.blockID, x, y, z);
			}
		}
	}

	public void destroyBlock(int id, int x, int y, int z) {
		for (int i = 0; i < 10; i++) {
			worldObj.spawnParticle("tilecrack_" + id, x + rand.nextGaussian(),
					y + rand.nextGaussian(), z + rand.nextGaussian(), 0.0D,
					0.0D, 0.0D);
		}
		worldObj.playSoundAtEntity(this,
				Block.blocksList[id].stepSound.stepSoundName, 1F, 1F);
		worldObj.setBlock(x, y, z, 0);
	}

//	@Override
//	public boolean attackEntityAsMob(Entity entity) {
//		if (entity instanceof EntityLiving) {
//			if (rand.nextInt(25) == 1)
//				((EntityLiving) entity).addPotionEffect(new PotionEffect(rand
//						.nextInt(2) + 20, 2000, 100));
//		}
//		return super.attackEntityAsMob(entity);
//	}

	public boolean getIsEdible(int itemID) {
		Item item = Item.itemsList[itemID];
		ItemFood item2 = null;
		if (item instanceof ItemFood) {
			item2 = (ItemFood) item;
		} else {
			return false;
		}
		if (item2.isWolfsFavoriteMeat()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void playLivingSound() {
		String s = getLivingSound();
		if (s != null) {
			worldObj.playSoundAtEntity(this, s, getSoundVolume(),
					getSoundPitch());
		}
	}

	@Override
	protected int getDropItemId() {
		Random random = new Random();
		
		switch(random.nextInt(6)) {
			case 0:
				entityDropItem(new ItemStack(UndeadMinecraft.clothes, 1, 0), 1.0F);
			case 1:
				entityDropItem(new ItemStack(UndeadMinecraft.clothes, 1, 1), 1.0F);
			case 2:
				entityDropItem(new ItemStack(UndeadMinecraft.clothes, 1, 2), 1.0F);
			case 3:
				entityDropItem(new ItemStack(UndeadMinecraft.clothes, 1, 0), 1.0F);
			case 4:
				return 0;
			case 5:
				return 0;
			case 6:
				return 0;
		}
		return 0;
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);

		if (worldObj.canBlockSeeTheSky(i, j, k)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.zombie";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.zombiehurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.zombiedeath";
	}

	@Override
	protected float getSoundVolume() {
		return 1.5F;
	}

	@Override
	protected float getSoundPitch() {
		return 0.8F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		Entity living = damagesource.getEntity();
		if (living instanceof EntityPlayer && !damagesource.isProjectile()) {
			EntityPlayer player = (EntityPlayer) living;
			if (!playerLookingAtHead(player)) {
				i /= 10;
			}
		}
		return super.attackEntityFrom(damagesource, i);
	}
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

}
