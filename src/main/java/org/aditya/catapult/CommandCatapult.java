package org.aditya.catapult;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class CommandCatapult implements ICommand {
	private List aliases = new ArrayList();

	public CommandCatapult() {
		aliases.add("catapult");
		aliases.add("cp");
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return null;
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/catapult <angle: number> <power: number> <color: text>";
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length != 3) {
			sender.addChatMessage(Main.createChatMessage("Invalid arguments! Usage: " + getCommandUsage(sender) + "\".",
					EnumChatFormatting.RED));
			return;
		}

		try {
			Main.angle = Double.parseDouble(args[0]);
			Main.shownPower = Double.parseDouble(args[1]);
			// log base 3 of given power
			Main.power = Math.log(Main.shownPower) / Math.log(3);
		} catch (NumberFormatException exception) {
			sender.addChatMessage(Main.createChatMessage("Invalid arguments! Usage: " + getCommandUsage(sender) + "\".",
					EnumChatFormatting.RED));
			return;
		}

		if (!Main.colors.keySet().contains(args[2])) {
			sender.addChatMessage(Main.createChatMessage(
					"Invalid color! Valid colors: " + Main.colors.keySet().toString().replaceAll("[\\[\\]]", "") + ".",
					EnumChatFormatting.RED));
			return;
		}

		Main.color = args[2];

		if (!Main.parametersSet) {
			Main.parametersSet = true;
		}

		sender.addChatMessage(Main.createChatMessage(
				"Angle: " + Main.angle + " degrees, Power: " + Double.parseDouble(args[1]) + ", Color: " + Main.color,
				EnumChatFormatting.AQUA));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (sender instanceof EntityPlayer) {
			return true;
		}
		return false;
	}
}
