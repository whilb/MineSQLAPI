package net.aerenserve.minesql;

import java.util.LinkedHashMap;

public class Row {

	private final Table parent;
	private LinkedHashMap<String, Object> stuff; //column, value
	
	public Row(Table parent) {
		this.parent = parent;
		this.stuff = new LinkedHashMap<String, Object>();
	}
	
	public Table getTable() {
		return this.parent;
	}
	
	public LinkedHashMap<String, Object> getStuff() {
		return this.stuff;
	}
	
	public void addObject(String key, Object value) {
		getStuff().put(key, value);
	}
	
	public Object get(String column) {
		return getStuff().get(column);
	}
	
	public String getString(String column) {
		return String.format("" + getStuff().get(column));
	}
	
	public Integer getInt(String key) {
		return Integer.parseInt(getString(key));
	}
}
