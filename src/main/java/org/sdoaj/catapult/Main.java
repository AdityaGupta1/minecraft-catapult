package org.sdoaj.catapult;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "catapult";
    public static final String VERSION = "1.0";
    
    public static double angle = 0D;
    public static double power = 0D;
    
    public static boolean parametersSet = false;
    
    public static String aquaText = EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD;
    public static String redText = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	registerModEntity(EntityCatapult.class, new RenderCatapult(),
				"catapult", EntityRegistry.findGlobalUniqueEntityId(),
				0xC38751, 0xDCA556);
    	
    	MinecraftForge.EVENT_BUS.register(new FallingBlockEventHandler());
    }
    
    public void registerModEntity(Class parEntityClass, Render render,
			String parEntityName, int entityId, int foregroundColor,
			int backgroundColor) {
		EntityRegistry.registerGlobalEntityID(parEntityClass, parEntityName,
				entityId, foregroundColor, backgroundColor);
		EntityRegistry.registerModEntity(parEntityClass, parEntityName,
				entityId, this, 80, 1, false);
		RenderingRegistry
				.registerEntityRenderingHandler(parEntityClass, render);
	}
    
    @EventHandler
	public void registerCommands(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandCatapult());
	}
}
