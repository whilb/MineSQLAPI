package net.aerenserve.minesql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class Database {
	
	protected Logger logger;

	protected Database(Logger logger) {
		this.logger = logger;
	}
	
	public abstract ResultSet querySQL(String query) throws SQLException;
	
	public abstract void updateSQL(String update) throws SQLException;
	
	public abstract Connection getConnection();
	
	public abstract void closeConnection();
}
