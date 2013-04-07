package net.undead.entity;

import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

public class EntityArmZombie extends EntityNewZombie implements IMob{

	public EntityArmZombie(World world) {
		super(world);
		texture = "/textures/ZombieMod/Crawler.png";
	}
}