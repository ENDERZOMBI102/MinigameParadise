package com.enderzombi102.MinigameParadise;

import com.enderzombi102.MinigameParadise.commands.CommandListPlayers;
import com.enderzombi102.MinigameParadise.commands.CommandMode;
import com.enderzombi102.MinigameParadise.generalListeners.PlayerEventListener;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MinigameParadise extends JavaPlugin {

	public static MinigameParadise instance;
	public static Logger logger;
	public static final ArrayList<ModeBase> activeModes = new ArrayList<>();
	public static final String version = "1.0.0";
	public static boolean editWorld;

	public MinigameParadise() {
		super();
		instance = this;
		logger = LogManager.getLogger("Minigame Paradise");
	}
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
		editWorld = this.getConfig().getBoolean("editWorld");
		if ( Util.solid.isEmpty() ) Util.setupLists();
	}
	
	// Fired when plugin is enabled
	@Override
    public void onEnable() {
		logger.info("loading MinigameParadise v" + version + "!" );
		this.getCommand("mode").setExecutor( new CommandMode() );
		this.getCommand("listplayers").setExecutor( new CommandListPlayers() );
		Bukkit.getServer().getCommandMap().register("mgp", new Command("kit") {
			@Override
			public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
				if (sender instanceof Player) {
					if ( activeModes.isEmpty() ) {
						Player player = (Player) sender;
						player.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
						player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
					} else {
						broadcastCommandMessage(sender, "Can't use kit when a mode is active");
					}
					return true;
				}
				return false;
			}
		});
		Bukkit.getServer().getCommandMap().register("mgp", new Command("gm1") {
			@Override
			public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
				if (sender instanceof Player && sender.getName().equals("ENDERZOMBI102") ) {
					( (Player) sender ).setGameMode(GameMode.CREATIVE);
					return true;
				}
				return false;
			}
		});
		Bukkit.getServer().getCommandMap().register("mgp", new Command("gm0") {
			@Override
			public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
				if (sender instanceof Player) {
					( (Player) sender ).setGameMode(GameMode.SURVIVAL);
					return true;
				}
				return false;
			}
		});
		Bukkit.getPluginManager().registerEvents( new PlayerEventListener(), this );
		logger.info("finished loading MinigameParadise!");
    }
	
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
		if ( activeModes.size() > 0) {
			for (ModeBase mode : activeModes) {
				mode.stop();
			}
			activeModes.clear();
		}
    }
}
