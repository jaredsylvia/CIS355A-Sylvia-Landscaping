/**************************************************** 
Program Name: PlantType.java 
Programmer's Name: Jared Sylvia 
Program Description: Handles Plant Type objects
TODO: Long term if time allows, include water usage and maintenance attributes 
***********************************************************/
package landscaping;

public class PlantType {
	private String plantStyle;
	private double price;
	private int qty;
	//constructors
	public PlantType() {
		plantStyle = "None";
		price = 0.0;
		qty = 0;
	}
	
	public PlantType(String t, double p) {
		plantStyle = t;
		if(p < 0) {
			p = 0;
		}
		else {
		price = p;
		}
		
	}
	public PlantType(String t, double p, int q) {
		plantStyle = t;
		if(p < 0) {
			p = 0;
		}
		else {
		price = p;
		}
		qty = q;
	}
	
	//behaviors
	@Override
	public String toString() {
		return plantStyle;
	}
	
	//getters and setters
	public String getType() {return plantStyle;}
	public double getPrice() {return price;}
	public int getQty() {return qty;}
	public double getCost() {return price * qty;}
	public void setType(String t) {plantStyle = t;}
	public void setPrice(double p) {price = p;}
	public void setQty(int q) {qty = q;}
}
