package dev.edward.troll.listeners;

import dev.edward.troll.inventory.TrollsInventory;
import dev.edward.troll.manager.PlayerTroll;
import dev.edward.troll.manager.Troll;
import dev.edward.troll.manager.TrollManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    private final TrollManager trollManager;
    private final TrollsInventory trollsInventory;

    public InventoryListener(TrollManager trollManager, TrollsInventory trollsInventory) {
        this.trollManager = trollManager;
        this.trollsInventory = trollsInventory;
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().getName().equals("Online Players")) {

            if (inventoryClickEvent.getCurrentItem() == null) return;

            if (!inventoryClickEvent.getCurrentItem().getItemMeta().hasDisplayName()) return;

            String playerName = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();
            Player target = Bukkit.getPlayer(playerName);
            if (target == null) {
                inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.RED + "Player not found.");
                inventoryClickEvent.getWhoClicked().closeInventory();
                return;
            }

            trollManager.initiateTroll(inventoryClickEvent.getWhoClicked().getUniqueId(), target.getUniqueId());
            inventoryClickEvent.getWhoClicked().closeInventory();
            inventoryClickEvent.getWhoClicked().openInventory(trollsInventory.getTrollsInventory());
        } else if (inventoryClickEvent.getInventory().getName().equals(ChatColor.AQUA + "Trolls")) {
            if (inventoryClickEvent.getCurrentItem() == null) return;

            if (!inventoryClickEvent.getCurrentItem().getItemMeta().hasDisplayName()) return;

            inventoryClickEvent.setCancelled(true);

            String trollName = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName();

            PlayerTroll playerTroll = trollManager.getPlayerTroll(inventoryClickEvent.getWhoClicked().getUniqueId());
            if (playerTroll == null) {
                inventoryClickEvent.getWhoClicked()
                        .sendMessage(ChatColor.RED + "Error Occurred; You are not trolling anyone.");
                inventoryClickEvent.getWhoClicked().closeInventory();
                return;
            }

            Troll troll = trollManager.getTroll(trollName);
            if (troll == null) {
                inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.RED + "Error Occurred; Troll not found.");
                inventoryClickEvent.getWhoClicked().closeInventory();
                return;
            }

            playerTroll.setTroll(troll);
            troll.execute(playerTroll);
            trollManager.recordTroll(playerTroll);

            inventoryClickEvent.getWhoClicked().closeInventory();

            Player sender = (Player) inventoryClickEvent.getWhoClicked();
            sender.sendMessage(ChatColor.GREEN + "You have successfully trolled "
                    + Bukkit.getPlayer(playerTroll.getTarget()).getName() + " with " + troll.getName() + "!");
        }
    }

}
