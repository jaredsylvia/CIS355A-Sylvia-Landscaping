/**************************************************** 
Program Name: TreeType.java 
Programmer's Name: Jared Sylvia 
Program Description: Handles TreeType objects
TODO: Long term if time allows, include water usage and maintenance attributes 
***********************************************************/
package landscaping;

public class TreeType {
	private String treeStyle;
	private double price;
	private int qty;
	
	//constructors
	public TreeType() {
		treeStyle = "None";
		price = 0.0;
		qty = 0;
	}
	
	public TreeType(String t, double p) {
		treeStyle = t;
		if(p < 0) {
			p = 0;
		}
		else {
		price = p;
		}
		
	}
	public TreeType(String t, double p, int q) {
		treeStyle = t;
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
		return treeStyle;
	}
	
	//getters and setters
	public String getType() {return treeStyle;}
	public double getPrice() {return price;}
	public int getQty() {return qty;}
	public double getCost() {return price * qty;}
	public void setType(String t) {treeStyle = t;}
	public void setPrice(double p) {price = p;}
	public void setQty(int q) {qty = q;}
	
	
}
