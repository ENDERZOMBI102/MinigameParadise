package com.enderzombi102.MinigameParadise.modes.teletimer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

class TeleTimerTimer extends BukkitRunnable {

	private final int time;
	private int counter;

    public TeleTimerTimer(int time) {
    	this.time = time;
    	this.counter = time;
    }

    @Override
    public void run() {
        // What you want to schedule goes here
        if ( counter <= 3  && counter != 0) {
            Bukkit.getServer().broadcastMessage("Teleport in " + counter--);
        } else if ( counter == 0 ) {
        	this.counter = this.time;
        	TeleTimer.instance.teleport();
        } else {
        	counter--;
        }
    }
}

