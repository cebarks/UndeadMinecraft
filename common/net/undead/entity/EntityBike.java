package net.undead.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.ModLoader;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

public class EntityBike extends Entity {
	private double velocityX;
	private double velocityY;
	private double velocityZ;

	public EntityBike(World par1World) {
		super(par1World);
		preventEntitySpawning = true;
		setSize(1.5F, 1.5F);
		yOffset = height;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(17, new Integer(0));
		dataWatcher.addObject(18, new Integer(1));
		dataWatcher.addObject(19, new Integer(0));
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity) {
		return par1Entity.boundingBox;
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	public EntityBike(World par1World, double par2, double par4, double par6) {
		this(par1World);
		setPosition(par2, par4 + yOffset, par6);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
	}

	@Override
	public double getMountedYOffset() {
		return height * 0.0D - 0.30000001192092896D;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (worldObj.isRemote || isDead) {
			return true;
		}

		setForwardDirection(-getForwardDirection());
		setTimeSinceHit(10);
		setDamageTaken(getDamageTaken() + par2 * 10);
		setBeenAttacked();

		if (getDamageTaken() > 40) {
			if (riddenByEntity != null) {
				riddenByEntity.mountEntity(this);
			}

			for (int i = 0; i < 9; i++) {
				dropItemWithOffset(Item.leather.itemID, 1, 0.0F);
			}

			for (int j = 0; j < 12; j++) {
				dropItemWithOffset(Item.ingotIron.itemID, 1, 0.0F);
			}

			setDead();
		}

		return true;
	}

	@Override
	public void performHurtAnimation() {
		setForwardDirection(-getForwardDirection());
		setTimeSinceHit(10);
		setDamageTaken(getDamageTaken() * 11);
	}

	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	public void setPositionAndRotation2(double par1, double par3, double par5,
			float par7, float par8, int par9) {
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}

	@Override
	public void setVelocity(double par1, double par3, double par5) {
		velocityX = motionX = par1;
		velocityY = motionY = par3;
		velocityZ = motionZ = par5;
	}

	@Override
	public void onUpdate() {
		slowDown();

		if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer) {
			if (Keyboard
					.isKeyDown(ModLoader.getMinecraftInstance().gameSettings.keyBindForward.keyCode)) {
				motionX += Math.sin(rotationYaw) / 180D * Math.PI;
				motionZ += Math.cos(rotationYaw) / 180D * Math.PI;
			}
			if (Keyboard
					.isKeyDown(ModLoader.getMinecraftInstance().gameSettings.keyBindLeft.keyCode)) {
				rotationYaw--;
			}
			if (Keyboard
					.isKeyDown(ModLoader.getMinecraftInstance().gameSettings.keyBindRight.keyCode)) {
				rotationYaw++;
			}
		}

		forward(0.01f);

		if (onGround) {
			motionY = 0;
		} else {
			motionY = -0.5f;
		}

		moveEntity(motionX, motionY, motionZ);
	}

	public void forward(float speed) {

		if (rotationYaw > 360) {
			rotationYaw -= 360;
		}
		if (rotationYaw < 0) {
			rotationYaw += 360;
		}

		double motionX2 = -99999;

		double d = Math.ceil((rotationYaw - 270) / 90);
		if (d == 1) {
			motionX2 = 1f;
		} else if (d == 0) {

		} else if (d == -1) {

		} else if (d == -2) {

		}

		System.out.println(motionX2);

	}

	public void slowDown() {

		if (motionZ > 0) {
			motionZ -= 0.0025f;
		}
		if (motionZ < 0) {
			motionZ += 0.0025f;
		}
		if (motionZ < 0.0025f && motionZ > -0.0025f) {
			motionZ = 0;
		}

		if (motionX > 0) {
			motionX -= 0.0025f;
		}
		if (motionX < 0) {
			motionX += 0.0025f;
		}
		if (motionX < 0.0025f && motionX > -0.0025f) {
			motionX = 0;
		}

	}

	@Override
	public void updateRiderPosition() {
		if (riddenByEntity == null) {
			return;
		} else {
			double d = Math.cos((rotationYaw * Math.PI) / 180D) * 0.40000000000000002D;
			double d1 = Math.sin((rotationYaw * Math.PI) / 180D) * 0.40000000000000002D;
			riddenByEntity.setPosition(posX + d, posY + getMountedYOffset()
					+ riddenByEntity.getYOffset(), posZ + d1);
			return;
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer) {
		if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer)
				&& riddenByEntity != par1EntityPlayer) {
			return true;
		}

		if (!worldObj.isRemote) {
			par1EntityPlayer.mountEntity(this);
		}

		return true;
	}

	public void setDamageTaken(int par1) {
		dataWatcher.updateObject(19, Integer.valueOf(par1));
	}

	public int getDamageTaken() {
		return dataWatcher.getWatchableObjectInt(19);
	}

	public void setTimeSinceHit(int par1) {
		dataWatcher.updateObject(17, Integer.valueOf(par1));
	}
	
	public int getTimeSinceHit() {
		return dataWatcher.getWatchableObjectInt(17);
	}

	public void setForwardDirection(int par1) {
		dataWatcher.updateObject(18, Integer.valueOf(par1));
	}

	public int getForwardDirection() {
		return dataWatcher.getWatchableObjectInt(18);
	}
}
