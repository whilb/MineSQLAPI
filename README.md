MineSQLAPI
==========

API for making the integration of MySQL into Bukkit plugins quick and easy

Features
========

- Querying the MySQL database with traditional MySQL syntax
- Executing updates to the MySQL database with traditional MySQL syntax
- Creating a table
- Opening a table as an ArrayList<HashMap<String, String>>
- Getting a value from a certain row and column
- Putting lots of values in a table by using String arrays.

Usage
=====

First, we need to make a new MineSQL instance.

```java

//Creating a new MineSQL instance

minesql = new MineSQL(this, 'localhost', '3306', 'exampleDatabase', 'yourUser', '1337p4ssw0rd');
```

By using this constructor for a Table, we can create a new table.

```java

//Example of making a new table

HashMap<String, String> columns = new HashMap<String, String>();
columns.put("username", "text");
columns.put("firstjoined", "text");

Table myTable = new Table(minesql, "exampleTable", columns);
```

You can put multiple values in a table at once.

```java

//Example of putting values into a table

String username = "Notch";
String firstJoined = "1337";

if(!myTable.contains("username", username) { //Make sure that the value is not in the database
	String[] columns = {"username", "firstjoined"};
	String[] values = {e.getPlayer().getName(), String.format("" + e.getPlayer().getFirstPlayed())};
	myTable.insert(columns, values);
}
```

By using Table.openTable(), you can read and iterate through the values in a table 

```java

//Example of iterating through values in a table

ArrayList<HashMap<String, String>> table = myTable.openTable();
for(HashMap<String, String> hm : table) {
	Iterator<Entry<String, String>> it = hm.entrySet().iterator();
	  while (it.hasNext()) {
		  Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			System.out.println(entry.getKey() + " : " + entry.getValue());
			}
	}
```


			
