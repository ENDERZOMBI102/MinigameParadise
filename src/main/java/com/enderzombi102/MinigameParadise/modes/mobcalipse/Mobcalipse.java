package com.enderzombi102.MinigameParadise.modes.mobcalipse;

import com.enderzombi102.MinigameParadise.Util;
import com.enderzombi102.MinigameParadise.modes.ModeBase;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.HandlerList;

public class Mobcalipse extends ModeBase {

	static Mobcalipse instance;
	private final MobcalipseListener listener;
	private Difficulty oldDifficulty;
	private int oldSpawnLimit;
	private boolean oldDoLightCycle;

	public Mobcalipse() {
		broadcastPrefixedMessage("starting the Mobcalipse!");
		this.listener = new MobcalipseListener();
		Util.registerListener(this.listener);
		instance = this;
		broadcastPrefixedMessage("preparing the world..");
		this.setupWorld();
		broadcastPrefixedMessage("the Mobcalipse is started! good luck!");
	}

	public void setupWorld() {
		oldDifficulty = Bukkit.getWorlds().get(0).getDifficulty();
		oldSpawnLimit = Bukkit.getWorlds().get(0).getMonsterSpawnLimit();
		oldDoLightCycle = Bukkit.getWorlds().get(0).getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
		logger.info("saved previously used values:");
		logger.info(" - difficulty: " + oldDifficulty.name() );
		logger.info(" - doLightCycle: " + oldDoLightCycle );
		logger.info(" - monster spawn limit: " + oldSpawnLimit );
		for ( World world : Bukkit.getWorlds() ) {
			world.setDifficulty(Difficulty.PEACEFUL);
			world.setDifficulty(Difficulty.NORMAL);
			world.setTime(18000);
			world.setMonsterSpawnLimit(300);
			world.setGameRule( GameRule.DO_DAYLIGHT_CYCLE, false );
		}
		logger.info("updated all worlds");
	}

	@Override
	public void stop() {
		HandlerList.unregisterAll(this.listener);
		instance = null;
		for ( World world : Bukkit.getWorlds() ) {
			world.setDifficulty(oldDifficulty);
			world.setMonsterSpawnLimit(oldSpawnLimit);
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, oldDoLightCycle);
			logger.info("restored previous world values");
		}
	}
}
