package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1316960485995418441L;
	private MainFrame mainFrame = this;
	MenuBar menuBar = null;
	PaintPanel paintPanel = null;
	ColorPanel colorPanel = null;
	JFileChooser fileChooser = null;
	String path = null;
	boolean isDroppedDown = false;
	boolean filled = false;
	boolean menuBarActive = false;

	public MainFrame(String identifier) throws HeadlessException {
		super(identifier);
		this.setPreferredSize(new Dimension(960, 540));
		this.initializeControls();
	}

	private void initializeControls() {
		GridLayout grid = new GridLayout(1, 1);

		this.colorPanel = new ColorPanel();
		this.paintPanel = new PaintPanel(960, 540, Color.BLACK, Color.WHITE);
		this.paintPanel.setStraight(false);
		this.fileChooser = new JFileChooser();
		this.path = "";
		this.isDroppedDown = false;
		this.menuBarActive = false;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		this.menuBar = new MenuBar();
		
		this.menuBar.itemColorChoosing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintPanel.setColor(ColorPanel.showDialog(mainFrame, "Farbe wählen ...", Color.BLACK));
			}
		});

		// SAVE
		this.menuBar.itemSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int returnVal = fileChooser.showSaveDialog(mainFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						ImageIO.write(paintPanel.getImage(), "png", file);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else {
					// Abgebrochen
				}
			}
		});

		// LOAD
		this.menuBar.itemLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int returnVal = fileChooser.showOpenDialog(mainFrame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					BufferedImage image = new BufferedImage(600, 300, BufferedImage.TYPE_INT_ARGB);
					try {
						image = ImageIO.read(file);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					paintPanel.setImage(image);
					paintPanel.updateImage();
					paintPanel.setSize(image.getWidth(), image.getHeight());
					mainFrame.setSize(image.getWidth(), image.getHeight());
				} else {
					// Abgebrochen
				}
			}
		});

		this.menuBar.radioButtonStraight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintPanel.setStraight(true);
				menuBar.checkBoxFilled.setEnabled(false);
			}
		});
		
		this.menuBar.radioButtonRectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintPanel.setRectangle(true);
				menuBar.checkBoxFilled.setEnabled(true);
			}
		});
		
		this.menuBar.radioButtonOval.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintPanel.setOval(true);
				menuBar.checkBoxFilled.setEnabled(true);
			}
		});
		
		this.menuBar.radioButtonHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paintPanel.setStraight(false);
				menuBar.checkBoxFilled.setEnabled(false);
			}
		});
		
		this.menuBar.radioButtonHand.setSelected(true);
		this.menuBar.checkBoxFilled.setEnabled(false);
		
		this.menuBar.checkBoxFilled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(paintPanel.isFilled()) {
					paintPanel.setFilled(false);
				} else {
					paintPanel.setFilled(true);
				}
			}
		});
		
		this.setLayout(grid);
		
		this.setJMenuBar(this.menuBar);
		this.add(this.paintPanel);

		this.pack();
		// this.setVisible(true);
	}
}
