package landscaping;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class OrderDB {
//attributes
	
	int orderID;
	private String server, database, port, user, password;
	private Connection conn;
	private ArrayList<Order> orders;

	//constructors
		public OrderDB() {
			server = "localhost";
			database = "landscaping";
			port = "3306";
			user = "cis355a";
			password = "cis355a";
			conn = null;
			createTables();
		}
		
		public OrderDB(String server, String database, String port, String user, String password) {
			this.server = server;
			this.database = database;
			this.port = port;
			this.user = user;
			this.password = password;
			conn = null;
			String tableCreation = createTables();
			System.out.println(tableCreation);
			
		}

		//behaviors
		private String openDB() {  //Opens the database.
			String url = String.format("jdbc:mysql://%s:%s/%s", server, port, database); //formats URL from supplied variables
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(url, user, password);  //connects to SQL server
				return "SUCCESS";			
			}
			catch (SQLException e){
				e.printStackTrace();
				return e.getMessage();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println(e.toString());
				return e.getMessage();
			}
		}
		
		private String closeDB() { // Closes the database.
			try {
				conn.close();
				return "SUCCESS";		
			}
			catch (SQLException e) {
				return e.getMessage();
			}
		}
		
		public String createTables() {
			openDB();
			String sql = "CREATE TABLE if not exists orders " +
					"(orderid int NOT NULL," +
					"name VARCHAR(64)," +
					"cusAddLine1 VARCHAR(255)," +
					"cusAddLine2 VARCHAR(255)," +
					"cusCity VARCHAR(32)," +
					"cusState VARCHAR(16)," +
					"cusZip VARCHAR(10)," +
					"orderAddLine1 VARCHAR(255)," +
					"orderAddLine2 VARCHAR(255)," +
					"orderCity VARCHAR(32)," +
					"orderState VARCHAR(16)," +
					"orderZip VARCHAR(10)," +
					"groundStyle VARCHAR(16)," +
					"groundPrice DOUBLE," +
					"inGround BOOLEAN," +
					"drip BOOLEAN," +
					"zones TINYINT," +
					"plants TEXT," +
					"treeStyle VARCHAR(16)," +
					"treePrice DOUBLE," +
					"treeQty TINYINT," +
					"length DOUBLE," +
					"width DOUBLE," +
					"PRIMARY KEY(orderid)" +
					")";
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				closeDB();
				return "SUCCESS";
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
				return e.getMessage();
				
			}
		}
		
		public String addOrder(Order order) {
			openDB();
			String plantsCSV = "";
			for(PlantType plant : order.getPlantTypes()) {
				plantsCSV = plantsCSV + String.format("%s,%s,%s,", plant.getType(), plant.getCost(), plant.getQty());
			}
			String sql = String.format(
					"INSERT INTO orders (orderID, name, cusAddLine1, cusAddLine2, cusCity, cusState, cusZip, orderAddLine1, orderAddLine2," +
					"orderCity, orderState, orderZip, groundStyle, groundPrice, inGround, drip, zones, plants, treeStyle, treePrice, treeQty," +
					"length, width) " +
					"VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\"," +
					"\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")", 
					order.getOrderID(), order.getCustomer().getName(), 
					order.getCustomer().getAddress().getLine1(), order.getCustomer().getAddress().getLine2(), 
					order.getCustomer().getAddress().getCity(), order.getCustomer().getAddress().getState(), order.getCustomer().getAddress().getZip(),
					order.getAddress().getLine1(), order.getAddress().getLine2(), 
					order.getAddress().getCity(), order.getAddress().getState(), order.getAddress().getZip(),
					order.getYardType().getType(), order.getYardType().getPrice(), 
					order.getIrrigationType().getInGroundInt(), order.getIrrigationType().getDripInt(),order.getIrrigationType().getZones(),
					plantsCSV,order.getTreeTypes().getType(), order.getTreeTypes().getPrice(), order.getTreeTypes().getQty(),
					order.getLength(), order.getWidth()
					);
			
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				closeDB();
				return "SUCCESS";
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
				return e.getMessage();
			}
					
		}
		
		private String queryServer(){ //do not call if you have unsaved information
			ArrayList<Order> orders = new ArrayList<Order>();
			openDB();
			try {
				String sql = "SELECT * FROM orders";   
				Statement stmt = conn.createStatement(); 
				ResultSet results = stmt.executeQuery(sql); // execute select all from table 
							
				while(results.next()) {						//while loop to iterate over each entry
					
					Address cusAddress = new Address(results.getString(3),results.getString(4), 
							results.getString(5), results.getString(6), results.getString(7));
					Address orderAddress = new Address(results.getString(8),results.getString(9), 
							results.getString(10), results.getString(11), results.getString(12));
					YardType yard = new YardType(results.getString(13),results.getDouble(14));
					
					IrrigationType irrigation = new IrrigationType(results.getBoolean(15), results.getBoolean(16), results.getInt(17));
					ArrayList<PlantType> plantTypes = new ArrayList<PlantType>();
					StringTokenizer tokens = new StringTokenizer(results.getString(18), ",");
					for(int i = 0; i < tokens.countTokens(); i++) {
						plantTypes.add(new PlantType(tokens.nextToken(),Double.parseDouble(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
						i += 4;
					}
					TreeType tree = new TreeType(results.getString(19), results.getDouble(20),results.getInt(21));
					Customer customer = new Customer(results.getInt(1), results.getString(2),cusAddress);
					orders.add(new Order(customer, orderAddress, yard, irrigation, plantTypes, tree, 
							results.getDouble(22), results.getDouble(23),results.getInt(1)));
				}
				closeDB();
				this.orders = orders;
				return "SUCCESS";
				
				
			}
			catch (SQLException e){
				return e.getMessage();
			}
			
			
		}
		
		public String deleteEntry(int custID) {
			try {
				openDB();
				String sql = String.format("DELETE FROM orders WHERE orderid = \"%s\"", custID);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				return "SUCCESS";
			}
			catch (SQLException e){
				System.out.println(e.getLocalizedMessage());
				return e.getMessage();
			}
		}
		
		static int orderID() {
			long timeMilli = System.currentTimeMillis();
			String orderString = String.valueOf(timeMilli);
			return Integer.parseInt(orderString.substring(4, 9));
		}
		
		
		
		//getters and setters
		public String getServer() { return server;}
		public String getDatabase() { return database;}
		public String getPort() {return port;}
		public String getUser() {return user;}
		public ArrayList<Order> getAll() {
			queryServer();
			return orders;
			}
		public void setServer(String s) {
			if(!s.isBlank()) {
				server = s;
			}
			else {
				server = "localhost";
			}
		}
		
		public void setDatabase(String d) {
			if(!d.isBlank()) {
				database = d;
			}
			else {
				database = "landscaping";
			}
		}
		
		public void setPort(String p) {
			if(!p.isBlank()) {
				port = p;
			}
			else {
				port = "3306";
			}
		}
		
		public void setUser(String u) {
			if(!u.isBlank()) {
				user = u;
			}
			else {
				user = "cis355a";
			}
		}
		
		public void setPass(String p) {
			if(!p.isBlank()) {
				password = p;
			}
			else {
				password = "cis355a";
			}
		}
}
