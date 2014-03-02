package de.lucawimmer.slsjoin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

	private static final main instance = new main();
	public SimpleConfigManager manager;
	public static SimpleConfig config;

	@Override
	public void onEnable() {
		this.manager = new SimpleConfigManager(this);
		File file = new File(getDataFolder() + File.separator + "config.yml");
		if(!file.exists()){
		String[] header = {"SLSJoin", "Developed and written by", "Luca Wimmer (v1.0)", "skylands.eu:25565"};
	    	this.config = manager.getNewConfig("config.yml", header);
	    	this.config.set("onjoin", "true", "Should the joinmessage displayed when a new player join?");
	        this.config.set("joinmessage", "&b&l[SLS] &6&lWillkommen {player} auf SkyLands! &7[{counter}]", "Available variables are {online}, {max}, {player}, {displayname}, {time}, {counter}");
	        this.config.saveConfig();
			getLogger().info(config.getString("Configuration created successfully"));
		} else {
			this.config = manager.getNewConfig("config.yml");
		}
    	
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
                try {
                    Metrics metrics = new Metrics(this);
                    metrics.start();
                } catch (IOException e) {}
	}
		
	public static final main getPlugin() {
			return instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String prefix = ChatColor.AQUA + "" + ChatColor.BOLD + "[" + "SLS] ";
		if (cmd.getName().equalsIgnoreCase("slsjoin")) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("stats")) {
					if(sender instanceof Player) {
						Player player = (Player) sender;
						if(player.hasPermission("slsjoin.stats")) {
							int countoffline = Bukkit.getOfflinePlayers().length;
							player.sendMessage(prefix + ChatColor.GREEN + "Total players played on server: " + Integer.toString(countoffline) + ".");
							return true;
						} else {
							player.sendMessage(prefix + ChatColor.RED + "You don't have permission to acces this command.");
							return true;
						}
					}  else if(sender instanceof ConsoleCommandSender) {
						int countoffline = Bukkit.getOfflinePlayers().length;
						Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "Total players played on server: " + Integer.toString(countoffline) + ".");
						return true;
					}
				} else if(args[0].equalsIgnoreCase("reload")) {
					if(sender instanceof Player) {
						Player player = (Player) sender;
						if(player.hasPermission("slsjoin.reload")) {
							this.config.reloadConfig();
							player.sendMessage(prefix + ChatColor.GREEN + "The configuration file has been successfully reloaded.");
							return true;
						} else {
							player.sendMessage(prefix + ChatColor.RED + "You don't have permission to acces this command.");
							return true;
						}
					}  else if(sender instanceof ConsoleCommandSender) {
						this.config.reloadConfig();
						Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "The configuration file has been successfully reloaded.");
						return true;
					}
				} else {
					if(sender instanceof Player) {
						Player player = (Player) sender;
						player.sendMessage(prefix + ChatColor.RED + "Syntax: /slsjoin stats/reload");
					} else if(sender instanceof ConsoleCommandSender) {
						Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + "Syntax: /slsjoin stats/reload");
					}
					return true;
				}
			} else {
				if(sender instanceof Player) {
					Player player = (Player) sender;
					player.sendMessage(prefix + ChatColor.RED + "Syntax: /slsjoin stats/reload");
				} else if(sender instanceof ConsoleCommandSender) {
					Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + "Syntax: /slsjoin stats/reload");
				}
				return true;
			}
		}
		return false;
	}
}
