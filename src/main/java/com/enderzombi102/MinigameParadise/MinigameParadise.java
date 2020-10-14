package com.enderzombi102.MinigameParadise;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import com.enderzombi102.MinigameParadise.commands.CommandListPlayers;
import com.enderzombi102.MinigameParadise.commands.CommandMode;
import com.enderzombi102.MinigameParadise.modes.ModeBase;

public class MinigameParadise extends JavaPlugin {
	public static MinigameParadise instance;
	public static ArrayList<ModeBase> activeModes = new ArrayList<>();
	public static String version = "1.0.0";
	public static boolean editWorld;
	public MinigameParadise() {
		super();
		MinigameParadise.instance = this;
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
		LogHelper.Info("loading MinigameParadise v"+version+"!" );
		this.getCommand("mode").setExecutor( new CommandMode() );
		this.getCommand("listplayers").setExecutor( new CommandListPlayers() );
		LogHelper.Info("finished loading MinigameParadise!");
    }
	
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
		for (ModeBase mode : activeModes) {
			mode.stop();
			activeModes.remove(mode);
		}
    }
}
