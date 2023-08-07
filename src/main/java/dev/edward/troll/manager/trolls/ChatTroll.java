package dev.edward.troll.manager.trolls;

import dev.edward.troll.manager.PlayerTroll;
import dev.edward.troll.manager.Troll;
import dev.edward.troll.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ChatTroll implements Troll, Listener {

    private final Random random = new Random();

    private final String[] predefinedChat = new String[] {
            "When you use the parabola of the paella you find that GitHub is better than GitLab",
            "I like to eat pizza with pineapple and ketchup",
            "We mustn't forget it's been raining",
            "Hop out the 4 door with the 44 it was 1, 2, 3 & 4",
            "I'm a big fan of the 1975",
            "Eating a banana with the peel is the best way to eat it",
            "Bread is better than rice",
            "I like to eat my cereal with water",
            "I like to eat my cereal with orange juice",
            "Due to the moons radius in relation to Mars' gravity, we can assume that Liverpool will win the league"
    };

    private final Map<UUID, Long> chatTrollMap = new HashMap<>();

    @Override
    public String getName() {
        return ChatColor.GOLD + "Chat Control";
    }

    @Override
    public String getRawName() {
        return "Chat Control";
    }

    @Override
    public String getPermission() {
        return "troll.trolls.chat";
    }

    @Override
    public String[] getDescription() {
        return new String[] {ChatColor.LIGHT_PURPLE + "Change your targets chat into predefined chat.",
                ChatColor.LIGHT_PURPLE + "Auto expires after 30 seconds."
        };
    }

    @Override
    public void execute(PlayerTroll playerTroll) {
        chatTrollMap.put(playerTroll.getTarget(), System.currentTimeMillis());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent asyncPlayerChatEvent) {
        if (!chatTrollMap.containsKey(asyncPlayerChatEvent.getPlayer().getUniqueId())) return;

        long time = chatTrollMap.get(asyncPlayerChatEvent.getPlayer().getUniqueId());
        if (System.currentTimeMillis() - time > 30000) {
            chatTrollMap.remove(asyncPlayerChatEvent.getPlayer().getUniqueId());
            return;
        }

        asyncPlayerChatEvent.setCancelled(true);
        Bukkit.broadcastMessage("<" + asyncPlayerChatEvent.getPlayer().getName() + "> " + predefinedChat[random.nextInt(predefinedChat.length)]);
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemBuilder(Material.BOOK).setName(getName()).setLore(getDescription()).toItemStack();
    }
}
