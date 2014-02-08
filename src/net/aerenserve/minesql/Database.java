package net.aerenserve.minesql;

import java.sql.Connection;

import org.bukkit.plugin.Plugin;

public abstract class Database {

	protected Plugin plugin;

	protected Database(Plugin plugin) {
		this.plugin = plugin;
	}

	public abstract Connection openConnection();
	
	public abstract boolean checkConnection();

	public abstract Connection getConnection();

	public abstract void closeConnection();
}
