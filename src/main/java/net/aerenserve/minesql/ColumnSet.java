package net.aerenserve.minesql;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ColumnSet {

	private Set<Column> columns;
	
	public static ColumnSet fromMineSQL(MineSQL minesql, String tableName) {
		return new ColumnSet();
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
