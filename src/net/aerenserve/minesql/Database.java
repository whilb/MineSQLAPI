package net.aerenserve.minesql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

public abstract class Database {

	protected Plugin plugin;

	protected Database(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public abstract ResultSet querySQL(String query) throws SQLException;
	
	public abstract void updateSQL(String update) throws SQLException;
	
	public abstract Connection getConnection();
	
	public abstract void closeConnection();
}
