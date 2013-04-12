package net.undead;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;

public class Reference {
	public static final String MOD_ID = "UndeadMinecraft";
	public static final String MOD_NAME = "Undead Minecraft";
	
	private static final int versionMajor = 2;
	private static final int versionMinor = 0;
	private static final int versionSubminor = 0;
	
	public static final String VERSION = versionMajor+"."+versionMinor+"."+versionSubminor;
	
	public static final String TextureLocation = "/mods/UndeadMinecraft/textures";
	public static final String EntityTextureLocation = "/mods/UndeadMinecraft/textures/entity/";

	public static final Minecraft MC = FMLClientHandler.instance().getClient();
}