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
	private List<TreeType> treeTypes;
	private double length, width; 
	int orderID;
	
	
	//constructors
	public Order() {
		customer = new Customer();
		address = new Address();
		yardType = new YardType();
		irrigationType = new IrrigationType();
		plantTypes = new ArrayList<PlantType>();
		treeTypes = new ArrayList<TreeType>();
		length = 0;
		width = 0;
		orderID = 0;
		
	}
	
	public Order(Customer c, Address a, YardType y, IrrigationType i, List<PlantType> p, List<TreeType> t, double l, double w, int o) {
		customer = c;
		address =a;
		yardType = y;
		irrigationType = i;
		plantTypes = p;
		treeTypes = t;
		length = l;
		width = w;
		orderID = o;
	}
	
	//behaviors
	@Override
	public String toString() {
		String plants = "";
		String trees = "";
		for(PlantType p: plantTypes) {
			plants = plants + p.toString() + ",";
		}
		plants.substring(0, plants.length() - 1);
		for(TreeType t: treeTypes) {
			trees = trees + t.toString() + ",";
		}
		trees.substring(0, trees.length() -1);
		
		return String.format("OrderID: %s\n"
				+ "Customer: %s\n"
				+ "Address: %s\n"
				+ "YardType: %s\n"
				+ "IrrigationType: %s\n"
				+ "Plants: %s\n"
				+ "Trees: %s\n"
				+ "Length: %s, Width: %s, Area: %s", 
				orderID, address.toString(), yardType.toString(), irrigationType.toString(), plants, trees, length, width, getArea());
	}
	
	//getters and setters
	public Customer getCustomer() {return customer;}
	public Address getAddress() {return address;}
	public YardType getYardType() {return yardType;}
	public IrrigationType getIrrigationType() { return irrigationType;}
	public List<PlantType> getPlantTypes() { return plantTypes;}
	public List<TreeType> getTreeTypes() { return treeTypes;}
	public double getLength() { return length;}
	public double getWidth() { return width;}
	public double getArea() {return length * width;}
	public double getCost() {
		double plantCost = 0.0;
		double treeCost = 0.0;
		for(PlantType p: plantTypes) {
			plantCost += p.getCost();
		}
		for(TreeType t: treeTypes) {
			treeCost += t.getCost();
		}
		return plantCost + treeCost + (irrigationType.getPrice(this) * getArea()) + (yardType.getPrice() * getArea());
	}
}
