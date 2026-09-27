package net.firstsky.zahfix;

import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
import fr.maxlego08.zauctionhouse.api.enums.StorageType;
import fr.maxlego08.zauctionhouse.api.event.events.AuctionPostBuyEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ZAHFixListener implements Listener {

    private final ZAHFixPlugin plugin;

    public ZAHFixListener(ZAHFixPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPostBuy(AuctionPostBuyEvent event) {
        Plugin zahPlugin = Bukkit.getPluginManager().getPlugin("zAuctionHouseV3");
        if (!(zahPlugin instanceof ZAuctionPlugin zPlugin)) return;
        if (!zPlugin.isEnabled() || !zPlugin.isReady()) return;

        // Délai de 20 ticks (1s) pour que UpdateRunnable ait le temps d'écrire en DB
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                zPlugin.getAuctionManager().update(StorageType.BUY);
            } catch (Exception e) {
                plugin.getLogger().warning("Erreur refresh achats ZAH: " + e.getMessage());
            }
        }, 20L);
    }
}
