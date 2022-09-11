
/**************************************************** 
Program Name: Customer.java 
Programmer's Name: Jared Sylvia 
Program Description: Handles customer objects
TODO: Add list of orders?
TODO: Establish method of creating a non-duplicating auto-assigning Customer ID 
***********************************************************/
package landscaping;

public class Customer {
	
	//attributes / fields
	private int customerID;
	private String name;
	private Address address;
	
	
	
	//constructors
	public Customer() {
		customerID = 0;
		name = "unknown";
		address = new Address("123 Main St", "Anytown", "USA", "00000");
		
	}
	
	public Customer(int c, String n, Address a) {
		customerID = c;
		name = n;
		address = a;
		
	}
	
	//behaviors 
	@Override
	public String toString() {
		return name;
	}
	
	
	//getters and setters
	public int getCustomerID() {return customerID;}
	public String getName() {return name;}
	public Address getAddress() {return address;}
	
	public String getDetails() {
		
		String output="";
		if(address.getLine2() == null) {
			output = String.format("%s\n%s\n%s, %s, %s\n",name,address.getLine1(), address.getCity(), address.getState(), address.getZip()); 
		}
		else if(!(address.getLine2() == null)) {
			output = String.format("%s\n%s\n%s\n%s, %s, %s\n",name,address.getLine1(), address.getLine2(),address.getCity(), address.getState(), address.getZip());
		}
		return output;
	}
	
	
	public void setCustomerID(int c) {customerID = c;}
	public void setName(String n) {name = n;}
	public void setAddress(Address a) {address = a;}
	
	
	
}
