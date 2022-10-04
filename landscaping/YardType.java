/**************************************************** 
Program Name: YardType.java 
Programmer's Name: Jared Sylvia 
Program Description: Handles base yard type
TODO: add getCost() with YardType object input to return price * area, maybe? 
***********************************************************/
package landscaping;

public class YardType {
	//attributes
	private String groundStyle;
	private double price;
	
	//constructors
	public YardType() {
		groundStyle = "None";
		price = 0.0;
	}
	
	public YardType(String t, double p) {
		groundStyle = t;
		if(p < 0) {
			p = 0;
		}
		else {
		price = p;
		}
		
	}
	
	//behaviors
	@Override
	public String toString() {
		return groundStyle;
	}
	
	//getters and setters
	public String getType() {return groundStyle;}
	public double getPrice() {return price;}
	public double getCost(Order order) {
		return getPrice() * order.getArea();
	}
	public void setType(String t) {groundStyle = t;}
	public void setPrice(double p) {price = p;}
	
}


