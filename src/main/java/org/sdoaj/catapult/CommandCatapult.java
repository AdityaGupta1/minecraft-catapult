package org.sdoaj.catapult;

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
		return "/catapult <angle> <power>";
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length != 2) {
			sender.addChatMessage(
					new ChatComponentText(Main.redText + "Invalid arguments! Specify the angle and power as "
							+ Main.redText + "decimal numbers using \"" + getCommandUsage(sender) + "\"."));
			return;
		}
		
		try {
			Main.angle = Double.parseDouble(args[0]);
			Main.power = Double.parseDouble(args[1]);
		} catch (NumberFormatException exception) {
			// 2x Main.redText because the second line of the chat message is
			// not colored otherwise
			sender.addChatMessage(
					new ChatComponentText(Main.redText + "Invalid arguments! Specify the angle and power as "
							+ Main.redText + "decimal numbers using \"" + getCommandUsage(sender) + "\"."));
			return;
		}

		if (!Main.parametersSet) {
			Main.parametersSet = true;
		}

		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "Angle: "
				+ Main.angle + " degrees, Power: " + Main.power));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if (sender instanceof EntityPlayer) {
			return true;
		}
		return false;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}

}
