package dev.edward.troll.manager;

import dev.edward.troll.TrollPlugin;
import dev.edward.troll.manager.trolls.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrollManager {

    private final TrollPlugin trollPlugin;

    private final Map<String, Troll> trollMap = new HashMap<>();

    private final Map<UUID, PlayerTroll> playerTrollMap = new HashMap<>();

    public TrollManager(TrollPlugin trollPlugin) {
        this.trollPlugin = trollPlugin;

        SmiteTroll smiteTroll = new SmiteTroll();
        trollMap.put(smiteTroll.getName(), smiteTroll);
        registerEvents(smiteTroll);

        ChatTroll chatTroll = new ChatTroll();
        trollMap.put(chatTroll.getName(), chatTroll);
        registerEvents(chatTroll);

        EmptyInventoryTroll emptyInventoryTroll = new EmptyInventoryTroll();
        trollMap.put(emptyInventoryTroll.getName(), emptyInventoryTroll);
        registerEvents(emptyInventoryTroll);

        MultiplyTroll multiplyTroll = new MultiplyTroll();
        trollMap.put(multiplyTroll.getName(), multiplyTroll);
        registerEvents(multiplyTroll);

        RespawnTroll respawnTroll = new RespawnTroll();
        trollMap.put(respawnTroll.getName(), respawnTroll);

        ElderGuardianTroll elderGuardianTroll = new ElderGuardianTroll();
        trollMap.put(elderGuardianTroll.getName(), elderGuardianTroll);
    }

    private void registerEvents(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, trollPlugin);
    }

    public Troll getTroll(String name) {
        return trollMap.get(name);
    }

    public Map<String, Troll> getTrollMap() {
        return trollMap;
    }

    public void initiateTroll(UUID troller, UUID target) {
        playerTrollMap.put(troller, new PlayerTroll(troller, target));
    }

    public void initiateTroll(UUID troller, UUID target, Troll troll) {
        playerTrollMap.put(troller, new PlayerTroll(troller, target, troll));
    }

    public PlayerTroll getPlayerTroll(UUID troller) {
        return playerTrollMap.get(troller);
    }

    public void removeTroll(UUID troller) {
        playerTrollMap.remove(troller);
    }

    public void recordTroll(PlayerTroll playerTroll) {
        FileConfiguration fileConfiguration = trollPlugin.getFileConfiguration();

        fileConfiguration.set("trolls." + playerTroll.getTroller().toString() + ".target", playerTroll.getTarget().toString());
        fileConfiguration.set("trolls." + playerTroll.getTroller().toString() + ".target.troll", playerTroll.getTroll().getName());
        fileConfiguration.set("trolls." + playerTroll.getTroller().toString() + ".target.time", System.currentTimeMillis());

        try {
            fileConfiguration.save(trollPlugin.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
