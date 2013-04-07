package net.undead;

import java.util.Comparator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

	
public class DistanceChecker implements Comparator<EntityLiving>{
		
	public DistanceChecker(EntityLiving you){
			this.you = you;
	}
		
	@Override
	public int compare(EntityLiving o1, EntityLiving o2) {
			return Integer.signum((int) (o1.getDistanceToEntity(you) - o2.getDistanceToEntity(you)));
	}
		
	private Entity you;	
}