/**************************************************** 
Program Name: Address.java 
Programmer's Name: Jared Sylvia 
Program Description: Handles addresses
TODO: Optimized handling for multi-line addresses 
***********************************************************/
package landscaping;

public class Address {
	//fields / attributes
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String zipCode;
	
	//constructors
	public Address() {
		line1 = "123 Main Street";
		city = "Anytown";
		state = "USA";
		zipCode = "00000";
		
	}
	public Address(String l1, String l2, String c, String s, String z) {
		line1 = l1;
		if(!l2.isBlank()) {
			line2 = l2;
		}
		city = c;
		state = s;
		zipCode = z;
	}
	public Address(String l1, String c, String s, String z) {
		line1 = l1;
		city = c;
		state = s;
		zipCode = z;
	}
	
	//behaviors
	@Override
	public String toString() {
		return line1;
	}
	
	public String getDetails() {
		return String.format("%s\n%s\n%s, %s %s", line1, line2, city, state, zipCode);
	}
	
	//getters and setters
	public String getLine1() {return line1;}
	public String getLine2() {return line2;}
	public String getCity() {return city;}
	public String getState() {return state;}
	public String getZip() {return zipCode;}
	public void setLine1(String l) {line1 = l;}
	public void setLine2(String l) {line2 = l;}
	public void setCity(String c) {city = c;}
	public void setState(String s) {state = s;}
	public void setZip(String z) {zipCode = z;}
	
}
