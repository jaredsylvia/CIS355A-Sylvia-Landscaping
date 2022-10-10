/**************************************************** 
Program Name: IrrigationType.java 
Programmer's Name: Jared Sylvia 
Program Description: Handles irrigation types
TODO: Add getCost, rewrite getPrice to return JSON of individual price values 
***********************************************************/
package landscaping;

public class IrrigationType {
	//attributes
	private boolean inGround;
	private boolean drip;
	private int zones;
	
	
	//constructors
	public IrrigationType() {
		inGround = false;
		drip = false;
		zones = 0;
	}
	
	public IrrigationType(boolean i, boolean d, int z) {
		inGround = i;
		drip = d;
		zones = z;
		
	}
	
	//behaviors
	@Override
	public String toString() {
		String groundLevel;
		String hoseType;
		if(inGround == true) {
			groundLevel = "in ground";
		}
		else {
			groundLevel ="above ground";
		}
		if(drip == true) {
			hoseType = "drip";
		}
		else {
			hoseType = "conventional";
		}
		
		return String.format("%s-zone %s %s irrigation system.", zones, groundLevel, hoseType);
	}
	
	//getters and setters
	public boolean getInGround() {return inGround;}
	public boolean getDrip() {return drip;}
	public int getInGroundInt() {
		if(inGround == false) {
			return 0;
		}
		return 1;
	}
	public int getDripInt() {
		if(drip == false) {
			return 0;
		}
		return 1;
	}
	public int getZones() {return zones;}
	public double getPrice(Order order) {
		double plumbingCost = 0.0;
		double sprinklerCost = 0.0;
		if(inGround == true) {
			plumbingCost = 3 * order.getArea();
		}
		else if (inGround == false) {
			plumbingCost = 1.5 * order.getArea();
		}
		
		if(drip == true) {
			sprinklerCost = 2.75 * (order.getArea()/10);
		}
		else if(drip == false) {
			sprinklerCost = 3.25 * (order.getArea()/10);
		}
		return plumbingCost + sprinklerCost;
	}
		
	public void setInGround(boolean g) {inGround = g;}
	public void setDrip(boolean d) {drip = d;}
	public void setZones(int z) {zones = z;}
	
	
}
