package com.enderzombi102.MinigameParadise;

import org.bukkit.plugin.java.JavaPlugin;

import com.enderzombi102.MinigameParadise.commands.CommandListPlayers;
import com.enderzombi102.MinigameParadise.commands.CommandMode;
import com.enderzombi102.MinigameParadise.modes.ModeBase;

public class MinigameParadise extends JavaPlugin {
	public static MinigameParadise instance;
	public static ModeBase currentMode = null;
	public static String version = "1.0.0";
	public static boolean editWorld;
	public MinigameParadise() {
		super();
		MinigameParadise.instance = this;
		Util.setupLists();
	}
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
		editWorld = this.getConfig().getBoolean("editWorld");
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
    	MinigameParadise.currentMode.stop();
    	MinigameParadise.currentMode = null;
    }
}
