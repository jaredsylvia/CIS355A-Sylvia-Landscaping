package landscaping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.ibm.icu.util.StringTokenizer;

public class OrderIO {
	//attributes
	String fileName;
	ArrayList<Order> orders;
	
	//constructors
	public OrderIO() {
		fileName = "orders.csv";
	}
	
	public OrderIO(String fName) {
		setFilename(fName);
	}
	
	//behaviors
	public void saveData(ArrayList<Order> data) {
		try {
			BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
			for(Order order : data) {
				outFile.write("startOrder\n");
				outFile.write(String.format("%s,%s\n", order.getCustomer().getCustomerID(), order.getCustomer().getName()));
				outFile.write(String.format("%s,%s,%s,%s,%s\n", order.getCustomer().getAddress().getLine1(),
						order.getCustomer().getAddress().getLine2(),
						order.getCustomer().getAddress().getCity(),
						order.getCustomer().getAddress().getState(),
						order.getCustomer().getAddress().getZip()
						));
				outFile.write(String.format("%s,%s,%s,%s,%s\n", order.getAddress().getLine1(),
						order.getAddress().getLine2(),
						order.getAddress().getCity(),
						order.getAddress().getState(),
						order.getAddress().getZip()
						));
				outFile.write(String.format("%s,%s\n", order.getYardType().getType(), order.getYardType().getPrice()));
				outFile.write(String.format("%s,%s,%s\n", order.getIrrigationType().getInGround(), 
						order.getIrrigationType().getDrip(), order.getIrrigationType().getZones()));
				outFile.write("startPlants\n");
				for(PlantType plant : order.getPlantTypes()) {
					outFile.write(String.format("%s,%s,%s\n", plant.getType(), plant.getPrice(), plant.getQty()));
				}
				outFile.write("endPlants\n");
				outFile.write(String.format("%s,%s,%s\n", order.getTreeTypes().getType(), order.getTreeTypes().getPrice(),
						order.getTreeTypes().getQty()));
				outFile.write(String.format("%s,%s,%s\n", order.getLength(), order.getWidth(), order.getOrderID()));
				outFile.write("endOrder\n");
			}
			outFile.close();
			
		}
		catch(IOException ex) {
			JOptionPane.showMessageDialog(null, "Error: Unable to write to the file:\n" + ex.getMessage(), fileName, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public ArrayList<Order> getData(){
		ArrayList<Order> data = new ArrayList<Order>();
		try {
			BufferedReader inFile = new BufferedReader (new FileReader(fileName));
			String inputLine = "";
			StringTokenizer tokens;
			
			inputLine = inFile.readLine();
						
			while(inputLine != null) {
				if(inputLine.toString().equals("endOrder")) {
					inputLine = inFile.readLine();
				}
				if(inputLine == null) {
					continue;
				}
				if(inputLine.toString().equals("startOrder")) {
					Order order = new Order();
					while(!inputLine.toString().equals("endOrder")) {
						
						inputLine = inFile.readLine();
						tokens = new StringTokenizer(inputLine, ",");
						order.getCustomer().setCustomerID(Integer.parseInt(tokens.nextToken()));
						order.getCustomer().setName(tokens.nextToken());
						inputLine = inFile.readLine();
						tokens = new StringTokenizer(inputLine, ",");
						order.getCustomer().getAddress().setLine1(tokens.nextToken());
						String line2 = tokens.nextToken();
						if(line2 != "null") {
							order.getCustomer().getAddress().setLine2(line2);
						}
						order.getCustomer().getAddress().setCity(tokens.nextToken());
						order.getCustomer().getAddress().setState(tokens.nextToken());
						order.getCustomer().getAddress().setZip(tokens.nextToken());
						
						inputLine = inFile.readLine();
						tokens = new StringTokenizer(inputLine, ",");
						order.getAddress().setLine1(tokens.nextToken());
						
						line2 = tokens.nextToken();
						if(line2 != "null") {
							order.getAddress().setLine2(line2);
						}
						order.getAddress().setCity(tokens.nextToken());
						order.getAddress().setState(tokens.nextToken());
						order.getAddress().setZip(tokens.nextToken());
						
						inputLine = inFile.readLine();
						tokens = new StringTokenizer(inputLine, ",");
						order.getYardType().setType(tokens.nextToken());
						order.getYardType().setPrice(Double.parseDouble(tokens.nextToken()));
						
						inputLine = inFile.readLine();
						tokens = new StringTokenizer(inputLine, ",");
						order.getIrrigationType().setInGround(Boolean.parseBoolean(tokens.nextToken()));
						order.getIrrigationType().setDrip(Boolean.parseBoolean(tokens.nextToken()));
						order.getIrrigationType().setZones(Integer.parseInt(tokens.nextToken()));
						
						inputLine = inFile.readLine();
						if(inputLine.toString().equals("startPlants")) {
							inputLine = inFile.readLine();
							while(!inputLine.toString().equals("endPlants")) {
								tokens = new StringTokenizer(inputLine, ",");
								System.out.println(inputLine);
								System.out.println(inputLine.toString().equals("endPlants"));
								order.addPlant(new PlantType(tokens.nextToken(), 
										Double.parseDouble(tokens.nextToken()), 
										Integer.parseInt(tokens.nextToken()
												)
										)
									);
								inputLine = inFile.readLine();
							}
						}
						
						inputLine = inFile.readLine();
						tokens = new StringTokenizer(inputLine, ",");
						order.getTreeTypes().setType(tokens.nextToken());
						order.getTreeTypes().setPrice(Double.parseDouble(tokens.nextToken()));
						order.getTreeTypes().setQty(Integer.parseInt(tokens.nextToken()));
						
						inputLine = inFile.readLine();
						tokens = new StringTokenizer(inputLine, ",");
						order.setLength(Double.parseDouble(tokens.nextToken()));
						order.setWidth(Double.parseDouble(tokens.nextToken()));
						order.setOrderID(Integer.parseInt(tokens.nextToken()));
						
						data.add(order);
						inputLine = inFile.readLine();
					}
						
				}
			}
		inFile.close();	
		}
		catch(IOException ex){
			JOptionPane.showMessageDialog(null, "Error: Unable to open file:\n" + ex.getMessage(), fileName, JOptionPane.ERROR_MESSAGE);
		}
		return data;
	}
	//setters and getters
	public String getFilename() {return fileName;}
	public void setFilename(String fName) {
		if(fName.length() > 0) {
			fileName = fName;
		}
		else {
			fileName = "orders.csv";
		}
	}
	
}
