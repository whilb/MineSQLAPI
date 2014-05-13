package net.aerenserve.minesql;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * MineSQLAPI v1.4
 * 
 * An API to make working with MySQL and Bukkit (and Bungeecord! No, not Bungeecord. :( ) quick and easy.
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
	
	public MineSQL defaultMineSQL = null;
	
	@Override
	public void onEnable() {	
		saveDefaultConfig();

		String host = getConfig().getString("database.ip");
		String port = getConfig().getString("database.port");
		String database = getConfig().getString("database.dbname");
		String user = getConfig().getString("database.user");
		String pass = getConfig().getString("database.pass");
		
		try {
			this.defaultMineSQL = new MineSQL(this.getLogger(), host, port, database, user, pass);
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			this.defaultMineSQL = null;
		}
		
		getLogger().info("MineSQLAPI by hatten33 enabled");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("MineSQLAPI by hatten33 disabled");
	}

	public boolean hasDefault() {
		return getDefault() != null;
	}
	
	public MineSQL getDefault() {
		return this.defaultMineSQL;
	}
	
}
