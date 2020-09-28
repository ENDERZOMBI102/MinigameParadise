package com.enderzombi102.MinigameParadise;

import com.enderzombi102.MinigameParadise.modes.deathswap.DeathSwapListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import com.enderzombi102.MinigameParadise.commands.CommandListPlayers;
import com.enderzombi102.MinigameParadise.commands.CommandStart;
import com.enderzombi102.MinigameParadise.commands.CommandStop;
import com.enderzombi102.MinigameParadise.modes.ModeBase;

import java.util.Map;
import java.util.Set;

public class MinigameParadise extends JavaPlugin {
	public static MinigameParadise instance;
	public static ModeBase currentMode = null;
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
		Bukkit.getPluginManager().registerEvents( new DeathSwapListener(), this );
	}
	
	// Fired when plugin is enabled
	@Override
    public void onEnable() {
		LogHelper.Info("loading MinigameParadise v"+version+"!" );
		this.getCommand("modestart").setExecutor( new CommandStart() );
		this.getCommand("modestop").setExecutor( new CommandStop() );
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
