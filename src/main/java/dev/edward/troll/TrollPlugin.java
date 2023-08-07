package dev.edward.troll;

import dev.edward.troll.commands.TrollCommand;
import dev.edward.troll.inventory.TrollsInventory;
import dev.edward.troll.listeners.InventoryListener;
import dev.edward.troll.manager.TrollManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TrollPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        TrollManager trollManager = new TrollManager(this);
        TrollsInventory trollsInventory = new TrollsInventory(trollManager);

        getCommand("troll").setExecutor(new TrollCommand(trollManager, trollsInventory));

        getServer().getPluginManager().registerEvents(new InventoryListener(trollManager, trollsInventory), this);

    }
}
