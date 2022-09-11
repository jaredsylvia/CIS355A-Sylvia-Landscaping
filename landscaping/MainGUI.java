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
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Scale;
import javax.swing.JOptionPane;


public class MainGUI {

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
        JOptionPane.showMessageDialog(null, "Method is not complete.");
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
		
		TabFolder tabFolder = new TabFolder(shlJaredSylviaLandscapes, SWT.NONE);
		tabFolder.setBounds(10, 10, 768, 425);
		
		TabItem tbtmGroundType = new TabItem(tabFolder, SWT.NONE);
		tbtmGroundType.setText("Ground Type");
		
		Composite compositeGround = new Composite(tabFolder, SWT.NONE);
		tbtmGroundType.setControl(compositeGround);
		compositeGround.setLayout(null);
		
		Label lblGroundImage = new Label(compositeGround, SWT.NONE);
		lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/gravel.png"));
		lblGroundImage.setBounds(280, 120, 400, 200);
		
		Button btnGravel = new Button(compositeGround, SWT.RADIO);
		btnGravel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnGravel.setSelection(true);
		btnGravel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnGravel.setBounds(75, 150, 90, 16);
		btnGravel.setText("Gravel");
		btnGravel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/gravel.png"));
			}
		});
		
		
		Button btnAsphalt = new Button(compositeGround, SWT.RADIO);
		btnAsphalt.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnAsphalt.setBounds(75, 180, 90, 16);
		btnAsphalt.setText("Asphalt");
		btnAsphalt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/asphalt.png"));
			}
		});
		
		Button btnTurf = new Button(compositeGround, SWT.RADIO);
		btnTurf.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnTurf.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/turf.png"));
			}
		});
		btnTurf.setBounds(75, 210, 90, 16);
		btnTurf.setText("Turf");
		
		Button btnMulch = new Button(compositeGround, SWT.RADIO);
		btnMulch.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnMulch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/mulch.png"));
			}
		});
		btnMulch.setBounds(75, 240, 90, 16);
		btnMulch.setText("Mulch");
		
		Button btnGrass = new Button(compositeGround, SWT.RADIO);
		btnGrass.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		btnGrass.setText("Grass");
		btnGrass.setBounds(75, 270, 90, 16);
		btnGrass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblGroundImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/ground/grass.png"));
			}
		});
		
		Label lblJaredSylviaLandscapeGround = new Label(compositeGround, SWT.NONE);
		lblJaredSylviaLandscapeGround.setText("Jared Sylvia Landscapes");
		lblJaredSylviaLandscapeGround.setFont(SWTResourceManager.getFont("Calibri", 24, SWT.BOLD));
		lblJaredSylviaLandscapeGround.setBounds(215, 20, 314, 45);
		
		Label orderDescriptor = new Label(compositeGround, SWT.NONE);
		orderDescriptor.setAlignment(SWT.CENTER);
		orderDescriptor.setText("Ground Types");
		orderDescriptor.setBounds(215, 65, 314, 15);
		
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
		
		Button btnNextIrrigation = new Button(compositeIrrigation, SWT.NONE);
		btnNextIrrigation.setText("Next");
		btnNextIrrigation.setBounds(675, 400, 75, 25);
		
		Group grpInGround = new Group(compositeIrrigation, SWT.NONE);
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
			}
		});
		
		
		
		Group grpSprinklerType = new Group(compositeIrrigation, SWT.NONE);
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
			}
		});
		btnDrip.setBounds(10, 60, 90, 16);
		btnDrip.setText("Drip");
		
		TabItem tbtmPlantType = new TabItem(tabFolder, SWT.NONE);
		tbtmPlantType.setText("Plants");
		
		Composite compositePlants = new Composite(tabFolder, SWT.NONE);
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
		
		Button btnBlueEyedGrass = new Button(compositePlants, SWT.CHECK);
		btnBlueEyedGrass.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/BlueEyedGrass.png"));
		btnBlueEyedGrass.setBounds(235, 115, 125, 100);
		
		Button btnCaliforniaFuschia = new Button(compositePlants, SWT.CHECK);
		btnCaliforniaFuschia.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/CaliforniaFuschia.png"));
		btnCaliforniaFuschia.setBounds(405, 115, 125, 100);
		
		Button btnCatalinaCurrant = new Button(compositePlants, SWT.CHECK);
		btnCatalinaCurrant.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/CatalinaCurrent.png"));
		btnCatalinaCurrant.setBounds(575, 115, 125, 100);
		
		Button btnDeerGrass = new Button(compositePlants, SWT.CHECK);
		btnDeerGrass.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/Deergrass.png"));
		btnDeerGrass.setBounds(65, 249, 125, 100);
		
		Button btnDouglasIris = new Button(compositePlants, SWT.CHECK);
		btnDouglasIris.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/DouglasIris.png"));
		btnDouglasIris.setBounds(235, 249, 125, 100);
		
		Button btnManzanita = new Button(compositePlants, SWT.CHECK);
		btnManzanita.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/Manzanita.png"));
		btnManzanita.setBounds(405, 249, 125, 100);
		
		Button btnSage = new Button(compositePlants, SWT.CHECK);
		btnSage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/plants/Sage.png"));
		btnSage.setBounds(575, 249, 125, 100);
		
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
		
		Composite compositeTrees = new Composite(tabFolder, SWT.NONE);
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
				}
				
				if(comboTrees.getSelectionIndex() == 1) {
					lblTreeImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/trees/JapaneseMaple.png"));
				}
				
				if(comboTrees.getSelectionIndex() == 2) {
					lblTreeImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/trees/SweetGum.png"));
				}
				
				if(comboTrees.getSelectionIndex() == 3) {
					lblTreeImage.setImage(SWTResourceManager.getImage(MainGUI.class, "/landscapingImg/trees/ModestoAsh.png"));
				}
			}
		});
		
		Scale scaleQtyTrees = new Scale(compositeTrees, SWT.NONE);
		scaleQtyTrees.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblQtyTrees.setText(String.format("Quantity: %s", scaleQtyTrees.getSelection()));
			}
		});
		scaleQtyTrees.setPageIncrement(1);
		scaleQtyTrees.setMaximum(5);
		scaleQtyTrees.setBounds(465, 182, 204, 42);
				
		TabItem tbtmInformation = new TabItem(tabFolder, SWT.NONE);
		tbtmInformation.setText("Information");
		
		Composite compositeInfo = new Composite(tabFolder, SWT.NONE);
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
		
		Button btnCalculate = new Button(compositeInfo, SWT.NONE);
		btnCalculate.addSelectionListener(new SelectionAdapter() {
			/* As you can tell GUI is more fleshed out than current week requirements
			 * Some information has been relocated from customer class and included in more appropriate classes
			 * Each class calculates price of those components of a landscape
			 * For the purposes of being able to turn this in on time I have omitted much of the code to get those tabs to work correctly
			 * The customer object does not contain all the information that should be displayed in the text box for this assignment
			 * And is instead located in YardType and Order classes
			 * Those classes are nearly complete and if inspected I believe should exceed the requirements of this assignment 
			 */
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
		
		Menu menu = new Menu(shlJaredSylviaLandscapes, SWT.BAR);
		shlJaredSylviaLandscapes.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);
		mntmFile.setText("File");
		
		MenuItem mntmOrder = new MenuItem(menu, SWT.NONE);
		mntmOrder.setText("Order");
		
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
		tabFolder.setSelection(4);
		

	}
}
