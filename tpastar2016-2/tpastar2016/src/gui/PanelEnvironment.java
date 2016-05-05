package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import environment.Cell;
import environment.Entrepot;
import environment.TypeCell;

/**
 * panel on which the environment is displayed<br>
 * user can change the position of the goal by a mouse click
 * @author emmanuel adam*/
@SuppressWarnings("serial")
public class PanelEnvironment extends JPanel{
	/** entrepot (environment) linked to this gui*/
	Entrepot ent;

	/** width of a cell in pixel*/
	int depX;
	/** height of a cell in pixel*/
	int depY;
	
	/** define the panel and the mouse behaviour*/
	PanelEnvironment(int _width, int _height, Entrepot _ent) {
		setSize(_width, _height);
		this.setToolTipText("click to change the goal");
		ent = _ent;
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) {
				int i = e.getX()/depX;
				int j = e.getY()/depY;
				ent.setGoal(i, j);
				ent.removeVisit();
				ent.recompute();
			}
		});
	}

	/** paint the environment*/
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// to have a good rendering

		RenderingHints qualityHints = new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(qualityHints);

		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		Cell[][] content = ent.getContent();
		
		//compute the size of a cell in pixels
		depX = (int) (((double) getWidth()) / ((double) ent.getWidth()));
		depY = (int) (((double) getHeight()) / ((double) ent.getHeight()));
		
		double coefShadowContainer = TypeCell.OBSTACLE.getValue() / 4;
		
		for (int i = 0; i < ent.getWidth(); i++)
			for (int j = 0; j < ent.getHeight(); j++) {
				if (content[i][j].getType() == TypeCell.HERBE) {
					int diff = (int)(coefShadowContainer*(content[i][j].getValue() - TypeCell.HERBE.getValue()));
					Color green = adjustLuminosity(Color.GREEN, -diff);
					g2d.setColor(green);
					g2d.fillRect(i * depX , j * depY , depX , depY ); }
				if (content[i][j].getType() == TypeCell.SABLE) {
					int diff = (int)(coefShadowContainer*(content[i][j].getValue() - TypeCell.SABLE.getValue()));
					Color yellow = adjustLuminosity(Color.YELLOW, -diff);
					g2d.setColor(yellow);
					g2d.fillRect(i * depX , j * depY , depX , depY ); }
				if (content[i][j].getType() == TypeCell.EAU) {
					int diff = (int)(coefShadowContainer*(content[i][j].getValue() - TypeCell.EAU.getValue()));
					Color blue = adjustLuminosity(Color.BLUE, -diff);
					g2d.setColor(blue);
					g2d.fillRect(i * depX , j * depY , depX , depY ); }
				if (content[i][j].isContainer()) {
					g2d.setColor(Color.BLACK);
					g2d.fillOval(i * depX, j * depY, depX, depY);
				}
				else if (content[i][j].isStart()) {
					g2d.setColor(Color.WHITE);
					g2d.fillRect(i * depX , j * depY , depX , depY ); 
//					g2d.fillOval(i * depX, j * depY, depX, depY);
				}
				else if (content[i][j].isGoal()) {
					g2d.setColor(Color.RED);
					g2d.fillRect(i * depX , j * depY , depX , depY ); 
//					g2d.fillOval(i * depX, j * depY, depX, depY);
				}
//				else if (content[i][j].isVisited()) {
//					g2d.setColor(Color.RED);
//					g2d.fillRect(i * depX + 1, j * depY + 1, depX - 2, depY - 2);
//				}
				 if (content[i][j].getF()>0) {// if the cell has been evaluated
					Color c = adjustLuminosity(g2d.getColor(), 150);//.brighter().brighter().brighter();
//					int r = (c.getRed()+150);
//					r = (r>255?255:r);
//					int v = (c.getGreen()+150);
//					v = (v>255?255:v);
//					int b = (c.getBlue()+150);
//					b = (b>255?255:b);
//					c = new Color(r,v,b );
					g2d.setColor(c);
					g2d.fillRect(i * depX , j * depY , depX , depY ); 
//					g2d.fillRect(i * depX + 2, j * depY + 2, depX - 4, depY - 4);
				}
					
			}
		ArrayList<Cell> solution = ent.getSolution();
		if(solution!=null)
		{
			//g2d.setColor(Color.RED);
			int smaller = (depX<depY?depX:depY);
			BasicStroke penSize = new BasicStroke(smaller-1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10f);
			
			g2d.setStroke(penSize);
			
			Cell c0 = ent.getStart();
			for(Cell c1:solution)
			{
				//On met en bleu les murs qu'on traverse
				if (c1.isContainer()) {
					g2d.setColor(Color.BLUE);
				}
				else {
					g2d.setColor(Color.RED);
				}
				g2d.drawLine(c0.getX() * depX + depX/2, c0.getY() * depY + depY/2, c1.getX() * depX + depX/2, c1.getY()  * depY + depY/2);
				c0 = c1;
			}
		}
	}
	
	/**compute a new color, lighter or lighter than the original
	 * @param col original color
	 * @param adjustment degree of darkness (if negative) or lightness (if positive)
	 * @return a new color lighter of darker than col*/
	private Color adjustLuminosity(Color col, int adjustment)
	{
		Color c = null;
		int r = (col.getRed()+adjustment);
		r = (r>255?255:r);
		r = (r<0?0:r);
		int v = (col.getGreen()+adjustment);
		v = (v>255?255:v);
		v = (v<0?0:v);
		int b = (col.getBlue()+adjustment);
		b = (b>255?255:b);
		b = (b<0?0:b);
		c = new Color(r,v,b );
		return c;		
	}

}
