
/**Program Name: Java Class Maker
 * Program Purpose: 
 * A tool for the user to quickly make a Java class
 * 
 * @author Reynaldo Santos
 */

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.Scanner;

public class JavaClassMaker extends JFrame {
    /* Fields, GUI components */
    // main components
    private JPanel mainClassPanel = new JPanel();
    private Box mainVertBox = Box.createVerticalBox();

    // 1st component set, modifiers
    private Box modifierBox = Box.createHorizontalBox();
    private Box varMakerBox = Box.createHorizontalBox();
    private Box finalAccessBox = Box.createVerticalBox();
    private Box accessModifierBox = Box.createVerticalBox();
    private JRadioButton notfinalBtn = new JRadioButton("No");
    private JRadioButton finalBtn = new JRadioButton("Yes");
    private JCheckBox noModifierCheckBox = new JCheckBox("None");
    private JCheckBox publicCheckBox = new JCheckBox("public");
    private JCheckBox abstractCheckBox = new JCheckBox("abstract");
    private ButtonGroup finalGroup = new ButtonGroup();
    private JPanel nameOfClassTxtFieldPanel = new JPanel();
    private JTextField nameOfClassTxtField = new JTextField(13);

    // 2nd component set, attributes, constructor, getters, setters
    private JPanel attributesPanel = new JPanel();
    private Box scrollPaneVertBox = Box.createVerticalBox();
    private Box getrsSetrsHorizonBox = Box.createHorizontalBox();
    private JTextArea attributeTextArea = new JTextArea(4, 20);
    private Box varsBox = Box.createVerticalBox();
    private JScrollPane attrScrollPane = new JScrollPane(varsBox);
    private JCheckBox constructParamCheckBox = new JCheckBox("In Constructor Parameter");
    private JCheckBox gettersCheckBox = new JCheckBox("Getters");
    private JCheckBox settersCheckBox = new JCheckBox("Setters");

    // 3rd component set, main method placement
    private JPanel mainMethodPanel = new JPanel();
    private JRadioButton bottomMainMethodRadBtn = new JRadioButton("Bottom");
    private JRadioButton topMainMethodRadBtn = new JRadioButton("Top");
    private JRadioButton noMainMethodRadBtn = new JRadioButton("None");
    private ButtonGroup mainMethodBtnGroup = new ButtonGroup();

    // 4th component set, make class button
    private JPanel makeClassPanel = new JPanel();
    private JButton makeClassBtn = new JButton("Make class");
    private JButton interfaceButton = new JButton("Create interface");

    // Other variables
    // fonts
    private Font fontOne = new Font("Consolas", Font.BOLD, 18);
    private Font fontTwo = new Font("Consolas", Font.BOLD, 20);
    
    /* Variables for method - public void makeClass() */
    private JFrame classMkrFrame = new JFrame();
    private JPanel classMkrPanel = new JPanel();
    private JButton clipBoardBtn = new JButton("Copy to Clipboard");
    private JTextArea classTextArea = new JTextArea();
    private JScrollPane textAreaScrollPane = new JScrollPane(classTextArea);
    private ArrayList<String> attributesList = new ArrayList<>();
    private ArrayList<String> attriParamList = new ArrayList<>();
    private String classStr = "class", leftCurlyBrace = "{", rightCurlyBrace = "}";
    private String textAreaAttributes = "";
    private Scanner scanAttribute = null;

    // Vars for getter & setter method
    private String accMod = "";
    private String varName = "";
    private String capStr = "";
    private String newName = "";

    /* Constructor */
    public JavaClassMaker() {
	super("Java Class Maker");
	setSize(430, 515);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setResizable(false);

	/* 1st component setup */
	// final modifier button group setup
	finalGroup.add(notfinalBtn);
	finalGroup.add(finalBtn);

	// finalAccessBox setup
	finalAccessBox.setBorder(BorderFactory.createTitledBorder("Final Modifier"));
	finalAccessBox.setPreferredSize(new Dimension(100, 80));
	finalAccessBox.add(notfinalBtn);
	finalAccessBox.add(finalBtn);

	// accessModifierBox setup
	accessModifierBox.setBorder(BorderFactory.createTitledBorder("Access Modifier"));
	accessModifierBox.setPreferredSize(new Dimension(140, 120));
	accessModifierBox.add(noModifierCheckBox);
	accessModifierBox.add(publicCheckBox);
	accessModifierBox.add(abstractCheckBox);
	accessModifierBox.add(interfaceButton);

	// name of class setup
	nameOfClassTxtFieldPanel.setBorder(BorderFactory.createTitledBorder("Name of class: "));
	nameOfClassTxtFieldPanel.setPreferredSize(new Dimension(5, 5));
	nameOfClassTxtField.setPreferredSize(new Dimension(10, 30));
	nameOfClassTxtField.setFont(fontOne);
	nameOfClassTxtFieldPanel.add(nameOfClassTxtField);
	nameOfClassTxtFieldPanel.add(interfaceButton);

	// components added to modifierBox
	modifierBox.setBorder(BorderFactory.createTitledBorder("Modifiers"));
	modifierBox.add(finalAccessBox);
	modifierBox.add(accessModifierBox);
	modifierBox.add(nameOfClassTxtFieldPanel);

	/* 2nd component setup */
	// scroll pane setup
	attributeTextArea.setFont(fontTwo);
	attributeTextArea.setText("Declare vars properly: \nmodifier, data type, var name\n\nDO NOT  initialize\nAdd ';' for each var");
	attrScrollPane = new JScrollPane(attributeTextArea);
	attrScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	attrScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	attrScrollPane.setBorder(BorderFactory.createEtchedBorder());
	attrScrollPane.setBorder(BorderFactory.createTitledBorder("Declare Variables"));

	attrScrollPane.setPreferredSize(new Dimension(375, 600));

	// set up getters & setters horizontal grouping
	getrsSetrsHorizonBox.add(constructParamCheckBox);
	getrsSetrsHorizonBox.add(gettersCheckBox);
	getrsSetrsHorizonBox.add(settersCheckBox);

	// all added to scrollPaneVertBox
	scrollPaneVertBox.setPreferredSize(new Dimension(375, 225));
	scrollPaneVertBox.add(attrScrollPane);
	scrollPaneVertBox.add(getrsSetrsHorizonBox);

	// all added to attributesPanel
	attributesPanel.setBorder(BorderFactory.createTitledBorder("Start Attribute setup"));
	attributesPanel.add(scrollPaneVertBox);

	/* 3rd component setup */
	// mainMethod button group setup
	mainMethodBtnGroup.add(bottomMainMethodRadBtn);
	mainMethodBtnGroup.add(topMainMethodRadBtn);
	mainMethodBtnGroup.add(noMainMethodRadBtn);

	// all added to mainMethodPanel
	mainMethodPanel.setBorder(BorderFactory.createTitledBorder("Main Method Placement"));
	mainMethodPanel.setPreferredSize(new Dimension(0, 25));
	mainMethodPanel.add(bottomMainMethodRadBtn);
	mainMethodPanel.add(topMainMethodRadBtn);
	mainMethodPanel.add(noMainMethodRadBtn);

	/* 4th component setup */
	makeClassPanel.add(makeClassBtn);
	makeClassPanel.add(interfaceButton);

	/* All components added to the mains */
	mainVertBox.add(modifierBox).setPreferredSize(new Dimension(0, 115));
	mainVertBox.add(attributesPanel);
	mainVertBox.add(mainMethodPanel).setPreferredSize(new Dimension(0, 60));
	mainVertBox.add(makeClassPanel);
	mainVertBox.setPreferredSize(new Dimension(400, 475));
	mainClassPanel.add(mainVertBox);
	add(mainClassPanel);

	// visibility
	setVisible(true);

	// default button choices
	notfinalBtn.setSelected(true);
	publicCheckBox.setSelected(true);
	bottomMainMethodRadBtn.setSelected(true);

	// If abstract check box is selected
	abstractCheckBox.addItemListener(a -> {
	    if (abstractCheckBox.isSelected()) {
		finalBtn.setEnabled(false);
		bottomMainMethodRadBtn.setEnabled(false);
		topMainMethodRadBtn.setEnabled(false);
		noMainMethodRadBtn.setSelected(true);
	    }

	    else if (!abstractCheckBox.isSelected()) {
		finalBtn.setEnabled(true);
		bottomMainMethodRadBtn.setEnabled(true);
		topMainMethodRadBtn.setEnabled(true);
	    }

	});
	
	// makes interface
	interfaceButton.setToolTipText("No access modifiers is need and initialize variables.");
		interfaceButton.addActionListener(in -> {
		    makeInterface();
		});

	// makes the class once user presses "Make class" button
	makeClassBtn.addActionListener(mc -> {
	    makeClass();
	});

    } // END OF Constructor

    // Makes the user's class
    public void makeClass() {
	// components for GUI
	classMkrFrame = new JFrame();
	classMkrPanel = new JPanel();
	clipBoardBtn = new JButton("Copy to Clipboard");
	textAreaScrollPane = new JScrollPane(classTextArea);
	Font font = new Font("Consolas", Font.BOLD, 12);
	classTextArea.setFont(font);

	// classMkrFrame windows setup
	classMkrFrame.setSize(400, 580);
	classMkrFrame.setLocationRelativeTo(null);
	classMkrFrame.setVisible(true);
	classMkrFrame.setResizable(false);

	// textAreaScrollPane scrollbar setup
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	textAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(350, 500));

	// textAreaScrollPane added to frame
	classMkrPanel.add(textAreaScrollPane);
	classMkrPanel.add(clipBoardBtn);

	// Window is created
	classMkrFrame.add(classMkrPanel);

	// scans attributes
	textAreaAttributes = attributeTextArea.getText();
	scanAttribute = new Scanner(textAreaAttributes);
	scanAttribute.useDelimiter(";");

	// scans textAreaAttributes JTextArea
	while (scanAttribute.hasNext()) {
	    attributesList.add(scanAttribute.nextLine());
	}

	/* Builds class */
	// main method at the bottom
	if (bottomMainMethodRadBtn.isSelected()) {
	    classTextArea.append(decideFinalClass() + addPublicModifer() + addAbstractModifer() + classStr + " "
		    + nameOfClassTxtField.getText() + " " + leftCurlyBrace);

	    // prints attributes
	    for (int i = 0; i < attributesList.size(); i++) {
		classTextArea.append("\n    " + attributesList.get(i));
	    }

	    /* Constructor is made */
	    makeConstructor();
	    classTextArea.append("\n\n");

	    /* Makes getters */
	    makeGetters();

	    /* Makes setters */
	    makeSetters();

	    // prints main method
	    classTextArea.append("    public static void main(String[] args) {\n\n" + "    }");

	    // end of class
	    classTextArea.append("\n\n" + rightCurlyBrace);
	}

	// main method at the top
	else if (topMainMethodRadBtn.isSelected()) {
	    classTextArea.append(decideFinalClass() + addPublicModifer() + addAbstractModifer() + classStr + " "
		    + nameOfClassTxtField.getText() + " " + leftCurlyBrace);

	    // prints attributes
	    for (int i = 0; i < attributesList.size(); i++) {
		classTextArea.append("\n    " + attributesList.get(i));
	    }

	    // prints main method
	    classTextArea.append("\n\n    public static void main(String[] args) {\n\n" + "    }");

	    /* Constructor is made */
	    makeConstructor();
	    classTextArea.append("\n\n");

	    /* Makes getters */
	    makeGetters();

	    /* Makes setters */
	    makeSetters();

	    classTextArea.append(rightCurlyBrace);
	}

	// no main method
	else if (noMainMethodRadBtn.isSelected()) {
	    classTextArea.append(decideFinalClass() + addPublicModifer() + addAbstractModifer() + classStr + " "
		    + nameOfClassTxtField.getText() + " " + leftCurlyBrace);

	    // prints attributes
	    for (int i = 0; i < attributesList.size(); i++) {
		classTextArea.append("\n    " + attributesList.get(i));
	    }

	    /* Constructor is made */
	    makeConstructor();

	    /* Makes getters */
	    makeGetters();

	    /* Makes setters */
	    makeSetters();

	    classTextArea.append("\n" + rightCurlyBrace);
	}
	
	// closes scanner
	scanAttribute.close();

	// clears data when button is clicked again
	makeClassBtn.addActionListener(mc -> {
	    classMkrFrame.dispose();
	    classTextArea.setText("");
	    attributesList.removeAll(attributesList);
	    attriParamList.removeAll(attriParamList);
	});

	// copies to clipboard
	clipBoardBtn.addActionListener(c -> {
	    StringSelection stringSelection = new StringSelection(classTextArea.getText());
	    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clpbrd.setContents(stringSelection, null);
	});
    }

    // makes interface
    public void makeInterface() {
	// components for GUI
	classMkrFrame = new JFrame();
	classMkrPanel = new JPanel();
	clipBoardBtn = new JButton("Copy to Clipboard");
	Font font = new Font("Consolas", Font.BOLD, 12);
	classTextArea.setFont(font);
	textAreaScrollPane = new JScrollPane(classTextArea);

	// classMkrFrame windows setup
	classMkrFrame.setSize(375, 375);
	classMkrFrame.setLocationRelativeTo(null);
	classMkrFrame.setVisible(true);
	classMkrFrame.setResizable(false);

	// textAreaScrollPane scrollbar setup
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	textAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(350, 300));

	// textAreaScrollPane added to frame
	classMkrPanel.add(textAreaScrollPane);
	classMkrPanel.add(clipBoardBtn);

	// Window is created
	classMkrFrame.add(classMkrPanel);

	// scans attributes
	textAreaAttributes = attributeTextArea.getText();
	scanAttribute = new Scanner(textAreaAttributes);
	scanAttribute.useDelimiter(";");

	// scans textAreaAttributes JTextArea
	while (scanAttribute.hasNext()) {
	    attributesList.add(scanAttribute.nextLine());
	}

	classTextArea.append(addPublicModifer() + "interface " + nameOfClassTxtField.getText() + " " + leftCurlyBrace);

	// prints attributes
	for (int i = 0; i < attributesList.size(); i++) {
	    classTextArea.append("\n    " + attributesList.get(i));
	}

	classTextArea.append("\n" + rightCurlyBrace);

	// clears data when button is clicked again
	interfaceButton.addActionListener(mc -> {
	    classMkrFrame.dispose();
	    classTextArea.setText("");
	    attributesList.removeAll(attributesList);
	    attriParamList.removeAll(attriParamList);
	});
	
	// closes scanner
	scanAttribute.close();

	// copies to clipboard
	clipBoardBtn.addActionListener(c -> {
	    StringSelection stringSelection = new StringSelection(classTextArea.getText());
	    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clpbrd.setContents(stringSelection, null);
	});
    }

    // Creates constructor
    public void makeConstructor() {
	// vars
	String field = "", paramVar = "", removeStr = "";

	// if user selects to put field in constructor parameter
	if (constructParamCheckBox.isSelected()) {
	    while (scanAttribute.hasNext()) {
		attributesList.add(scanAttribute.next());
	    }

	    // prints field
	    classTextArea.append("\n\n    " + addPublicModifer() + nameOfClassTxtField.getText() + "(");

	    // puts field in constructor parameter
	    for (int i = 0; i < attributesList.size(); i++) {
		if (attributesList.get(i).contains("private ")) {
		    removeStr = attributesList.get(i).replaceAll("private ", "");
		}

		else if (attributesList.get(i).contains("public ")) {
		    removeStr = attributesList.get(i).replaceAll("public ", "");
		}

		else if (attributesList.get(i).contains("protected ")) {
		    removeStr = attributesList.get(i).replaceAll("protected ", "");
		}

		field = removeStr.replaceAll(";", "");

		attriParamList.add(field);

		if (!attributesList.get(i).equals(attributesList.get(attributesList.size() - 1))) {
		    classTextArea.append(field + ", ");
		}

		else {
		    classTextArea.append(field + ")");
		}
	    }

	    // parameters are put in constructor
	    classTextArea.append(" {");

	    while (scanAttribute.hasNext(";")) {
		attriParamList.add(scanAttribute.nextLine());
	    }

	    for (int i = 0; i < attriParamList.size(); i++) {
		// takes the last word of the line
		paramVar = attriParamList.get(i).replaceAll("^.*?(\\w+)\\W*$", "$1");
		classTextArea.append("\n        this." + paramVar + " = " + paramVar + ";");
	    }

	    classTextArea.append("\n    }");
	}

	// if user DOESN'T select to put field in constructor parameter
	else {
	    classTextArea.append("\n\n    " + nameOfClassTxtField.getText() + "() {\n    }");
	}

	// closes scanner
	scanAttribute.close();

    } // END OF public void makeConstructor()

    // Creates getter methods
    public void makeGetters() {
	// if getter button is selected
	if (gettersCheckBox.isSelected()) {
	    for (String gtr : attriParamList) {
		scanAttribute = new Scanner(gtr.toString());
		scanAttribute.useDelimiter("\\s+");

		while (scanAttribute.hasNext()) {
		    accMod = scanAttribute.next();
		    varName = scanAttribute.next();
		}

		for (int i = 0; i < varName.length(); i++) {
		    capStr = varName.substring(0, 1).toUpperCase();
		    newName = varName.substring(1);
		}

		// constructs getters
		classTextArea.append("    public " + accMod + " get" + capStr + newName + "() {\n" + "        return "
			+ varName + ";" + "\n    }\n\n");

		// closes scanner
		scanAttribute.close();
	    }
	}

	else {
	    // getter method is not made
	}

    }

    // Creates setter methods
    public void makeSetters() {
	// if getter button is selected
	if (settersCheckBox.isSelected()) {
	    for (String gtr : attriParamList) {
		scanAttribute = new Scanner(gtr.toString());
		scanAttribute.useDelimiter("\\s+");

		while (scanAttribute.hasNext()) {
		    accMod = scanAttribute.next();
		    varName = scanAttribute.next();
		}

		for (int i = 0; i < varName.length(); i++) {
		    capStr = varName.substring(0, 1).toUpperCase();
		    newName = varName.substring(1);
		}

		// constructs getters
		classTextArea.append("    public void" + " set" + capStr + newName + "(" + accMod + " " + varName + ")"
			+ "{\n" + "        this." + varName + " = " + varName + ";" + "\n    }\n\n");

		// closes scanner
		scanAttribute.close();
	    }
	}

	else {
	    // setter method is not made
	}

    }

    // Decides final modifier
    public String decideFinalClass() {
	if (notfinalBtn.isSelected()) {
	    return "";
	}

	else if (finalBtn.isSelected()) {
	    return "final ";
	}

	return "";
    }

    // Decides access modifier
    public String addPublicModifer() {
	String modifier = "";

	if (noModifierCheckBox.isSelected()) {
	    modifier = "";
	}

	else if (publicCheckBox.isSelected()) {
	    modifier = "public ";
	}

	return modifier;
    }

    public String addAbstractModifer() {
	String modifier = "";

	if (abstractCheckBox.isSelected()) {
	    modifier = "abstract ";
	}

	return modifier;
    }

    /* MAIN METHOD */
    public static void main(String[] args) {
	JavaClassMaker test = new JavaClassMaker();
    }

}
