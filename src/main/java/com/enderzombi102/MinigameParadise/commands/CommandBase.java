package com.enderzombi102.MinigameParadise.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.enderzombi102.MinigameParadise.LogHelper;
import com.enderzombi102.MinigameParadise.MinigameParadise;
import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class CommandBase extends Command {

	private boolean useDifferentHandlers;
	private boolean hadToUseFallback;

	/**
	 * default constructor for this abstract class, with this you'll always use a single handler: onCommand
	 * @param name the command name
	 */
	protected CommandBase(String name) {
		this( name, false );
	}

	/**
	 * costructs the CommandBase class, setting up the command name and if it should use different handlers
	 * for entities and players
	 * @param name the command name
	 * @param useDifferentHandlers true if should use different handlers to handle the command (player / entity)
	 */
	protected CommandBase(String name, boolean useDifferentHandlers) {
		super( name );
		this.useDifferentHandlers = useDifferentHandlers;
	}

	@Override
	public abstract @NotNull String getUsage();

	/**
     * Executes this command.
     * <br>
     * if a WrongUsageException is thrown, the usage text of this command
     * (if defined) will be sent to the entity.
     *
     * @param server the server object
     * @param sender Source of the command
     * @param args Passed command arguments
     * @param alias Alias of the command which was used
     */
	public abstract void onCommand(Server server, CommandSender sender, String[] args, String alias ) throws CommandException;

	/**
	 * Executes this command, only by players.
	 * <br>
	 * if a WrongUsageException is thrown, the usage text of this command
	 * (if defined) will be sent to the player.
	 *
	 * @param server the server object
	 * @param sender source of the command
	 * @param args passed command arguments
	 * @param alias alias of the command which was used
	 */
	public abstract void onPlayer(Server server, Player sender, String[] args, String alias) throws CommandException;

	/**
	 * Executes this command, only by entities (not players).
	 * <br>
	 * if a WrongUsageException is thrown, the usage text of this command
	 * (if defined) will be sent to the player.
	 *
	 * @param server the server object
	 * @param sender source of the command
	 * @param args passed command arguments
	 * @param alias alias of the command which was used
	 */
	public abstract void onEntity(Server server, Entity sender, String[] args, String alias) throws CommandException;


	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
		// execute the command
		try {
			if ( this.useDifferentHandlers ) {
				if (sender instanceof Player) this.onPlayer( sender.getServer(), (Player) sender, args, alias );
				else this.onEntity( sender.getServer(), (Entity) sender, args, alias );
			}
			else this.onCommand(Bukkit.getServer(), sender, args, alias);
		} catch (WrongUsageException e) {
			sender.sendMessage( ChatColor.RED + this.getUsage() );
		} catch( CommandException e) {
			// send the error message to the player only if the config says so
			if  ( MinigameParadise.instance.getConfig().getBoolean("sendCommandErrorMessage") ) {
				sender.sendMessage( ChatColor.RED + "An error occurred while executing this command:" );
				sender.sendMessage( ChatColor.RED + Arrays.toString( e.getStackTrace() ) );
			}
			// log the exception
			LogHelper.Error("Captured exception while executing " + this.getClass().getSimpleName() + ".\n" + e.getStackTrace() );
		}
		// let spigot handle the rest
		return true;
	}

	public void register() {
		this.hadToUseFallback = Bukkit.getServer().getCommandMap().register("mgp", this );
	}

	@SneakyThrows
	public void unregister() {
		String name = "";
		if (this.hadToUseFallback) name = "mgp:";
		name = name.concat( this.getName() );
		if ( Bukkit.getServer().getCommandMap().getKnownCommands().remove(name, this) ) {
			this.getClass().getDeclaredField("commandMap" ).set(this, null);
		}
	}
	
	/**
     * Returns true if the given substring is exactly equal to the start of the given string (case insensitive).
     */
    public static boolean doesStringStartWith(String original, String region) {
        return region.regionMatches( true, 0, original, 0, original.length() );
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
        List<String> list = new ArrayList<>();

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
