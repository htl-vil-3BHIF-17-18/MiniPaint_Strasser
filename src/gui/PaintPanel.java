package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {
	private static final long serialVersionUID = -7060887915588269337L;
	private Color drawColor = null;
	private Color backgroundColor = null;
	private boolean straight = false;
	private boolean rectangle = false;
	private boolean oval = false;
	private boolean filled = false;
	private PaintPanel paintPanel= this;
	private BufferedImage paintImage = null;
	private int xCoordinate = -1;
	private int yCoordinate = -1;
	private Timer recalculateTimer = new Timer(500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			BufferedImage timage = deepCopyImage(paintImage);
			createImage(getWidth() <= 0 ? 1 : getWidth(), getHeight() <= 0 ? 1 : getHeight(), getBackground());
			paintImage.createGraphics().drawImage(timage, 0, 0, null);
			updateImage();
			timage = null;
		}
	});

	public PaintPanel(int width, int height, Color drawColor, Color backgroundColor) {
		this.drawColor = drawColor;
		this.backgroundColor = backgroundColor;

		this.paintPanel.setBackground(backgroundColor);
		
		this.paintPanel.createImage(width, height, backgroundColor);
		
		this.paintPanel.addMouseListener(this);
		this.paintPanel.addMouseMotionListener(this);
		
		this.paintPanel.addComponentListener(this);
	}

	public Color getDrawColor() {
		return this.drawColor;
	}

	public void setColor(Color drawColor) {
		this.drawColor = drawColor;
	}
	
	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public boolean isStraight() {
		return straight;
	}

	public void setStraight(boolean straight) {
		this.straight = straight;
		this.rectangle = false;
		this.oval = false;
	}

	public boolean isRectangle() {
		return rectangle;
	}

	public void setRectangle(boolean rectangle) {
		this.straight = false;
		this.rectangle = rectangle;
		this.oval = false;
	}

	public boolean isOval() {
		return oval;
	}
	
	public void setOval(boolean oval) {
		this.straight = false;
		this.rectangle = false;
		this.oval = oval;
	}

	public boolean isFilled() {
		return this.filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public PaintPanel getPaintPanel() {
		return this.paintPanel;
	}
	
	public void setPaintPanel(PaintPanel paintPanel) {
		this.paintPanel = paintPanel;
	}
	
	public BufferedImage getImage() {
		return this.paintImage;
	}

	public void setImage(BufferedImage image) {
		this.paintImage = image;
	}
	
	public void updateImage() {
		Graphics2D graphics = (Graphics2D) this.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.drawImage(this.paintImage, 0, 0, null);
	}

	private void createImage(int width, int height, Color background) {
		this.paintImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = paintImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setPaint(background);
		graphics.fillRect(0, 0, paintImage.getWidth(), paintImage.getHeight());
	}

	private BufferedImage deepCopyImage(BufferedImage image) {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (this.straight || this.rectangle || this.oval) {
			this.xCoordinate = e.getX();
			this.yCoordinate = e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Graphics2D g = (Graphics2D) this.getGraphics();
		Graphics2D ig = (Graphics2D) this.paintImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(this.drawColor);
		ig.setColor(this.drawColor);
		int pos[] = new int[4];

		if (this.straight) {
			g.drawLine(xCoordinate, yCoordinate, e.getX(), e.getY());
			ig.drawLine(xCoordinate, yCoordinate, e.getX(), e.getY());
		} else if (this.rectangle) {
			pos = checkPosAndCorrect(xCoordinate, yCoordinate, e.getX(), e.getY());
			if (filled) {
				g.fillRect(pos[0], pos[1], difference(pos[2], pos[0]), difference(pos[3], pos[1]));
				ig.fillRect(pos[0], pos[1], difference(pos[2], pos[0]), difference(pos[3], pos[1]));
			} else {
				g.drawRect(pos[0], pos[1], difference(pos[2], pos[0]), difference(pos[3], pos[1]));
				ig.drawRect(pos[0], pos[1], difference(pos[2], pos[0]), difference(pos[3], pos[1]));
			}
		} else if (this.oval) {
			pos = checkPosAndCorrect(xCoordinate, yCoordinate, e.getX(), e.getY());
			if (filled) {
				g.fillOval(pos[0], pos[1], difference(pos[2], pos[0]), difference(pos[3], pos[1]));
				ig.fillOval(pos[0], pos[1], difference(pos[2], pos[0]), difference(pos[3], pos[1]));
			} else {
				g.drawOval(pos[0], pos[1], difference(pos[2], pos[0]), difference(pos[3], pos[1]));
				ig.drawOval(pos[0], pos[1], difference(pos[2], pos[0]), difference(pos[3], pos[1]));
			}
		}

		this.xCoordinate = -1;
		this.yCoordinate = -1;
	}

	public int difference(int num1, int num2) {
		return num1 > num2 ? num1 - num2 : num2 - num1;
	}

	private int[] checkPosAndCorrect(int x1, int y1, int x2, int y2) {
		int result[] = new int[4];
		int tx1 = x1;
		int ty1 = y1;
		int tx2 = x2;
		int ty2 = y2;

		if (x1 < x2 && y1 < y2) {
			// passt schon
		} else if (x1 > x2 && y1 < y2) {
			x1 = tx2;
			y1 = ty1;
			x2 = tx1;
			y2 = ty2;
		} else if (x1 > x2 && y1 > y2) {
			x1 = tx2;
			y1 = ty2;
			x2 = tx1;
			y2 = ty1;
		} else if (x1 < x2 && y1 > y2) {
			x1 = tx1;
			y1 = ty2;
			x2 = tx2;
			y2 = ty1;
		} else {
			// ???
		}

		result[0] = x1;
		result[1] = y1;
		result[2] = x2;
		result[3] = y2;

		return result;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Graphics2D g = (Graphics2D) this.getGraphics();
		Graphics2D ig = (Graphics2D) this.paintImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(this.drawColor);
		ig.setColor(this.drawColor);
		if (!this.straight && !this.rectangle && !this.oval) {
			if (this.xCoordinate != -1 && this.yCoordinate != -1) {
				g.drawLine(xCoordinate, yCoordinate, e.getX(), e.getY());
				ig.drawLine(xCoordinate, yCoordinate, e.getX(), e.getY());
			}
			this.xCoordinate = e.getX();
			this.yCoordinate = e.getY();
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	
	/*
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generatsetRectangleed method stub
				BufferedImage timage = deepCopyImage(paintImage);
				createImage(getWidth() <= 0 ? 1 : getWidth(), getHeight() <= 0 ? 1 : getHeight(), getBackground());
				paintImage.createGraphics().drawImage(timage, 0, 0, null);
				updateImage();
				timage = null;
			}
		});
	}
	*/
	
	public void componentResized(ComponentEvent e) {
		
		if(recalculateTimer.isRunning()) {
			recalculateTimer.restart();
		} else {
			recalculateTimer.start();
		}
	}
	
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
	}
}
