package net.aerenserve.minesql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Table {
	
	private ColumnSet columns;
	private MineSQL minesql;
	private final String name;

	public Table(MineSQL minesql, String tableName) {
		this.name = tableName;
		this.minesql = minesql;
		this.columns = ColumnSet.fromMineSQL(minesql, tableName);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String[] getColumns() {
		return this.columns.getColumns();
	}
	
	public Column getColumn(String name) {
		return this.columns.getColumn(name);
	}
	
	public Row getRow(String key) {
		Row row = null;
		for(Column c : columns.columns()) {
			if(c.isPrimary()) {
				try {
					row = new Row(this);
					ResultSet res = minesql.querySQL("SELECT * FROM " + name + " WHERE " + c.getName() + " = '" + key + "' LIMIT 1;");
					while(res.next()) {
						for(int column = 1; column <= res.getMetaData().getColumnCount(); column++) {
							Object value = res.getObject(column);
							row.addObject(res.getMetaData().getColumnName(column), value);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return row;
	}
}
