package net.aerenserve.minesql;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * MineSQLAPI v1.3
 * 
 * An API to make working with MySQL and Bukkit (and Bungeecord!) quick and easy.
 *
 * You may use this code according to these conditions:
 *
 * 1. No warranty is given or implied.
 * 2. All damage is your own responsibility.
 * 3. You provide credit publicly to the original source should you release the source within a project.
 * 
 * @author hatten33
 */


public class MineSQLAPI extends JavaPlugin {
	
	static String VERSION = "1.3";
	
	@Override
	public void onEnable() {
		getLogger().info("MineSQLAPI v" + VERSION + " hatten33 enabled");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("MineSQLAPI v" + VERSION + " by hatten33 disabled");
	}
	
	
}
