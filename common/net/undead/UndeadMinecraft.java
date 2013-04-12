package net.undead;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldType;
import net.undead.creativetab.UndeadCreativeTab;
import net.undead.gui.GuiHandler;
import net.undead.item.ItemBandage;
import net.undead.item.ItemCamera;
import net.undead.item.ItemCanOpener;
import net.undead.item.ItemCannedFood;
import net.undead.item.ItemClothes;
import net.undead.item.ItemCrossbow;
import net.undead.item.ItemDebug;
import net.undead.item.ItemEnergyBar;
import net.undead.item.ItemFirstAid;
import net.undead.item.ItemLeatherCanteen;
import net.undead.item.ItemLeatherCanteenWithWater;
import net.undead.item.ItemPistolAmmo;
import net.undead.item.ItemPocketKnife;
import net.undead.item.ItemPot;
import net.undead.item.ItemPotWithFood;
import net.undead.item.ItemZombieLimb;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid = Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION)
@NetworkMod(clientSideRequired=true, serverSideRequired=true)
public class UndeadMinecraft {
	
	@Instance("UndeadMinecraft")
	public static UndeadMinecraft instance;
	
	@SidedProxy(clientSide="net.undead.ClientProxy", serverSide="net.undead.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		
		//Names
		LanguageRegistry.addName(bandage, "Bandage");
		LanguageRegistry.addName(camera, "Camera");
		LanguageRegistry.addName(cannedFood, "Canned Food");
		LanguageRegistry.addName(canOpener, "Can Opener");
		LanguageRegistry.addName(crossbow, "Crossbow");
		LanguageRegistry.addName(debug, "Debug Item");
		LanguageRegistry.addName(energyBar, "Energy bar");
		LanguageRegistry.addName(firstAid, "First Aid Kit");
		LanguageRegistry.addName(leatherCanteen, "Leather Canteen");
		LanguageRegistry.addName(leatherCanteenWithWater, "Leather Canteen (Water)");
		LanguageRegistry.addName(pocketKnife, "Pocket Knife");
		LanguageRegistry.addName(pot, "Empty Pot");
		LanguageRegistry.addName(potWithFood, "Full Pot");
		LanguageRegistry.addName(zombieLimb, "Zombie Limb");
		LanguageRegistry.addName(pistolAmmo, "Pistol Ammo");
		LanguageRegistry.addName(new ItemStack(clothes, 1, 0), "Cap");
		LanguageRegistry.addName(new ItemStack(clothes, 1, 1), "Shirt");
		LanguageRegistry.addName(new ItemStack(clothes, 1, 2), "Pants");
		LanguageRegistry.addName(new ItemStack(clothes, 1, 3), "Shoes");
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabUndead", "en_US", "Undead Minecraft");
		LanguageRegistry.instance().addStringLocalization("entity.NewZombie.name", "en_US", "New Zombie");
		LanguageRegistry.instance().addStringLocalization("entity.Survivor.name", "en_US", "Survivor");
		
		//Entity Shenanigans
		EntityRegistry.removeSpawn(net.minecraft.entity.monster.EntitySpider.class, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.removeSpawn(net.minecraft.entity.monster.EntityCreeper.class, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.removeSpawn(net.minecraft.entity.monster.EntityEnderman.class, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.removeSpawn(net.minecraft.entity.monster.EntityPigZombie.class, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.removeSpawn(net.minecraft.entity.monster.EntitySkeleton.class, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.removeSpawn(net.minecraft.entity.monster.EntitySilverfish.class, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.removeSpawn(net.minecraft.entity.monster.EntitySpider.class, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.removeSpawn(net.minecraft.entity.monster.EntityZombie.class, EnumCreatureType.monster, WorldType.base12Biomes);
		
		EntityRegistry.registerGlobalEntityID(net.undead.entity.EntityNewZombie.class, "NewZombie", 100, 0xFFFFFF, 0x000000);
		EntityRegistry.registerGlobalEntityID(net.undead.entity.EntitySurvivor.class, "Survivor", 101, 0x008888, 0x880000);
		
		EntityRegistry.addSpawn(net.undead.entity.EntityNewZombie.class, 6, 0, 15, EnumCreatureType.monster, WorldType.base12Biomes);
		EntityRegistry.addSpawn(net.undead.entity.EntitySurvivor.class, 2, 2, 4, EnumCreatureType.creature, WorldType.base12Biomes);

		//Crafting :&
		GameRegistry.addShapelessRecipe(new ItemStack(bandage, 1),  new Object[] {
			new ItemStack(clothes, 1, 0), pocketKnife
		});
		GameRegistry.addShapelessRecipe(new ItemStack(bandage, 3),  new Object[] {
			new ItemStack(clothes, 1, 1), pocketKnife
		});
		GameRegistry.addShapelessRecipe(new ItemStack(bandage, 2),  new Object[] {
			new ItemStack(clothes, 1, 2), pocketKnife
		});
		GameRegistry.addShapelessRecipe(new ItemStack(bandage, 1),  new Object[] {
			new ItemStack(clothes, 1, 3), pocketKnife
		});
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {

	}
	
	public final static UndeadCreativeTab tabUndead = new UndeadCreativeTab("tabUndead");

	public final static ItemBandage bandage = new ItemBandage(5000);
	public final static ItemCamera camera = new ItemCamera(5001);
	public final static ItemCannedFood cannedFood = new ItemCannedFood(5002);
	public final static ItemCanOpener canOpener = new ItemCanOpener(5003);
	public final static ItemCrossbow crossbow = new ItemCrossbow(5004);
	public final static ItemDebug debug = new ItemDebug(5005);
	public final static ItemEnergyBar energyBar = new ItemEnergyBar(5006, 4, 3, false);
	public final static ItemFirstAid firstAid = new ItemFirstAid(5007);
	public final static ItemLeatherCanteen leatherCanteen = new ItemLeatherCanteen(5008);
	public final static ItemLeatherCanteenWithWater leatherCanteenWithWater = new ItemLeatherCanteenWithWater(5009);
	public final static ItemPocketKnife pocketKnife = new ItemPocketKnife(5010);
	public final static ItemPot pot = new ItemPot(5011);
	public final static ItemPotWithFood potWithFood = new ItemPotWithFood(5012);
	public final static ItemZombieLimb zombieLimb = new ItemZombieLimb(5013);
	public final static ItemClothes clothes = new ItemClothes(5014);
	public final static ItemPistolAmmo pistolAmmo = new ItemPistolAmmo(5015);
}