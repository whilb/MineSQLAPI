package net.aerenserve.minesql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Table {

	String name;
	MineSQL minesql;

	/**
	 * Use this constructor if you already have an existing table.
	 * 
	 * @param minesql  An existing MineSQL instance
	 * @param name  The name of the existing table
	 */

	public Table(MineSQL minesql, String name) {
		this.minesql = minesql;
		this.name = name;
	}

	/**
	 * Use this constructor if you want to make a new table.
	 * 
	 * @param minesql  An existing MineSQL instance
	 * @param name  The name of the table.
	 * @param columns  A hashmap used as <Column Name, DataType>. You DO NOT need to include the PRIMARY KEY. Generally, the DataType will probably be text.
	 */

	public Table(MineSQL minesql, String name, HashMap<String, String> columns) {
		this.minesql = minesql;
		this.name = name;

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS `" + name + "` (id int PRIMARY KEY AUTO_INCREMENT");
		Iterator<Entry<String, String>> it = columns.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			sb.append(", " + entry.getKey() + " " + entry.getValue());	        
		}

		sb.append(");");
		String statement = sb.toString();

		try {
			minesql.updateSQL(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public boolean contains(String column, String value) {
		ResultSet res = null;
		try {
			res = minesql.querySQL("SELECT * FROM " + name + " WHERE " + column + " = '" + value + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(res.next()) {
				if(res.getString(column) != null) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Provides a (different/easier/weirder) way to read data out of tables.
	 * 
	 * @return The table represented in an ArrayList<HashMap<String, String>>
	 */

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, String>> openTable() {
		ResultSet res = null;
		ArrayList<HashMap<String, String>> table = new ArrayList<HashMap<String, String>>();
		try {
			res = minesql.querySQL("SELECT * FROM " + name + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			int length = res.getMetaData().getColumnCount();
			HashMap<String, String> tempMap = new HashMap<String, String>();
			while(res.next()) { //For every row in resultset
				for(int n = 1; n <= length; n++) { //For every column in row of resultset
					String k = res.getMetaData().getColumnName(n);
					String v = res.getString(n);
					tempMap.put(k, v);
				}
				HashMap<String, String> hm = (HashMap<String, String>) tempMap.clone();
				hm.putAll(tempMap);
				table.add(hm);
				tempMap.clear();		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}
	
	public Integer getRowId(String column, String value) {
		ResultSet res = null;
		Integer rowId = null;
		try {
			res = minesql.querySQL("SELECT * FROM " + name + " WHERE " + column + " = '" + value + "';");
			if(res == null) {
				return null;
			}		
			if(res.next()) {
				rowId = res.getInt("id");
				return rowId;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowId; 
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
			if((Integer)minesql.querySQL("SELECT * FROM " + name + " WHERE id=" + rowId + ";").getInt("id") != null) {
				minesql.updateSQL("UPDATE " + name + " SET " + column + "='" + value +"' WHERE id=" + rowId + ";");
			} else {
				minesql.updateSQL("INSERT INTO " + name + " (" + column + ") VALUES ('" + value + "');");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Please make sure that the strings in the columns array correspond with the strings in the values array.
	 * 
	 * @param columns  Array of column names
	 * @param values  Array of values corresponding with the columns
	 */

	public void insert(String[] columns, String[] values) {
		try {
			StringBuilder cb = new StringBuilder();
			StringBuilder vb = new StringBuilder();

			for(String c : columns) {
				cb.append("," + c);
			}
			for(String v : values) {
				vb.append("','" + v);
			}
			
			System.out.println("INSERT INTO " + name + " (" + cb.toString().substring(1) + ") VALUES ('" + vb.toString().substring(3) + "');");
			minesql.updateSQL("INSERT INTO " + name + " (" + cb.toString().substring(1) + ") VALUES ('" + vb.toString().substring(3) + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
