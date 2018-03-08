package gui;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = -5511353684955783810L;
	JMenu menu = null;
	JMenu menu2 = null;
	JMenuItem itemSave = null;
	JMenuItem itemLoad = null;
	JMenuItem itemColorChoosing = null;
	ButtonGroup radioButtonGroup = null;
	JRadioButton radioButtonStraight = null;
	JRadioButton radioButtonHand = null;
	JRadioButton radioButtonRectangle = null;
	JRadioButton radioButtonOval = null;
	JCheckBox checkBoxFilled = null;

	public MenuBar() {
		this.menu = new JMenu("Datei");
		this.menu2 = new JMenu("Bearbeiten");
		
		this.itemSave = new JMenuItem("Speichern unter ...");
		this.itemLoad = new JMenuItem("Laden ...");
		
		this.itemColorChoosing = new JMenuItem("Farbe wählen ...");
		this.radioButtonGroup = new ButtonGroup();
		this.radioButtonStraight = new JRadioButton("Gerade");
		this.radioButtonRectangle = new JRadioButton("Viereck");
		this.radioButtonOval = new JRadioButton("Oval");
		this.radioButtonHand = new JRadioButton("Freihand");
		this.radioButtonGroup.add(this.radioButtonStraight);
		this.radioButtonGroup.add(this.radioButtonRectangle);
		this.radioButtonGroup.add(this.radioButtonOval);
		this.radioButtonGroup.add(this.radioButtonHand);
		this.checkBoxFilled = new JCheckBox("ausgefüllt");
		
		this.menu.add(this.itemSave);
		this.menu.add(this.itemLoad);
		
		this.menu2.add(this.itemColorChoosing);
		this.menu2.addSeparator();
		this.menu2.add(this.radioButtonStraight);
		this.menu2.add(this.radioButtonRectangle);
		this.menu2.add(this.radioButtonOval);
		this.menu2.add(this.radioButtonHand);
		this.menu2.addSeparator();
		this.menu2.add(this.checkBoxFilled);

		this.add(this.menu);
		this.add(this.menu2);
	}
}
