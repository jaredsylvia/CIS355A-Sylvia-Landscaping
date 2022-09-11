/**************************************************** 
Program Name: YardOptions.java 
Programmer's Name: Jared Sylvia 
Program Description: Provides sample objects
TODO: Entire class should be removed in favor of this information being stored in database 
***********************************************************/
package landscaping;

public class YardOptions {
	//built in YardTypes
	static YardType gravel = new YardType("Gravel", 2.0);
	static YardType asphalt = new YardType("Asphalt", 5.0);
	static YardType turf = new YardType("Turf", 12.0);
	static YardType mulch = new YardType("Mulch", 0.5);
	static YardType grass = new YardType("Grass", 1.5);
	
	
	//built in PlantTypes
	static PlantType perrenial = new PlantType("Perrenial", 5);
	static PlantType annual = new PlantType("Annual", 3.5);
	static PlantType succulent = new PlantType("Succuluent", 7);
	static PlantType droughtTolerant = new PlantType("Drought Tolerant", 6);
	
	//built in TreeTypes
	static TreeType oak = new TreeType("Oak", 80);
	static TreeType pine = new TreeType("Pine", 65);
	static TreeType fruit = new TreeType("Fruit", 110);
}
