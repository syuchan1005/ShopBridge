package com.github.syuchan1005.shopbridge;

import com.sk89q.worldedit.BlockVector;
import nl.evolutioncoding.areashop.events.notify.UnrentedRegionEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.Shop.Shop;
import org.maxgamer.quickshop.Shop.ShopManager;


/**
 * Created by syuchan on 2016/06/18.
 */
public class ShopBridge extends JavaPlugin implements Listener {
	private static ShopManager shopManager;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getLogger().info("ShopBrodgeが起動しました");
		this.getLogger().info("AreaShop - connect - QuickShop");
		Plugin plugin = this.getServer().getPluginManager().getPlugin("QuickShop");
		if(plugin == null) {
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		shopManager = ((QuickShop) plugin).getShopManager();
	}

	@EventHandler
	public void onUnrent(UnrentedRegionEvent event) {
		BlockVector max = event.getRegion().getRegion().getMaximumPoint();
		BlockVector min = event.getRegion().getRegion().getMinimumPoint();
		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
				for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
					Location loc = new Location(event.getRegion().getWorld(), x, y, z);
					Shop shop = shopManager.getShop(loc);
					if(shop != null) shopManager.removeShop(shop);
				}
			}
		}
	}

}
