package net.aerenserve.minesql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

public class MineSQL extends Database {

	private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;

    private Connection connection;

    /**
     * Creates a new MySQL database instance
     * 
     * @param plugin - Plugin instance
     * @param hostname - Database Server hostname         
     * @param port - Database Port
     * @param database - Database name
     * @param username - Username
     * @param password - Password
     */
    
    public MineSQL(Plugin plugin, String hostname, String port, String database, String username, String password) {
        super(plugin);
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
        this.connection = null;
    }
    
    /**
     * Opens a new connection to your MySQL database
     * 
     * @return Connection
     */
    
    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.user, this.password);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to MySQL server! because: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
        }
        return connection;
    }
    
    public Connection getConnection() {
    	return connection;
    }

    public boolean checkConnection() {
    	if(connection != null) return true; 
    	else return false;
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; 
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Error closing the MySQL Connection!");
                e.printStackTrace();
            }
        } else {
        	plugin.getLogger().log(Level.SEVERE, "There is no connection to close!");
        }
    }
    
    /**
     * Method to get a ResultSet from querying the MySQL database
     * 
     * @param query
     * @return A ResultSet from the query.
     * @throws SQLException
     */
    
    public ResultSet querySQL(String query) throws SQLException {
    	Connection c = connection;
    	ResultSet res = null;
        PreparedStatement statement = null;
        
        if(c == null) {
            c = openConnection();
        }
        
        try {
            c.setAutoCommit(false);   
			statement = c.prepareStatement(query);
			res = statement.executeQuery();
			c.commit();
		} catch (SQLException e) {
			e.printStackTrace();
	        if (c != null) {
	            try {
	                plugin.getLogger().log(Level.SEVERE, "Query is being rolled back");
	                c.rollback();
	            } catch(SQLException excep) {
	            	excep.printStackTrace();
	            }
	        }
		} finally {
	        if (statement != null) {
	            statement.close();
	            statement = null;
	        }
	        c.setAutoCommit(true);
	    }
       
        return res;
    }
    
    /**
     * Sends a statement to the database.
     * 
     * @param update  The statement to send to the database
     * @throws SQLException
     */

    public void updateSQL(String update) throws SQLException {
    	Connection c = connection;
        PreparedStatement statement = null;
        
        if(c == null) {
            c = openConnection();
        }
        
        try {
            c.setAutoCommit(false);   
			statement = c.prepareStatement(update);
			statement.execute();
			c.commit();
		} catch (SQLException e) {
			e.printStackTrace();
	        if (c != null) {
	            try {
	                plugin.getLogger().log(Level.SEVERE, "Update is being rolled back");
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
       
        return;
    }
}
