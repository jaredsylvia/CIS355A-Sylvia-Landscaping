/**************************************************** 
Program Name: Order.java 
Programmer's Name: Jared Sylvia 
Program Description: Handles final order object for insertion into database
TODO: toString() method should evaluate lists plantTypes and treeTypes and return them formatted, I think it's currently broken 
TODO: add getPrice() to return individual pricing elements for possible future Invoice Class 
***********************************************************/
package landscaping;

import java.util.*;

public class Order {
	//attributes	
	private Customer customer;
	private Address address;
	private YardType yardType;
	private IrrigationType irrigationType;
	private List<PlantType> plantTypes;
	private TreeType treeType;
	private double length, width; 
	int orderID;
	
	
	//constructors
	public Order() {
		customer = new Customer();
		address = new Address();
		yardType = new YardType();
		irrigationType = new IrrigationType();
		plantTypes = new ArrayList<PlantType>();
		treeType = new TreeType();
		length = 0;
		width = 0;
		orderID = 0;
		
	}
	
	public Order(Customer c, Address a, YardType y, IrrigationType i, List<PlantType> p, TreeType t, double l, double w, int o) {
		customer = c;
		address =a;
		yardType = y;
		irrigationType = i;
		plantTypes = p;
		treeType = t;
		length = l;
		width = w;
		orderID = o;
	}
	
	//behaviors
	@Override
	public String toString() {
		String plants = "";
		for(PlantType p: plantTypes) {
			plants = plants + p.toString() + ",";
		}
		plants.substring(0, plants.length() - 1);
			
		return String.format("OrderID: %s\n"
				+ "Customer: %s\n"
				+ "Address: %s\n%s, %s %s\n"
				+ "YardType: %s\n"
				+ "IrrigationType: %s\n"
				+ "Plants: %s\n"
				+ "Trees: %s - %s\n"
				+ "Length: %s, Width: %s, Area: %s", 
				orderID, customer.getName(), address.getLine1(), address.getCity(), address.getState(), address.getZip(),
				yardType.getType(), irrigationType.toString(), plants, treeType.getQty(), treeType.getType(), 
				length, width, getArea());
	}
	
	public void addPlant(PlantType plant) {
		plantTypes.add(plant);
	}
	
	//getters and setters
	public Customer getCustomer() {return customer;}
	public Address getAddress() {return address;}
	public YardType getYardType() {return yardType;}
	public IrrigationType getIrrigationType() { return irrigationType;}
	public List<PlantType> getPlantTypes() { return plantTypes;}
	public TreeType getTreeTypes() { return treeType;}
	public double getLength() { return length;}
	public double getWidth() { return width;}
	public double getArea() {return length * width;}
	public int getOrderID() {return orderID;}
	public double getCost() {
		double plantCost = 0.0;
		for(PlantType p: plantTypes) {
			plantCost += p.getCost();
		}
		
		return plantCost + treeType.getCost() + irrigationType.getPrice(this) + yardType.getCost(this);
	}
	
	public void setLength(double l) {length = l;}
	public void setWidth(double w) {width = w;}
	public void setOrderID(int i) {orderID = i;}
	
}
