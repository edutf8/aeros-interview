package dev.edward.troll;

import dev.edward.troll.commands.TrollCommand;
import dev.edward.troll.inventory.PlayersInventory;
import dev.edward.troll.inventory.TrollsInventory;
import dev.edward.troll.listeners.InventoryListener;
import dev.edward.troll.manager.TrollManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class TrollPlugin extends JavaPlugin {

    private File file;
    private FileConfiguration fileConfiguration;


    @Override
    public void onEnable() {
        try {
            file = new File(getDataFolder(), "trolls.yml");

            if (!file.exists())
                file.createNewFile();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);


        TrollManager trollManager = new TrollManager(this);
        PlayersInventory playersInventory = new PlayersInventory();
        TrollsInventory trollsInventory = new TrollsInventory(trollManager);

        getCommand("troll").setExecutor(new TrollCommand(trollManager, playersInventory, trollsInventory));

        getServer().getPluginManager().registerEvents(new InventoryListener(trollManager, trollsInventory), this);

    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    @Override
    public File getFile() {
        return file;
    }
}
