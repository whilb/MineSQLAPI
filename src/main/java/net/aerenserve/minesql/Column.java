package net.aerenserve.minesql;

public class Column {
	
	private final String name;
	private final DataType type;
	private final Boolean isPrimary;
	
	public Column(String name, DataType type, Boolean isPrimary) {
		this.name = name;
		this.type = type;
		this.isPrimary = isPrimary;
	}
	
	public String getName() {
		return this.name;
	}
	
	public DataType getType() {
		return this.type;
	}
	
	public Boolean isPrimary() {
		return this.isPrimary;
	}
}
