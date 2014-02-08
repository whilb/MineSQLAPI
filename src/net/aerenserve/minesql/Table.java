package net.aerenserve.minesql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Table {

	String name;
	MineSQL minesql;
	
	/**
	 * 
	 * @param minesql 
	 * @param name
	 */

	public Table(MineSQL minesql, String name) {
		this.minesql = minesql;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Method to get the value of a column from the row with the specified ID. 
	 * This assumes your 'PRIMARY KEY AUTO_INCREMENT' is called 'id'.
	 * 
	 * @param rowId  The value of the primary key
	 * @param column  The name of the column from which to retrieve a value
	 * @return The value found at the specified location, or null if it is not found.
	 */

	public String getAt(Integer rowId, String column) {
		ResultSet res = null;
		try {
			res = minesql.querySQL("SELECT * FROM " + name + " WHERE id = " + rowId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(res.next()) {
				if(res.getString(column) != null) {
					return res.getString(column);
				}		
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method to put a value in the column of a row specified by rowId. 
	 * This assumes your 'PRIMARY KEY AUTO_INCREMENT' is called 'id'.
	 * 
	 * @param rowId  The value of the primary key
	 * @param column  The name of the column 
	 * @param value  The value to put into the column
	 */
	
	public void putAt(Integer rowId, String column, String value) {
		try {
			minesql.updateSQL("UPDATE " + name + " SET " + column + "='" + value +"' WHERE id=" + rowId + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
