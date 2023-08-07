package dev.edward.troll.commands;

import dev.edward.troll.inventory.PlayersInventory;
import dev.edward.troll.inventory.TrollsInventory;
import dev.edward.troll.manager.TrollManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrollCommand implements CommandExecutor {

    private final TrollManager trollManager;
    private final PlayersInventory playersInventory;
    private final TrollsInventory trollsInventory;

    public TrollCommand(TrollManager trollManager, PlayersInventory playersInventory, TrollsInventory trollsInventory) {
        this.trollManager = trollManager;
        this.playersInventory = playersInventory;
        this.trollsInventory = trollsInventory;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] args) {
        if (!command.getName().equalsIgnoreCase("troll"))
            return false;

        if (!commandSender.hasPermission("troll.command.use")) {
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return false;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length == 0) {
            player.openInventory(playersInventory.getPlayersInventory());
        } else if (args.length == 1) {
            Player target = player.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player not found.");
                return false;
            }

            trollManager.initiateTroll(player.getUniqueId(), target.getUniqueId());
            player.openInventory(trollsInventory.getTrollsInventory());
        }
        return false;
    }
}
