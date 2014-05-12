package net.aerenserve.minesql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MineSQL extends Database {

	//private List<Statement> openStatement;

	private final String user;
	private final String database;
	private final String password;
	private final String port;
	private final String hostname;

	private Connection connection;

	/**
	 * Creates a new MySQL database instance and opens a connection.
	 * 
	 * @param logger - Optional logger
	 * @param hostname - Database Server hostname         
	 * @param port - Database Port
	 * @param database - Database name
	 * @param username - Username
	 * @param password - Password
	 * @throws SQLException 
	 */

	public MineSQL(Logger logger, String hostname, String port, String database, String username, String password) throws SQLException {
		super(logger);
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = openConnection();
	}
	
	public MineSQL(String hostname, String port, String database, String username, String password) throws SQLException {
		super(Logger.getLogger("Minecraft"));
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
		this.connection = openConnection();
	}

	private Connection openConnection() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "JDBC Driver not found!");
		}
		return connection;
	}

	@Override
	public Connection getConnection() {
		try {
			if(connection == null || !connection.isValid(10)) {
				connection = openConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	@Override
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null; 
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Error closing the MySQL Connection!");
				e.printStackTrace();
			}
		} else {
			logger.log(Level.SEVERE, "There is no connection to close!");
		}
	}

	/**
	 * Method to get a ResultSet from querying the MySQL database
	 * 
	 * @param query
	 * @return A ResultSet from the query.
	 * @throws SQLException
	 */

	@Override
	public ResultSet querySQL(String query) throws SQLException {
		Connection c = getConnection();
		ResultSet res = null;
		PreparedStatement statement = null;
		try {
			c.setAutoCommit(false);   
			statement = c.prepareStatement(query);
			res = statement.executeQuery();
			c.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (c != null) {
				try {
					logger.log(Level.SEVERE, "Query is being rolled back");
					c.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
		} finally {
			/*if (statement != null) {
		        	//openStatement.add(statement);	     
		            statement = null;
		        }*/
			c.setAutoCommit(true);
		}     
		return res;
	}

	/**
	 * May or may not be used.
	 *
	 */

	/*public void queryComplete() {
    	try {
    		Statement s = openStatement.get(0);
	    	if(s != null) {
	    		openStatement.remove(0);
	    		s.close();
	    	}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}   	
    }*/

	/**
	 * Sends a statement to the database.
	 * 
	 * @param update  The statement to send to the database
	 * @throws SQLException
	 */

	@Override
	public void updateSQL(String update) throws SQLException {
		Connection c = getConnection();
		PreparedStatement statement = null;

		try {
			c.setAutoCommit(false);   
			statement = c.prepareStatement(update);
			statement.execute();
			c.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (c != null) {
				try {
					logger.log(Level.SEVERE, "Update is being rolled back");
					c.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			c.setAutoCommit(true);
		}

		closeConnection();

		return;
	}
}
