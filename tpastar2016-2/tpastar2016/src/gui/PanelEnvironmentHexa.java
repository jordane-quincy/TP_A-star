package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JPanel;

import environment.Cell;
import environment.Entrepot;
import environment.TypeCell;

/**
 * panel on which the environment is displayed with HEXAGONAL Cells<br>
 * user can change the position of the goal by a mouse click
 * @author emmanuel adam*/
@SuppressWarnings("serial")
public class PanelEnvironmentHexa extends JPanel{
	/** entrepot (environment) linked to this gui*/
	Entrepot ent;

	/** width of a cell in pixel*/
	int depX;
	/** height of a cell in pixel*/
	int depY;
	
	/** define the panel and the mouse behaviour*/
	PanelEnvironmentHexa(int _width, int _height, Entrepot _ent) {
		setSize(_width, _height);
		ent = _ent;
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) {
				int i = (3*e.getX())/(2*depX);
				int j = e.getY()/depY;
				System.err.println("clic on i=" + i + ", j=" + j);
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

//////////////////////////////////////////////////shape1
		
		RenderingHints qualityHints = new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(qualityHints);

		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		Cell[][] content = ent.getContent();
		
		//compute the size of a cell in pixels
		depX = (int) (((double) getWidth()) / ((double) ent.getWidth()));
		depY = (int) (((double) getHeight()) / ((double) ent.getHeight()));

//		 create a hexagone
		GeneralPath p = new GeneralPath(GeneralPath.WIND_NON_ZERO);
		p.moveTo(- depX/2, 0);
		p.lineTo(- depX/6, depY/2);
		p.lineTo( depX/6, depY/2);
		p.lineTo( depX/2, 0);
		p.lineTo( depX/6, -depY/2);
		p.lineTo(- depX/6, -depY/2);
		p.closePath();
//		 translate origin towards center of canvas


		
		for (int i = 0; i < ent.getWidth(); i++)
			for (int j = 0; j < ent.getHeight(); j++) {
				if (content[i][j].getType() == TypeCell.HERBE) {
					g2d.setColor(Color.GREEN);
					int posX = (i * depX * 2) / 3  + depX/2;
					int posY = j * depY + depY/2 * (i%2);
					g2d.translate(posX,  posY);
					g2d.fill(p);
					g2d.translate(-posX,  -posY);
					}
				if (content[i][j].getType() == TypeCell.SABLE) {
					g2d.setColor(Color.YELLOW);
					g2d.fillRect(i * depX , j * depY , depX , depY ); }
				if (content[i][j].getType() == TypeCell.EAU) {
					g2d.setColor(Color.BLUE);
					g2d.fillRect(i * depX , j * depY , depX , depY ); }
				if (content[i][j].isContainer()) {
					g2d.setColor(Color.BLACK);
					int posX = (i * depX * 2) / 3  + depX/2;
					int posY = j * depY + depY/2 * (i%2);
					g2d.translate(posX,  posY);
					g2d.fill(p);
					g2d.translate(-posX,  -posY);
				}
				else if (content[i][j].isStart()) {
					g2d.setColor(Color.WHITE);
					int posX = (i * depX * 2) / 3  + depX/2;
					int posY = j * depY + depY/2 * (i%2);
					g2d.translate(posX,  posY);
					g2d.fill(p);
					g2d.translate(-posX,  -posY);
//					g2d.fillOval(i * depX, j * depY, depX, depY);
				}
				else if (content[i][j].isGoal()) {
					g2d.setColor(Color.YELLOW);
					int posX = (i * depX * 2) / 3  + depX/2;
					int posY = j * depY + depY/2 * (i%2);
					g2d.translate(posX,  posY);
					g2d.fill(p);
					g2d.translate(-posX,  -posY);
//					g2d.fillOval(i * depX, j * depY, depX, depY);
				}
//				else if (content[i][j].isVisited()) {
//					g2d.setColor(Color.RED);
//					g2d.fillRect(i * depX + 1, j * depY + 1, depX - 2, depY - 2);
//				}
				else if (content[i][j].getF()>0) {// if the cell has been evaluated
					g2d.setColor(Color.GRAY);
					int posX = (i * depX * 2) / 3  + depX/2;
					int posY = j * depY + depY/2 * (i%2);
					g2d.translate(posX,  posY);
					g2d.fill(p);
					g2d.translate(-posX,  -posY);
//					g2d.fillRect(i * depX + 2, j * depY + 2, depX - 4, depY - 4);
				}
					
			}
		ArrayList<Cell> solution = ent.getSolution();
		if(solution!=null)
		{
			g2d.setColor(Color.RED);
			int smaller = (depX<depY?depX:depY);
			smaller = smaller / 2;
			if (smaller<=1) smaller = 2;
			BasicStroke penSize = new BasicStroke(smaller-1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10f);
			
			g2d.setStroke(penSize);
			
			Cell c0 = ent.getStart();
			for(Cell c1:solution)
			{
				int posX0 = (c0.getX() * depX * 2) / 3  + depX/2;
				int posY0 = c0.getY()  * depY + depY/2 * (c0.getX()%2);

				int posX1 = (c1.getX() * depX * 2) / 3  + depX/2;
				int posY1 = c1.getY()  * depY + depY/2 * (c1.getX()%2);

				g2d.drawLine(posX0, posY0, posX1, posY1);
				c0 = c1;
			}
		}

	}

}
