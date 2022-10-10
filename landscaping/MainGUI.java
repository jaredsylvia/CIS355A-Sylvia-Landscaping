/**************************************************** 
Program Name: MainGUI.java 
Programmer's Name: Jared Sylvia 
Program Description: Handles GUI construction and runs program. 
TODO: Redesign plant and tree tabs to have dynamic drop down menus populated by database.
TODO: Finish all tabs
TODO: Align everything
TODO: Buttons on information tab seem to disappear on random presses of TAB button, have not attempted to duplicate on another machine
***********************************************************/
package landscaping;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Scale;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;


public class MainGUI {
	/* TODO: Move methods to prevent defining objects in beginning of class
	 * or if this is a standard practice define every object in beginning of class
	 * so that all methods can be defined prior to the construction of the form
	*/
	protected Shell shlJaredSylviaLandscapes;
	private Text textOrderName;
	private Text textOrderAddressOne;
	private Text textOrderAddressTwo;
	private Text textQtyBeardTongue;
	private Text textQtyBlueEyedGrass;
	private Text textQtyCaliforniaFuschia;
	private Text textQtyCatalinaCurrant;
	private Text textQtySage;
	private Text textQtyManzanita;
	private Text textQtyDouglasIris;
	private Text textQtyDeerGrass;
	private Text textOrderCity;
	private Text textOrderState;
	private Text textOrderZip;
	private Text textYardLength;
	private Text textYardWidth;
	private Text textOrderInfo;
	
	private Group grpGroundType;
	private Group grpInGround;
	private Group grpSprinklerType;
	private Label lblGroundImage;
	private Label lblYardPrice;
	
	private Composite compositePlants;
	private Composite compositeTrees;
	private Composite compositeInfo;
	
	private TabFolder tabFolder;
	
	private org.eclipse.swt.widgets.List listOrders;
	
	
	private YardType currentOrderYard = new YardType();
	private IrrigationType currentOrderIrrigation = new IrrigationType();
	private Address currentCustomerAddress = new Address();
	private Address currentOrderAddress = new Address();
	private ArrayList<PlantType> orderPlants = new ArrayList<PlantType>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private TreeType tree = new TreeType();
	private PlantType selectedPlant;
	private Text textIrrigationZones;
	private int orderID = 0;
	private Text textSelectedOrder;
	
	private OrderDB orderDB = new OrderDB();
	
	private boolean validateInputs() {  // Only applicable to order information screen. Order object contains address of order, customer object contains billing address only
		if(textOrderName.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Enter a Name",
                    "Name Error", JOptionPane.ERROR_MESSAGE);
            textOrderName.setFocus();
            return false;
		}
		
		if(textOrderAddressOne.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter an Address",
					"Address Error", JOptionPane.ERROR_MESSAGE);
			textOrderAddressOne.setFocus();
			return false;
		}
		
		if(textOrderCity.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter a City",
					"City Error", JOptionPane.ERROR_MESSAGE);
			textOrderCity.setFocus();
			return false;
		}
		
		if(textOrderState.getText().equals("")) {
			JOptionPane.showMessageDialog(null,  "Enter a State",
					"State Error", JOptionPane.ERROR_MESSAGE);
			textOrderState.setFocus();
			return false;
		}
		
		if(textOrderZip.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter a Zip Code",
					"Zip Code Error", JOptionPane.ERROR_MESSAGE);
			textOrderZip.setFocus();
			return false;
		}
		
		if(textYardWidth.getText().equals("") || textYardLength.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter yard measurements",
					"Yard Measurement Error", JOptionPane.ERROR_MESSAGE);
			textYardLength.setFocus();
			return false;
		}
		
		
		try {
			Double.parseDouble(textYardWidth.getText());
		}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Width must be numeric.",
					"Width Error", JOptionPane.ERROR_MESSAGE);
			textYardWidth.setText("");;
			textYardWidth.setFocus();
			return false;
		}
		
		if(Double.parseDouble(textYardWidth.getText()) <= 0) {
			JOptionPane.showMessageDialog(null, "Width must be greater than 0",
					"Width Error", JOptionPane.ERROR_MESSAGE);
			textYardWidth.setText("");
			textYardWidth.setFocus();
			return false;
		}
		
		try {
			Double.parseDouble(textYardLength.getText());
		}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Length must be numeric.",
					"Length Error", JOptionPane.ERROR_MESSAGE);
			textYardLength.setText("");;
			textYardLength.setFocus();
			return false;
		}
		if(Double.parseDouble(textYardLength.getText()) <= 0) {
			JOptionPane.showMessageDialog(null, "Length must be greater than 0",
					"Length Error", JOptionPane.ERROR_MESSAGE);
			textYardLength.setText("");
			textYardLength.setFocus();
			return false;
		}
		return true;
		
	}
	
	private Customer createCustomer() { //temporary code to turn in on time, code for yard size should be in order and not customer as customer has orders and orders have yards
		Address address;
		if(textOrderAddressTwo.getText().equals("")){
			address = new Address(textOrderAddressOne.getText(),textOrderCity.getText(),textOrderState.getText(),textOrderZip.getText());
		}
		else {
			address = new Address(textOrderAddressOne.getText(),textOrderAddressTwo.getText(),textOrderCity.getText(),textOrderState.getText(),textOrderZip.getText());
		}
		Customer cust = new Customer(0, textOrderName.getText(), address);
		return cust;
		
	}
	
    public void submitOrder()
    {
    	Customer thisCust = createCustomer();
    	orderID++;
    	Order thisOrder = new Order(thisCust, thisCust.getAddress(), currentOrderYard, currentOrderIrrigation, orderPlants, tree, Double.parseDouble(textYardLength.getText()), Double.parseDouble(textYardWidth.getText()), Order.orderID());
    	orders.add(thisOrder);
    	listOrders.add(String.format("%s - (%s)", thisOrder.getCustomer().getName(), thisOrder.getOrderID()));
        orderDB.addOrder(thisOrder);
        
    }
    
    public void populateFromDB() {
    	orders = orderDB.getAll();
    	for (Order order: orders) {
    		listOrders.add(String.format("%s - (%s)", order.getCustomer().getName(), order.getOrderID()));
    	}
    }
    
   
    /*
     * TODO: Run a single loop that goes through the entire frame?
     */
	public void reset() {
    	for( Control c : grpGroundType.getChildren()) {
    		Button selectedButton = (Button) c;
    		selectedButton.setSelection(false);
    	}
    	for( Control c : grpInGround.getChildren()) {
    		if(c instanceof Button) {
    			Button selectedButton = (Button) c;
        		selectedButton.setSelection(false);	
    		}
    	}
    	for( Control c : grpSprinklerType.getChildren()) {
    		if(c instanceof Button) {
    			Button selectedButton = (Button) c;
        		selectedButton.setSelection(false);	
    		}
    	}
    	for( Control c : compositePlants.getChildren()) {
    		if(c instanceof Button) {
    			Button selectedButton = (Button) c;
        		selectedButton.setSelection(false);
    		}
    		if(c instanceof Text) {
    			Text selectedText = (Text) c;
    			selectedText.setText("0");
    		}
    	}
    	for( Control c: compositeTrees.getChildren()) {
    		if(c instanceof Scale) {
    			Scale selectedScale = (Scale) c;
    			selectedScale.setSelection(0);
    		}
    		if(c instanceof Combo) {
    			Combo selectedCombo = (Combo) c;
    			selectedCombo.setItem(0, "Oak");
    		}
    	}
    	for( Control c: compositeInfo.getChildren()) {
    		if(c instanceof Text) {
    			Text selectedText = (Text) c;
    			selectedText.setText("");
    		}
    		
    	}
    	lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/dirt.png"));
    	lblYardPrice.setText("Please select a ground type.");
    	tabFolder.setSelection(0);
    	
    }

	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainGUI window = new MainGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlJaredSylviaLandscapes.open();
		shlJaredSylviaLandscapes.layout();
		while (!shlJaredSylviaLandscapes.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlJaredSylviaLandscapes = new Shell();
		shlJaredSylviaLandscapes.setSize(803, 539);
		shlJaredSylviaLandscapes.setText("Jared Sylvia Landscapes");
		
		tabFolder = new TabFolder(shlJaredSylviaLandscapes, SWT.NONE);
		tabFolder.setBounds(10, 10, 768, 425);
		
		TabItem tbtmGroundType = new TabItem(tabFolder, SWT.NONE);
		tbtmGroundType.setText("Ground Type");
		
		Composite compositeGround = new Composite(tabFolder, SWT.NONE);
		tbtmGroundType.setControl(compositeGround);
		compositeGround.setLayout(null);
		
		lblGroundImage = new Label(compositeGround, SWT.NONE);
		lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/gravel.png"));
		lblGroundImage.setBounds(280, 120, 400, 200);
		
		lblYardPrice = new Label(compositeGround, SWT.NONE);
		lblYardPrice.setAlignment(SWT.CENTER);
		lblYardPrice.setBounds(419, 347, 120, 15);
		lblYardPrice.setText("$2.00/sq. ft.");
		
		Label lblJaredSylviaLandscapeGround = new Label(compositeGround, SWT.NONE);
		lblJaredSylviaLandscapeGround.setText("Jared Sylvia Landscapes");
		lblJaredSylviaLandscapeGround.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
		lblJaredSylviaLandscapeGround.setBounds(215, 20, 314, 45);
		
		Label orderDescriptor = new Label(compositeGround, SWT.NONE);
		orderDescriptor.setAlignment(SWT.CENTER);
		orderDescriptor.setText("Ground Types");
		orderDescriptor.setBounds(215, 65, 314, 15);
		
		grpGroundType = new Group(compositeGround, SWT.NONE);
		grpGroundType.setText("Ground Type:");
		grpGroundType.setBounds(60, 120, 174, 187);
		
		Button btnGravel = new Button(grpGroundType, SWT.RADIO);
		btnGravel.setLocation(10, 30);
		btnGravel.setSize(90, 16);
		btnGravel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnGravel.setSelection(true);
		btnGravel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnGravel.setText("Gravel");
		
		
		Button btnAsphalt = new Button(grpGroundType, SWT.RADIO);
		btnAsphalt.setLocation(10, 60);
		btnAsphalt.setSize(90, 16);
		btnAsphalt.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnAsphalt.setText("Asphalt");
		
		Button btnTurf = new Button(grpGroundType, SWT.RADIO);
		btnTurf.setLocation(10, 90);
		btnTurf.setSize(90, 16);
		btnTurf.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnTurf.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/turf.png"));
				lblYardPrice.setText("$12.00/sq. ft.");
				currentOrderYard.setPrice(12.00);
				currentOrderYard.setType("Turf");
			}
		});
		btnTurf.setText("Turf");
		
		Button btnMulch = new Button(grpGroundType, SWT.RADIO);
		btnMulch.setLocation(10, 120);
		btnMulch.setSize(90, 16);
		btnMulch.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnMulch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/mulch.png"));
				lblYardPrice.setText("$0.50/sq. ft.");
				currentOrderYard.setPrice(0.50);
				currentOrderYard.setType("Mulch");
				
			}
		});
		btnMulch.setText("Mulch");
		
		Button btnGrass = new Button(grpGroundType, SWT.RADIO);
		btnGrass.setLocation(10, 150);
		btnGrass.setSize(90, 16);
		btnGrass.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnGrass.setText("Grass");
		btnGrass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/grass.png"));
				lblYardPrice.setText("$1.50/sq. ft.");
				currentOrderYard.setPrice(1.50);
				currentOrderYard.setType("Grass");
				
			}
		});
		btnAsphalt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/asphalt.png"));
				lblYardPrice.setText("$5.00/sq. ft.");
				currentOrderYard.setPrice(5.00);
				currentOrderYard.setType("Asphalt");
				
			}
		});
		btnGravel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/gravel.png"));
				lblYardPrice.setText("$2.00/sq. ft.");
				currentOrderYard.setPrice(2.00);
				currentOrderYard.setType("Gravel");
			}
		});
		
		
		
		TabItem tbtmIrrigationType = new TabItem(tabFolder, SWT.NONE);
		tbtmIrrigationType.setText("Irrigation");
		
		Composite compositeIrrigation = new Composite(tabFolder, SWT.NONE);
		tbtmIrrigationType.setControl(compositeIrrigation);
		
		Label lblJaredSylviaLandscapeIrrigation = new Label(compositeIrrigation, SWT.NONE);
		lblJaredSylviaLandscapeIrrigation.setText("Jared Sylvia Landscapes");
		lblJaredSylviaLandscapeIrrigation.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
		lblJaredSylviaLandscapeIrrigation.setBounds(215, 20, 314, 45);
		
		Label orderDescriptorIrrigation = new Label(compositeIrrigation, SWT.NONE);
		orderDescriptorIrrigation.setText("Irrigation Types");
		orderDescriptorIrrigation.setAlignment(SWT.CENTER);
		orderDescriptorIrrigation.setBounds(215, 65, 314, 15);
		
		grpInGround = new Group(compositeIrrigation, SWT.NONE);
		grpInGround.setText("In Ground");
		grpInGround.setBounds(41, 98, 315, 260);
		
		
		Label lblInGround = new Label(grpInGround, SWT.NONE);
		lblInGround.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/irrigation/inGround.png"));
		lblInGround.setBounds(30, 100, 255, 135);
		
		Button btnAboveGround = new Button(grpInGround, SWT.RADIO);
		btnAboveGround.setBounds(10, 60, 100, 16);
		btnAboveGround.setText("Above Ground");
		btnAboveGround.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblInGround.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/irrigation/aboveGround.png"));
				currentOrderIrrigation.setInGround(false);
			}
		});
		Button btnInGround = new Button(grpInGround, SWT.RADIO);
		btnInGround.setSelection(true);
		btnInGround.setLocation(10, 30);
		btnInGround.setSize(100, 16);
		btnInGround.setText("In Ground");
		btnInGround.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblInGround.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/irrigation/inGround.png"));
				currentOrderIrrigation.setInGround(true);
			}
		});
		
		
		
		grpSprinklerType = new Group(compositeIrrigation, SWT.NONE);
		grpSprinklerType.setText("Sprinkler Type");
		grpSprinklerType.setBounds(400, 98, 315, 260);
		
		Label lblSprinklerType = new Label(grpSprinklerType, SWT.NONE);
		lblSprinklerType.setLocation(30, 100);
		lblSprinklerType.setSize(255, 135);
		lblSprinklerType.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/irrigation/regSprinkler.png"));
		
		Button btnConventional = new Button(grpSprinklerType, SWT.RADIO);
		btnConventional.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblSprinklerType.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/irrigation/regSprinkler.png"));
				currentOrderIrrigation.setDrip(false);
			}
		});
		btnConventional.setSelection(true);
		btnConventional.setBounds(10, 30, 90, 16);
		btnConventional.setText("Conventional");
		
		Button btnDrip = new Button(grpSprinklerType, SWT.RADIO);
		btnDrip.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblSprinklerType.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/irrigation/dripSprinkler.png"));
				currentOrderIrrigation.setDrip(true);
			}
		});
		btnDrip.setBounds(10, 60, 90, 16);
		btnDrip.setText("Drip");
		
		textIrrigationZones = new Text(compositeIrrigation, SWT.BORDER);
		textIrrigationZones.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					if(Double.parseDouble(textIrrigationZones.getText()) > 0) {
						currentOrderIrrigation.setZones(Integer.parseInt(textIrrigationZones.getText()));
					}
					else {
						currentOrderIrrigation.setZones(1);
						JOptionPane.showMessageDialog(null, "Zones must be numeric and at least 1.",
								"Zones Error", JOptionPane.ERROR_MESSAGE);
						textIrrigationZones.setFocus();
						textIrrigationZones.setText("1");
					}
				}
				catch(NumberFormatException nFE) {
					currentOrderIrrigation.setZones(1);
					JOptionPane.showMessageDialog(null, "Zones must be numeric and at least 1.",
							"Zones Error", JOptionPane.ERROR_MESSAGE);
					textIrrigationZones.setFocus();
					textIrrigationZones.setText("1");
				}
				
			}
		});
		textIrrigationZones.setBounds(639, 366, 76, 21);
		
		Label lblIrrigationZones = new Label(compositeIrrigation, SWT.NONE);
		lblIrrigationZones.setBounds(565, 372, 55, 15);
		lblIrrigationZones.setText("Zones");
		
		TabItem tbtmPlantType = new TabItem(tabFolder, SWT.NONE);
		tbtmPlantType.setText("Plants");
		
		compositePlants = new Composite(tabFolder, SWT.NONE);
		tbtmPlantType.setControl(compositePlants);
		
		Label lblJaredSylviaLandscapePlants = new Label(compositePlants, SWT.NONE);
		lblJaredSylviaLandscapePlants.setText("Jared Sylvia Landscapes");
		lblJaredSylviaLandscapePlants.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
		lblJaredSylviaLandscapePlants.setBounds(215, 20, 314, 45);
		
		Label orderDescriptorPlants = new Label(compositePlants, SWT.NONE);
		orderDescriptorPlants.setText("Plant Types");
		orderDescriptorPlants.setAlignment(SWT.CENTER);
		orderDescriptorPlants.setBounds(215, 65, 314, 15);
		
		Button btnBeardTongue = new Button(compositePlants, SWT.CHECK);
		btnBeardTongue.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/BeardTongue.png"));
		btnBeardTongue.setBounds(65, 115, 125, 100);
		btnBeardTongue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnBeardTongue.getSelection() == true) {
					orderPlants.add(new PlantType("Beardtongue", 13.99));
					if (Integer.parseInt(textQtyBeardTongue.getText()) <= 0) {
						textQtyBeardTongue.setText("1");
						for(PlantType plant : orderPlants) {
							if(plant.getType().equals("Beardtongue")) {
								plant.setQty(1);
							}
						}
					}
				}
				else if (btnBeardTongue.getSelection() == false) {
					for(PlantType plant : orderPlants) {
						if(plant.getType().equals("Beardtongue")) {
							selectedPlant = plant;
							
						}
					}
					orderPlants.remove(selectedPlant);
					
				}
			}
		});
		
		Button btnBlueEyedGrass = new Button(compositePlants, SWT.CHECK);
		btnBlueEyedGrass.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/BlueEyedGrass.png"));
		btnBlueEyedGrass.setBounds(235, 115, 125, 100);
		btnBlueEyedGrass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnBlueEyedGrass.getSelection() == true) {
					orderPlants.add(new PlantType("Blue-eyed Grass", 21.99));
					if (Integer.parseInt(textQtyBlueEyedGrass.getText()) <= 0) {
						textQtyBlueEyedGrass.setText("1");
						for(PlantType plant : orderPlants) {
							if(plant.getType().equals("Blue-eyed Grass")) {
								plant.setQty(1);
							}
						}
					}
				}
				else if (btnBlueEyedGrass.getSelection() == false) {
					for(PlantType plant : orderPlants) {
						if(plant.getType().equals("Blue-eyed Grass")) {
							selectedPlant = plant;
							
						}
					}
					orderPlants.remove(selectedPlant);
					
				}
			}
		});
		
		Button btnCaliforniaFuschia = new Button(compositePlants, SWT.CHECK);
		btnCaliforniaFuschia.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/CaliforniaFuschia.png"));
		btnCaliforniaFuschia.setBounds(405, 115, 125, 100);
		btnCaliforniaFuschia.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnCaliforniaFuschia.getSelection() == true) {
					orderPlants.add(new PlantType("California Fuschia", 25.99));
					if (Integer.parseInt(textQtyCaliforniaFuschia.getText()) <= 0) {
						textQtyCaliforniaFuschia.setText("1");
						for(PlantType plant : orderPlants) {
							if(plant.getType().equals("California Fuschia")) {
								plant.setQty(1);
							}
						}
					}
				}
				else if (btnCaliforniaFuschia.getSelection() == false) {
					for(PlantType plant : orderPlants) {
						if(plant.getType().equals("California Fuschia")) {
							selectedPlant = plant;
							
						}
					}
					orderPlants.remove(selectedPlant);
					
				}
			}
		});
		
		Button btnCatalinaCurrant = new Button(compositePlants, SWT.CHECK);
		btnCatalinaCurrant.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/CatalinaCurrent.png"));
		btnCatalinaCurrant.setBounds(575, 115, 125, 100);
		btnCatalinaCurrant.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnCatalinaCurrant.getSelection() == true) {
					orderPlants.add(new PlantType("Catalina Currant", 18.99));
					if (Integer.parseInt(textQtyCatalinaCurrant.getText()) <= 0) {
						textQtyCatalinaCurrant.setText("1");
						for(PlantType plant : orderPlants) {
							if(plant.getType().equals("Catalina Currant")) {
								plant.setQty(1);
							}
						}
					}
				}
				else if (btnCatalinaCurrant.getSelection() == false) {
					for(PlantType plant : orderPlants) {
						if(plant.getType().equals("Catalina Currant")) {
							selectedPlant = plant;
							
						}
					}
					orderPlants.remove(selectedPlant);
					
				}
			}
		});

		
		Button btnDeerGrass = new Button(compositePlants, SWT.CHECK);
		btnDeerGrass.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/Deergrass.png"));
		btnDeerGrass.setBounds(65, 249, 125, 100);
		btnDeerGrass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnDeerGrass.getSelection() == true) {
					orderPlants.add(new PlantType("Deer Grass", 15.99));
					if (Integer.parseInt(textQtyDeerGrass.getText()) <= 0) {
						textQtyDeerGrass.setText("1");
						for(PlantType plant : orderPlants) {
							if(plant.getType().equals("Deer Grass")) {
								plant.setQty(1);
							}
						}
					}
				}
				else if (btnDeerGrass.getSelection() == false) {
					for(PlantType plant : orderPlants) {
						if(plant.getType().equals("Deer Grass")) {
							selectedPlant = plant;
							
						}
					}
					orderPlants.remove(selectedPlant);
					
				}
			}
		});

		
		Button btnDouglasIris = new Button(compositePlants, SWT.CHECK);
		btnDouglasIris.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/DouglasIris.png"));
		btnDouglasIris.setBounds(235, 249, 125, 100);
		btnDouglasIris.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnDouglasIris.getSelection() == true) {
					orderPlants.add(new PlantType("Douglas Iris", 19.99));
					if (Integer.parseInt(textQtyDouglasIris.getText()) <= 0) {
						textQtyDouglasIris.setText("1");
						for(PlantType plant : orderPlants) {
							if(plant.getType().equals("Douglas Iris")) {
								plant.setQty(1);
							}
						}
					}
				}
				else if (btnDouglasIris.getSelection() == false) {
					for(PlantType plant : orderPlants) {
						if(plant.getType().equals("Douglas Iris")) {
							selectedPlant = plant;
							
						}
					}
					orderPlants.remove(selectedPlant);
					
				}
			}
		});

		
		Button btnManzanita = new Button(compositePlants, SWT.CHECK);
		btnManzanita.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/Manzanita.png"));
		btnManzanita.setBounds(405, 249, 125, 100);
		btnManzanita.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnManzanita.getSelection() == true) {
					orderPlants.add(new PlantType("Manzanita", 11.99));
					if (Integer.parseInt(textQtyManzanita.getText()) <= 0) {
						textQtyManzanita.setText("1");
						for(PlantType plant : orderPlants) {
							if(plant.getType().equals("Manzanita")) {
								plant.setQty(1);
							}
						}
					}
				}
				else if (btnManzanita.getSelection() == false) {
					for(PlantType plant : orderPlants) {
						if(plant.getType().equals("Manzanita")) {
							selectedPlant = plant;
							
						}
					}
					orderPlants.remove(selectedPlant);
					
				}
			}
		});

		
		Button btnSage = new Button(compositePlants, SWT.CHECK);
		btnSage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/Sage.png"));
		btnSage.setBounds(575, 249, 125, 100);
		btnSage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnSage.getSelection() == true) {
					orderPlants.add(new PlantType("Sage", 11.99));
					if (Integer.parseInt(textQtySage.getText()) <= 0) {
						textQtySage.setText("1");
						for(PlantType plant : orderPlants) {
							if(plant.getType().equals("Sage")) {
								plant.setQty(1);
							}
						}
					}
				}
				else if (btnSage.getSelection() == false) {
					for(PlantType plant : orderPlants) {
						if(plant.getType().equals("Sage")) {
							selectedPlant = plant;
							
						}
					}
					orderPlants.remove(selectedPlant);
					
				}
			}
		});

		/* TODO: Each text field needs handlers to detect the qty number changing
		 * They should first check to see if the plant pictured is selected
		 * If checked then set qty of the corresponding object to the qty in the text field
		 * if unchecked then do nothing.
		 * */
		textQtyBeardTongue = new Text(compositePlants, SWT.BORDER);
		textQtyBeardTongue.setText("0");
		textQtyBeardTongue.setBounds(118, 221, 25, 21);
		
		textQtyBlueEyedGrass = new Text(compositePlants, SWT.BORDER);
		textQtyBlueEyedGrass.setText("0");
		textQtyBlueEyedGrass.setBounds(288, 221, 25, 21);
		
		textQtyCaliforniaFuschia = new Text(compositePlants, SWT.BORDER);
		textQtyCaliforniaFuschia.setText("0");
		textQtyCaliforniaFuschia.setBounds(458, 221, 25, 21);
		
		textQtyCatalinaCurrant = new Text(compositePlants, SWT.BORDER);
		textQtyCatalinaCurrant.setText("0");
		textQtyCatalinaCurrant.setBounds(628, 221, 25, 21);
		
		textQtySage = new Text(compositePlants, SWT.BORDER);
		textQtySage.setText("0");
		textQtySage.setBounds(628, 356, 25, 21);
		
		textQtyManzanita = new Text(compositePlants, SWT.BORDER);
		textQtyManzanita.setText("0");
		textQtyManzanita.setBounds(458, 356, 25, 21);
		
		textQtyDouglasIris = new Text(compositePlants, SWT.BORDER);
		textQtyDouglasIris.setText("0");
		textQtyDouglasIris.setBounds(288, 356, 25, 21);
		
		textQtyDeerGrass = new Text(compositePlants, SWT.BORDER);
		textQtyDeerGrass.setText("0");
		textQtyDeerGrass.setBounds(118, 356, 25, 21);
		
		TabItem tbtmTrees = new TabItem(tabFolder, SWT.NONE);
		tbtmTrees.setText("Trees");
		
		compositeTrees = new Composite(tabFolder, SWT.NONE);
		tbtmTrees.setControl(compositeTrees);
		
		Label lblJaredSylviaLandscapeTrees = new Label(compositeTrees, SWT.NONE);
		lblJaredSylviaLandscapeTrees.setText("Jared Sylvia Landscapes");
		lblJaredSylviaLandscapeTrees.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
		lblJaredSylviaLandscapeTrees.setBounds(215, 20, 314, 45);
		
		Label orderDescriptorTrees = new Label(compositeTrees, SWT.NONE);
		orderDescriptorTrees.setText("Tree Types");
		orderDescriptorTrees.setAlignment(SWT.CENTER);
		orderDescriptorTrees.setBounds(215, 65, 314, 15);
		
		Label lblTreeImage = new Label(compositeTrees, SWT.NONE);
		lblTreeImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/trees/Oak.png"));
		lblTreeImage.setBounds(93, 100, 275, 265);
		
		Label lblQtyTrees = new Label(compositeTrees, SWT.NONE);
		lblQtyTrees.setBounds(474, 230, 67, 15);
		lblQtyTrees.setText("Quantity: 0");
		
		Scale scaleQtyTrees = new Scale(compositeTrees, SWT.NONE);
		scaleQtyTrees.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblQtyTrees.setText(String.format("Quantity: %s", scaleQtyTrees.getSelection()));
				tree.setQty(scaleQtyTrees.getSelection());
			}
			
		});
		scaleQtyTrees.setPageIncrement(1);
		scaleQtyTrees.setMaximum(5);
		scaleQtyTrees.setBounds(465, 182, 204, 42);
		
		Combo comboTrees = new Combo(compositeTrees, SWT.NONE);
		comboTrees.setVisibleItemCount(4);
		comboTrees.setItems(new String[] {"Oak", "Japanese Maple", "Sweet Gum", "Modesto Ash"});
		comboTrees.setBounds(465, 120, 204, 23);
		comboTrees.setText("Oak");
		comboTrees.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(comboTrees.getSelectionIndex() == 0) {
					lblTreeImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/trees/Oak.png"));
					tree.setPrice(3500);
					tree.setType("Oak");
				}
				
				if(comboTrees.getSelectionIndex() == 1) {
					lblTreeImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/trees/JapaneseMaple.png"));
					tree.setPrice(125);
					tree.setType("Japanese Maple");
				}
				
				if(comboTrees.getSelectionIndex() == 2) {
					lblTreeImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/trees/SweetGum.png"));
					tree.setPrice(145.75);
					tree.setType("Sweet Gum");
					
				}
				
				if(comboTrees.getSelectionIndex() == 3) {
					lblTreeImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/trees/ModestoAsh.png"));
					tree.setPrice(150.50);
					tree.setType("Modesto Ash");
				}
								
			}
		});
		
		
		/* TODO: Need a second address field to differentiate between 
		 * bill to address and address where work should be done
		 * Additionally a check box that indicates whether billing and 
		 * work address are the same or different*/		
		TabItem tbtmInformation = new TabItem(tabFolder, SWT.NONE);
		tbtmInformation.setText("Information");
		
		compositeInfo = new Composite(tabFolder, SWT.NONE);
		compositeInfo.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
		tbtmInformation.setControl(compositeInfo);
		
		Label lblJaredSylviaLandscapeInfo = new Label(compositeInfo, SWT.NONE);
		lblJaredSylviaLandscapeInfo.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
		lblJaredSylviaLandscapeInfo.setBounds(215, 20, 314, 45);
		lblJaredSylviaLandscapeInfo.setText("Jared Sylvia Landscapes");
		
		Label lblOrderInformation = new Label(compositeInfo, SWT.NONE);
		lblOrderInformation.setAlignment(SWT.CENTER);
		lblOrderInformation.setBounds(215, 65, 314, 15);
		lblOrderInformation.setText("Order Information");
		
		Label lblName = new Label(compositeInfo, SWT.NONE);
		lblName.setBounds(75, 130, 55, 15);
		lblName.setText("Name:");
		
		Label lblAddress = new Label(compositeInfo, SWT.NONE);
		lblAddress.setBounds(75, 160, 55, 15);
		lblAddress.setText("Address:");
		
		Label lblAddress_1 = new Label(compositeInfo, SWT.NONE);
		lblAddress_1.setText("Address:");
		lblAddress_1.setBounds(75, 186, 55, 15);
		
		Label lblCity = new Label(compositeInfo, SWT.NONE);
		lblCity.setBounds(75, 217, 55, 15);
		lblCity.setText("City:");
		
		Label lblState = new Label(compositeInfo, SWT.NONE);
		lblState.setBounds(75, 243, 55, 15);
		lblState.setText("State:");
		
		Label lblZip = new Label(compositeInfo, SWT.NONE);
		lblZip.setBounds(215, 243, 55, 15);
		lblZip.setText("Zip Code:");
		
		Label lblYardDimensions = new Label(compositeInfo, SWT.NONE);
		lblYardDimensions.setFont(SWTResourceManager.getFont("Calibri", 14, SWT.NORMAL));
		lblYardDimensions.setBounds(75, 283, 142, 29);
		lblYardDimensions.setText("Yard Dimensions");
		
		Label lblLengthft = new Label(compositeInfo, SWT.NONE);
		lblLengthft.setBounds(75, 318, 66, 15);
		lblLengthft.setText("Length (ft.)");
		
		Label lblWidthft = new Label(compositeInfo, SWT.NONE);
		lblWidthft.setBounds(232, 318, 55, 15);
		lblWidthft.setText("Width (ft.)");
		
		textOrderName = new Text(compositeInfo, SWT.BORDER);
		textOrderName.setBounds(150, 130, 215, 21);
				
		textOrderAddressOne = new Text(compositeInfo, SWT.BORDER);
		textOrderAddressOne.setBounds(150, 160, 215, 21);
		
		textOrderAddressTwo = new Text(compositeInfo, SWT.BORDER);
		textOrderAddressTwo.setBounds(150, 186, 215, 21);
				
		textOrderCity = new Text(compositeInfo, SWT.BORDER);
		textOrderCity.setBounds(150, 211, 215, 21);
		
		textOrderState = new Text(compositeInfo, SWT.BORDER);
		textOrderState.setBounds(148, 238, 47, 21);
		
		textOrderZip = new Text(compositeInfo, SWT.BORDER);
		textOrderZip.setBounds(289, 243, 76, 21);
				
		textYardLength = new Text(compositeInfo, SWT.BORDER);
		textYardLength.setBounds(150, 318, 66, 21);
				
		textYardWidth = new Text(compositeInfo, SWT.BORDER);
		textYardWidth.setBounds(299, 315, 66, 21);
		
		textOrderInfo = new Text(compositeInfo, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textOrderInfo.setEditable(false);
		textOrderInfo.setBounds(420, 125, 300, 210);
		
		/* This method needs to be replaced, now that all tabs generate their relative
		 * objects those objects can be formed into an order. We need a method to store orders
		 * and customers separately with a field where they are linked, an order should have
		 * one customer (which is currently does) while a customer can have many orders.
		 * I'm not sure if this should be represented in the code or if it should be solely
		 * handled in a database.
		 * 
		 * I would imagine all options on all tabs to be stored in a relational database
		 * and generated dynamically, allowing for this app to be deployed on multiple sales
		 * machines while staying synced to current prices and options.
		 * 
		 * A backend management app with direct access to a database and various API endpoints
		 * could easily allow a scalable and adaptive solution.
		*/
		Button btnCalculate = new Button(compositeInfo, SWT.NONE);
		btnCalculate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(validateInputs() == false) {
					return;
				}
				Customer cust = createCustomer();
				Double yardArea = Double.parseDouble(textYardWidth.getText()) * Double.parseDouble(textYardLength.getText());
				Double yardCost = yardArea * 5; 

				textOrderInfo.setText(String.format("%s\nWidth: %s, Length: %s\nArea: %s, Cost: %s", 
						cust.getDetails(),textYardWidth.getText(),textYardLength.getText(),yardArea,yardCost));
				
			}
		});
		btnCalculate.setBounds(195, 362, 75, 25);
		btnCalculate.setText("Calculate");
		
		Button btnSubmitOrder = new Button(compositeInfo, SWT.NONE);
		btnSubmitOrder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				submitOrder();
			}
		});
		btnSubmitOrder.setBounds(533, 362, 83, 25);
		btnSubmitOrder.setText("Submit Order");
		
		TabItem tbtmCustomerList = new TabItem(tabFolder, SWT.NONE);
		tbtmCustomerList.setText("Customer List");
		
		Composite compositeCustomerList = new Composite(tabFolder, SWT.NONE);
		tbtmCustomerList.setControl(compositeCustomerList);
		
		Label lblJaredSylviaLandscapeInfo_1 = new Label(compositeCustomerList, SWT.NONE);
		lblJaredSylviaLandscapeInfo_1.setText("Jared Sylvia Landscapes");
		lblJaredSylviaLandscapeInfo_1.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
		lblJaredSylviaLandscapeInfo_1.setBounds(215, 20, 314, 45);
		
		Label lblOrderList = new Label(compositeCustomerList, SWT.NONE);
		lblOrderList.setText("Order List");
		lblOrderList.setAlignment(SWT.CENTER);
		lblOrderList.setBounds(215, 65, 314, 15);
		
		listOrders = new org.eclipse.swt.widgets.List(compositeCustomerList, SWT.BORDER);
		populateFromDB();
		listOrders.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedOrderFull = listOrders.getItem(listOrders.getSelectionIndex());
				String selectedOrderSub = selectedOrderFull.substring(selectedOrderFull.indexOf("(")+1, selectedOrderFull.indexOf(")"));
											
				int selectedOrderID = Integer.parseInt(selectedOrderSub);
				
				for (Order order: orders) {
					if(order.getOrderID() == selectedOrderID) {
						textSelectedOrder.setText(order.toString());
					}
				}
			}
		});
		
		listOrders.setBounds(25, 100, 325, 250);
		
		textSelectedOrder = new Text(compositeCustomerList, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textSelectedOrder.setBounds(400, 100, 325, 250);
		
		Button btnDeleteOrder = new Button(compositeCustomerList, SWT.NONE);
		btnDeleteOrder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedOrderFull = listOrders.getItem(listOrders.getSelectionIndex());
				String selectedOrderSub = selectedOrderFull.substring(selectedOrderFull.indexOf("(")+1, selectedOrderFull.indexOf(")"));
				orderDB.deleteEntry(Integer.parseInt(selectedOrderSub));
				Order selectedOrder = new Order();
				for (Order order: orders) {
					if (order.getOrderID() == Integer.parseInt(selectedOrderSub)) {
						selectedOrder = order;
					}
				}
				orders.remove(selectedOrder);
				listOrders.remove(listOrders.getSelectionIndex());
				textSelectedOrder.setText("");
				
			}
		});
		btnDeleteOrder.setBounds(650, 362, 75, 25);
		btnDeleteOrder.setText("Delete Order");
		
		Menu menu = new Menu(shlJaredSylviaLandscapes, SWT.BAR);
		shlJaredSylviaLandscapes.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menuFile = new Menu(mntmFile);
		mntmFile.setMenu(menuFile);
		
		MenuItem mntmSave = new MenuItem(menuFile, SWT.NONE);
		mntmSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String fileName = JOptionPane.showInputDialog("Enter file name: ");
				OrderIO outputFileName = new OrderIO(fileName);
				outputFileName.saveData(orders);
			}
		});
		mntmSave.setText("Save");
		
		MenuItem mntmOpen = new MenuItem(menuFile, SWT.NONE);
		mntmOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String fileName = JOptionPane.showInputDialog("Enter file name: ");
				OrderIO inputFileName = new OrderIO(fileName);
				ArrayList<Order> loadOrders = inputFileName.getData();
				for(Order order : loadOrders) {
					listOrders.add(String.format("%s - (%s)", order.getCustomer().getName(), order.getOrderID()));
			        orders.add(order);
				}
			}
		});
		mntmOpen.setText("Open");
		
		MenuItem mntmExit = new MenuItem(menuFile, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mntmExit.setText("Exit");
		
		MenuItem mntmOrder = new MenuItem(menu, SWT.CASCADE);
		mntmOrder.setText("Order");
		
		Menu menuOrder = new Menu(mntmOrder);
		mntmOrder.setMenu(menuOrder);
		
		MenuItem mntmReset = new MenuItem(menuOrder, SWT.NONE);
		mntmReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reset();
			}
		});
		mntmReset.setText("Reset");
		
		MenuItem mntmSubmitOrder = new MenuItem(menuOrder, SWT.NONE);
		mntmSubmitOrder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				submitOrder();
			}
		});
		mntmSubmitOrder.setText("Submit Order");
		
		Button btnNext = new Button(shlJaredSylviaLandscapes, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(tabFolder.getSelectionIndex() == (tabFolder.getItems().length - 1)) {
					tabFolder.setSelection(0);
				}
				
				else {
				tabFolder.setSelection(tabFolder.getSelectionIndex() + 1);
				}
				
			}
		});
		btnNext.setText("Next");
		btnNext.setBounds(703, 450, 75, 25);
		
		Button btnPrevious = new Button(shlJaredSylviaLandscapes, SWT.NONE);
		btnPrevious.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(tabFolder.getSelectionIndex() == 0) {
					tabFolder.setSelection(tabFolder.getItems().length -1);
				}
				else {
					tabFolder.setSelection(tabFolder.getSelectionIndex() - 1);
				}
			}
		});
		btnPrevious.setBounds(10, 450, 75, 25);
		btnPrevious.setText("Previous");
		tabFolder.setSelection(0);
		
		Button btnStartOver = new Button(shlJaredSylviaLandscapes, SWT.NONE);
		btnStartOver.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reset();
			}
		});
		btnStartOver.setBounds(91, 450, 75, 25);
		btnStartOver.setText("Start Over");
		

	}
}
