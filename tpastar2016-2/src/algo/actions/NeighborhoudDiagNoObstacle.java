package algo.actions;

import java.util.ArrayList;

import environment.Cell;
import environment.Entrepot;

/** return the neighborhood of a node n, can't hit an obstacle*/
public class NeighborhoudDiagNoObstacle implements ComputeNeighborhood {

	public ArrayList<Cell> getNeighbors(Cell n, Entrepot ent) {
		ArrayList<Cell> r = new ArrayList<Cell>();
		int width = ent.getWidth() ;
		int height = ent.getHeight();		
		int x = n.getX();
		int y = n.getY();		
		for(int i=-1; i<2; i++)
			for(int j=-1; j<2; j++)
			{
				int xi = x+i; 
				int yj = y+j;
				if (xi>=0 && xi<width && yj>=0 && yj<height)
				{
					Cell v = ent.getCell(xi,yj);
					if(i!=0 && j!=0 )
					{
						if (!ent.getCell(xi,y).isContainer() && !ent.getCell(x,yj).isContainer())
							r.add(v);
					}
					else r.add(v);						
				}
			}
		return r;
		}

}
