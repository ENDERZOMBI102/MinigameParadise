package com.enderzombi102.MinigameParadise.commands;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.enderzombi102.MinigameParadise.LogHelper;
import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.google.common.base.Functions;
import com.google.common.collect.Iterables;

public abstract class CommandBase implements TabExecutor {
	
	protected abstract String getUsage();

	/**
     * Executes this command.
     * <br>
     * if a WrongUsageException is thrown, the usage text of this command
     * (if defined) will be sent to the player.
     *
     * @param server the server object
     * @param sender Source of the command
     * @param args Passed command arguments
     * @param alias Alias of the command which was used
     */
	public abstract void execute(Server server, CommandSender sender, String[] args, String alias ) throws CommandException;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// execute the command
		try {
			this.execute(Bukkit.getServer(), sender, args, label);
		} catch (WrongUsageException e) {
			sender.sendMessage( ChatColor.RED + this.getUsage() );
		} catch( CommandException e) {
			// send the error message to the player only if the config says so
			if  ( MinigameParadise.instance.getConfig().getBoolean("sendCommandErrorMessage") ) {
				sender.sendMessage( ChatColor.RED + "An error occurred while executing the command:" );
				sender.sendMessage( ChatColor.RED + Arrays.toString( e.getStackTrace() ) );
			}
			// log the exception
			LogHelper.Error("Captured exception while executing " + this.getClass().getSimpleName() + ".\n" + e.getStackTrace() );
		}
		// let spigot handle the rest
		return true;
	}
	
	/**
     * Returns true if the given substring is exactly equal to the start of the given string (case insensitive).
     */
    public static boolean doesStringStartWith(String original, String region) {
        return region.regionMatches(true, 0, original, 0, original.length());
    }

    /**
     * Returns a List of strings (chosen from the given strings) which the last word in the given string array is a
     * beginning-match for. (Tab completion).
     */
    public static List<String> getListOfStringsMatchingLastWord(String[] args, String... possibilities) {
        return getListOfStringsMatchingLastWord(args, Arrays.asList(possibilities));
    }

    public static List<String> getListOfStringsMatchingLastWord(String[] inputArgs, Collection<?> possibleCompletions) {
        String input = inputArgs[inputArgs.length - 1];
        List<String> list = new ArrayList<String>();

        if (!possibleCompletions.isEmpty())
        {
            for (String completion : Iterables.transform( possibleCompletions, Functions.toStringFunction() ) )
            {
                if (doesStringStartWith(input, completion))
                {
                    list.add(completion);
                }
            }
        }

        return list;
    }
}
