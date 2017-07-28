package org.aditya.catapult;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector3d;

import org.aditya.catapult.entity.EntityCatapult;
import org.aditya.catapult.model.RenderCatapult;
import org.aditya.catapult.util.ColorBlock;
import org.aditya.catapult.util.FallingBlockEventHandler;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static final String MODID = "catapult";
	public static final String VERSION = "1.0";

	public static double angle = 0D;
	public static double power = 0D;
	public static double rotationAngle = 0;
	// power shown to user, no scaling applied
	public static double shownPower = 0D;
	public static String color = "white";

	public static boolean parametersSet = false;

	public static Map<String, ColorBlock> colors = new HashMap<>();

	@EventHandler
	public void init(FMLInitializationEvent event) {
		registerColors();
// 		registerModEntity(EntityCatapult.class, new RenderCatapult(),
// 			     "catapult", EntityRegistry.findGlobalUniqueEntityId(),
// 			     0xC38751, 0xDCA556);
	}

	@EventHandler
	public void registerCommands(FMLServerStartingEvent event) {
			event.registerServerCommand(new CommandCatapult());
//			MinecraftForge.EVENT_BUS.register(new FallingBlockEventHandler());
	}

	public void registerModEntity(Class parEntityClass, Render render, String parEntityName, int entityId,
			int foregroundColor, int backgroundColor) {
		EntityRegistry.registerGlobalEntityID(parEntityClass, parEntityName, entityId, foregroundColor,
				backgroundColor);
		EntityRegistry.registerModEntity(parEntityClass, parEntityName, entityId, this, 80, 1, false);
		RenderingRegistry.registerEntityRenderingHandler(parEntityClass, render);
	}

	public static ColorBlock getColorBlock() {
		return colors.get(color);
	}

	public static ChatComponentText createChatMessage(String text, EnumChatFormatting color) {
		ChatComponentText chat = new ChatComponentText(text);
		chat.getChatStyle().setColor(color);
		chat.getChatStyle().setBold(true);
		return chat;
	}

	private static void registerColors() {
		colors.put("red", new ColorBlock(0xFF0000, Blocks.redstone_block));
		colors.put("green", new ColorBlock(0x00FF00, Blocks.emerald_block));
		colors.put("blue", new ColorBlock(0x0000FF, Blocks.lapis_block));
		colors.put("cyan", new ColorBlock(0x00FFFF, Blocks.diamond_block));
		colors.put("yellow", new ColorBlock(0xFFFF00, Blocks.gold_block));
		colors.put("purple", new ColorBlock(0xFF00FF, Blocks.portal));
		colors.put("white", new ColorBlock(0xFFFFFF, Blocks.quartz_block));
		colors.put("black", new ColorBlock(0x000000, Blocks.obsidian));
	}
}
