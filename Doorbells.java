package me.flame.doorbells;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Doorbells extends JavaPlugin implements CommandExecutor, Listener {

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		loadConfig();
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("doorbells") || cmd.getName().equalsIgnoreCase("doorbell") || cmd.getName().equalsIgnoreCase("db")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] " + ChatColor.WHITE
						+ "Commands can only be used by players");
				return false;
			}
			Block block = ((Player) sender).getTargetBlock(null, 100);
			Material material = block.getType();
			Location b_loc = block.getLocation();
			
			int radius = getConfig().getInt("doorbell_distance");
			Boolean validsound = false;
			Boolean door = false;
			
			String doorbell = getConfig()
					.getString("Doorbell_" + b_loc.getBlockX() + "_" + b_loc.getBlockY() + "_" + b_loc.getBlockZ());


			if (args.length == 0) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.STRIKETHROUGH
						+ "                                                                                ");
				sender.sendMessage(centerText(ChatColor.WHITE + "Doorbells"));
				sender.sendMessage(centerText(ChatColor.GRAY + "Version 1.6"));
				sender.sendMessage("");
				sender.sendMessage(centerText(ChatColor.WHITE + "Commands"));
				sender.sendMessage(centerText(ChatColor.LIGHT_PURPLE + "/doorbells set"));
				sender.sendMessage(centerText(ChatColor.DARK_PURPLE + "Used to set a doorbell"));
				sender.sendMessage(
						centerText(ChatColor.DARK_PURPLE + "Look at a button next to a door and type this command"));
				sender.sendMessage("");
				sender.sendMessage(centerText(ChatColor.LIGHT_PURPLE + "/doorbells remove"));
				sender.sendMessage(centerText(ChatColor.DARK_PURPLE + "Used to remove a doorbell"));
				sender.sendMessage(
						centerText(ChatColor.DARK_PURPLE + "Look at a button next to a door and type this command"));
				sender.sendMessage("");
				sender.sendMessage(centerText(ChatColor.LIGHT_PURPLE + "/doorbells list"));
				sender.sendMessage(
						centerText(ChatColor.DARK_PURPLE + "Used to list the sounds available for doorbells"));
				sender.sendMessage("");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.STRIKETHROUGH
						+ "                                                                                ");
				return true;
			}

			if (args[0].equalsIgnoreCase("set")) {
				if (material != xmaterial.STONE_BUTTON.parseMaterial() && material != xmaterial.BIRCH_BUTTON.parseMaterial()
						&& material != xmaterial.DARK_OAK_BUTTON.parseMaterial() && material != xmaterial.JUNGLE_BUTTON.parseMaterial()
						&& material != xmaterial.OAK_BUTTON.parseMaterial() && material != xmaterial.SPRUCE_BUTTON.parseMaterial()
						&& material != xmaterial.ACACIA_BUTTON.parseMaterial()) {
					
				sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] "
						+ ChatColor.WHITE + "Doorbells can only be set by looking at a button");
				return true;
				}
				
				List<String> list = this.getConfig().getStringList("doorbell_sounds");
				if (!sender.hasPermission("doorbells.set") && (!(sender.hasPermission("doorbells.*")))) {
					sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE
							+ "] You do not have permission to set doorbells");
					return false;
				}
					for (int x = -radius; x <= radius; x++) {
						for (int y = -radius; y <= radius; y++) {
							for (int z = -radius; z <= radius; z++) {
								if (block.getRelative(x, y, z).getType() == xmaterial.ACACIA_DOOR.parseMaterial()
										|| block.getRelative(x, y, z).getType() == xmaterial.BIRCH_DOOR.parseMaterial()
										|| block.getRelative(x, y, z).getType() == xmaterial.DARK_OAK_DOOR.parseMaterial()
										|| block.getRelative(x, y, z).getType() == xmaterial.IRON_DOOR.parseMaterial()
										|| block.getRelative(x, y, z).getType() == xmaterial.JUNGLE_DOOR.parseMaterial()
										|| block.getRelative(x, y, z).getType() == xmaterial.OAK_DOOR.parseMaterial()
										|| block.getRelative(x, y, z).getType() == xmaterial.SPRUCE_DOOR.parseMaterial()) {
									door = true;
									if (doorbell != null) {
										sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE
												+ "] " + ChatColor.WHITE + "Doorbell already set at this location");
										return false;
									} else {
										
										if (!(args.length == 2) && doorbell == null) {
											sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] "
													+ ChatColor.WHITE + "Usage /doorbell set [Sound] - Find the sound using /doorbell list");
											return false;
										}
										
										for (String doorbellsound : list) {
											if (doorbellsound.equals(args[1])) {
												validsound = true;
											}
										}
										if (validsound == false) {
											sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] "
													+ ChatColor.WHITE + "This sound is not valid, use /doorbell list to find sounds");
											return false;
										}
											
										getConfig().set("Doorbell_" + b_loc.getBlockX() + "_" + b_loc.getBlockY() + "_"
												+ b_loc.getBlockZ(), args[1]);
										saveConfig();
										reloadConfig();
										sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE
												+ "] " + ChatColor.WHITE + "Set doorbell at " + b_loc.getBlockX() + ","
												+ b_loc.getBlockY() + "," + b_loc.getBlockZ() + " with the sound "
												+ args[1]);
										return false;
									}
									
								}
							}
						}
					}
					if (door == false) {
						sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells"
								+ ChatColor.WHITE + "] " + ChatColor.WHITE
								+ "Doorbell has to be within " + radius + " blocks of a door");
						return false;
					}
			}
			
			if (args[0].equalsIgnoreCase("remove")) {
				if (material != xmaterial.STONE_BUTTON.parseMaterial() && material != xmaterial.BIRCH_BUTTON.parseMaterial()
						&& material != xmaterial.DARK_OAK_BUTTON.parseMaterial() && material != xmaterial.JUNGLE_BUTTON.parseMaterial()
						&& material != xmaterial.OAK_BUTTON.parseMaterial() && material != xmaterial.SPRUCE_BUTTON.parseMaterial()
						&& material != xmaterial.ACACIA_BUTTON.parseMaterial()) {
					
				sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] "
						+ ChatColor.WHITE + "Doorbells can only be set by looking at a button");
				return true;
				}
				if (!sender.hasPermission("doorbells.remove") && (!(sender.hasPermission("doorbells.*")))) {
					sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE
							+ "] You do not have permission to remove doorbells");
					return false;
				}

				if (doorbell != null) {
					getConfig().set("Doorbell_" + b_loc.getBlockX() + "_" + b_loc.getBlockY() + "_" + b_loc.getBlockZ(),
							null);
					sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] "
							+ ChatColor.WHITE + "Doorbell has been removed");
					return false;
				} else {
					sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] "
							+ ChatColor.WHITE + "This is not a doorbell");
					return false;
				}
			}
			
			if (args[0].equalsIgnoreCase("list")) {
				if (!sender.hasPermission("doorbells.list") && (!(sender.hasPermission("doorbells.*")))) {
					sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE
							+ "] You do not have permission to list doorbell sounds");
					return false;
				}

				sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.STRIKETHROUGH
						+ "                                                                                ");
				sender.sendMessage(centerText(ChatColor.WHITE + "Sounds for Doorbells"));
				sender.sendMessage("");
				if (sender.hasPermission("doorbells.*")) {
					sender.sendMessage(centerText(ChatColor.WHITE + "Admin message" + ChatColor.GRAY
							+ " - You can add more sounds in the config"));

					sender.sendMessage(centerText(
							ChatColor.GRAY + "Use https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html"));
					sender.sendMessage("");
				}
				List<String> list = this.getConfig().getStringList("doorbell_sounds");
				for (String doorbellsound : list) {

					sender.sendMessage(centerText(ChatColor.LIGHT_PURPLE + "- " + doorbellsound));
				}

				sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.STRIKETHROUGH
						+ "                                                                                ");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("doorbells.reload") && (!(sender.hasPermission("doorbells.*")))) {
					sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE
							+ "] You do not have permission to reload the plugin");
					return false;
				}

				sender.sendMessage(
						"[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] Reloading plugin...");
				reloadConfig();
				sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] Plugin reloaded");
				return true;
			}

		sender.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE + "] Unknown command use /doorbells for help");
		}
		return false;
	}

	@EventHandler
	public void onButtonPress(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (!(action.equals(Action.RIGHT_CLICK_BLOCK))) {
			return;
		}
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		Material material = block.getType();
		Location b_loc = block.getLocation();
		int radius = getConfig().getInt("doorbell_distance");
		String doorbell = getConfig()
				.getString("Doorbell_" + b_loc.getBlockX() + "_" + b_loc.getBlockY() + "_" + b_loc.getBlockZ());
		int door = 0;
		if (material == xmaterial.ACACIA_BUTTON.parseMaterial() || material == xmaterial.BIRCH_BUTTON.parseMaterial()
				|| material == xmaterial.DARK_OAK_BUTTON.parseMaterial() || material == xmaterial.JUNGLE_BUTTON.parseMaterial()
				|| material == xmaterial.OAK_BUTTON.parseMaterial() || material == xmaterial.SPRUCE_BUTTON.parseMaterial()
				|| material == xmaterial.STONE_BUTTON.parseMaterial()) {
			for (int x = -radius; x <= radius; x++) {
				for (int y = -radius; y <= radius; y++) {
					for (int z = -radius; z <= radius; z++) {
						if (block.getRelative(x, y, z).getType() == xmaterial.ACACIA_DOOR.parseMaterial()
								|| block.getRelative(x, y, z).getType() == xmaterial.BIRCH_DOOR.parseMaterial()
								|| block.getRelative(x, y, z).getType() == xmaterial.DARK_OAK_DOOR.parseMaterial()
								|| block.getRelative(x, y, z).getType() == xmaterial.IRON_DOOR.parseMaterial()
								|| block.getRelative(x, y, z).getType() == xmaterial.JUNGLE_DOOR.parseMaterial()
								|| block.getRelative(x, y, z).getType() == xmaterial.OAK_DOOR.parseMaterial()
								|| block.getRelative(x, y, z).getType() == xmaterial.SPRUCE_DOOR.parseMaterial()) {
							door++;
							if (door == 1) {
								if (doorbell != null) {
									block.getWorld().playSound(block.getLocation(), xsound.valueOf(doorbell.toUpperCase()).parseSound(), 1, 1);
									//player.playSound(player.getLocation(), Sound.valueOf(doorbell.toUpperCase()), 1, 1);
										
									}
									return;
								}
							
						}
					}
				}
			}
			if (door == 0 && doorbell != null) {
				getConfig().set("Doorbell_" + b_loc.getBlockX() + "_" + b_loc.getBlockY() + "_" + b_loc.getBlockZ(),
						null);
				player.sendMessage("[" + ChatColor.DARK_PURPLE + "Doorbells" + ChatColor.WHITE
						+ "] Doorbell has been automatically removed - not in the " + radius + " block door radius");
				return;
			}
		}
	}

	private String centerText(String text) {
		int maxWidth = 80, spaces = (int) Math.round((maxWidth - 1.4 * ChatColor.stripColor(text).length()) / 2);
		return StringUtils.repeat(" ", spaces) + text;
	}
}
