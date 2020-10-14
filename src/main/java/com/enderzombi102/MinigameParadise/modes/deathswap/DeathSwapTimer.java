package com.enderzombi102.MinigameParadise.modes.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathSwapTimer extends BukkitRunnable {
	
	private int time;
	private int counter = 0;

    public DeathSwapTimer(int time) {
    	this.time = time;
    	this.counter = time;
    }

    @Override
    public void run() {
        // What you want to schedule goes here
        if ( counter <= 10  && counter != 0) { 
            Bukkit.getServer().broadcastMessage("Swapping in " + counter--);
        } else if ( counter == 0 ) {
        	this.counter = this.time;
        	DeathSwap.instance.swap();
        } else {
        	counter--;
        }
    }
}

