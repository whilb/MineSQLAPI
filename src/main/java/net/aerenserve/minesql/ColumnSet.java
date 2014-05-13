package net.aerenserve.minesql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColumnSet {

	private Set<Column> columns;
	
	public static ColumnSet fromMineSQL(MineSQL minesql, String tableName) {
		List<Column> columns = new ArrayList<Column>();
		try {
			ResultSet res = minesql.querySQL("SHOW COLUMNS FROM " + tableName);
			while(res.next()) {
				
				DataType dataType = DataType.OTHER;
				String type = res.getString("Type").toUpperCase();
				if(type.contains("TEXT")) dataType = DataType.TEXT;
				if(type.contains("BOOL")) dataType = DataType.BOOLEAN;
				if(type.contains("INT")) dataType = DataType.INTEGER;
				//TODO find a better way of doing this?
				columns.add(new Column(res.getString("field"), dataType, res.getString("KEY").contains("PRI")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ColumnSet(columns.toArray(new Column[columns.size()]));
	}
	
	public ColumnSet(Column... columns) {
		this.columns = new HashSet<Column>();
		boolean hasPrimary = false;
		for(Column c : columns) {
			if(c.isPrimary()) {
				if(!hasPrimary) {
					hasPrimary = true;
				} else {
					throw new IllegalArgumentException("Attempted to register more than 1 primary key!");
				}	
			}
			this.columns.add(c);
		}
	}
	
	public String[] getColumns() {
		String[] columnArray = new String[columns.size()];
		return columns.toArray(columnArray);
	}
	
	public Collection<Column> columns() {
		return this.columns();
	}
	
	public Column getColumn(String name) {
		for(Column c : columns) {
			if(c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}
}
