package environment;

import java.util.ArrayList;

import algo.AlgoAStar;
import algo.TypeDistance;
import algo.actions.TypeNeighborhood;

/** entrepot : rectangle of cells; some cells contains container*/
public class Entrepot {

	/** width of the environment*/
	int width = 30;
	/** height of the environment*/
	int height = 30;
	/** table of cells*/
	Cell[][] content;
	/** density of containers*/
	double density = 0;
	/** initial Cell*/
	Cell start;
	/** goal Cell*/
	Cell goal;
	
	/**solution found by atar*/
	ArrayList<Cell>  solution;
	

	/** A* algo linked to this environment*/
	public AlgoAStar algo;
	
	
	/**true if the neighbouring of a container should be avoided*/
	boolean poisonNeighbourg;
	
	/**coef of repulsion near a container*/
	int coefPoisonNeightbourg;
	
	/**create empty environment*/
	Entrepot()
	{
		content = new Cell[width][height];
		poisonNeighbourg = true;
		coefPoisonNeightbourg  = 20;
		init();
	}
	
	/**create empty environment*/
	public Entrepot(int _width, int _height)
	{
		width = _width;
		height = _height;
		poisonNeighbourg = true;
		content = new Cell[width][height];
		coefPoisonNeightbourg  = 20;
		init();
	}
	
	/**create an environment filled with container <br>
	 * set up the initial state and the goal state
	 * @param _density density of cells that own a container*/
	public Entrepot(int _width, int _height, double _density)
	{
		this(_width, _height);
		density = _density;
		fill();
		start = content[width/10][height/10];
		start.setStart(true);
		start.setContainer(false);
		goal = content[width-width/10][height-height/10];
		goal.setGoal(true);
		goal.setContainer(false);
		System.err.println("goal is in "+goal.getX() + "," + goal.getY());
	}

	
	/**create an environment filled with container <br>
	 * set up the initial state and the goal state
	 * @param _density density of cells that own a container
	 * @param _algo A* algo linked to this environment*/
	public Entrepot(int _width, int _height, double _density, AlgoAStar _algo)
	{
		this(_width, _height, _density);
		algo = _algo;
	}

	/** initialise the cells*/
	void init()
	{
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				content[i][j] = new Cell(i,j);
		Cell.setDock(this);

		colorLandscape();
	}
	
	/**define 1 zone for all the landscape*/
	public void unifyLandscape()
	{
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				content[i][j].setType(TypeCell.HERBE);
	}
	
	/**define 3 zones in the landscape*/
	public void colorLandscape()
	{
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				if(i<width/3) content[i][j].setType(TypeCell.HERBE);
				else
				if(i>=(width/3) && (i<(2*width)/3)) content[i][j].setType(TypeCell.SABLE);
				else
				if (i>=(2*width)/3) content[i][j].setType(TypeCell.EAU);
	}
	
	
	/** set some cells as container<br>
	 * the neighbouring of a container is not attractive --> */
	void fill()
	{
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				if (Math.random() <= density) 
					content[i][j].setContainer(true);
		if(poisonNeighbourg) diffusePoison();
	}
	
	/**a container has a negative impact on its neighbouring : cells arrouding the container increase their values*/
	void poisonNeighbouring(int x, int y)
	{
		for(int i=-1; i<=1; i++)
			for(int j=-1; j<=1; j++)
			{
				if(x+i<0 || x+i>=width || y+j<0 || y+j >= height || (x+y == 0)) continue;
				if(!content[x+i][y+j].isContainer())
					content[x+i][y+j].setValue(content[x+i][y+j].getValue() +TypeCell.OBSTACLE.getValue()/(double)coefPoisonNeightbourg );
			}
	}

	/** diffuse the repulsive aspect for all containers*/
	public void diffusePoison()
	{
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				if (content[i][j].isContainer())  poisonNeighbouring(i,j);		
	}
	
	/**restore the value of the cells, without containers repulsion*/
	public 	void restoreArea()
	{
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				content[i][j].setValue(content[i][j].getType().getValue());
	}
	
	/** return the cell int x,y*/
	public Cell getCell(int x, int y) {
		return content[x][y];
	}

	/** change the goal*/
	public void setGoal(int i, int j) {
		goal.setGoal(false);
		goal = content[i][j];
		goal.setGoal(true);
	}

	/** clear the previous visits */
	public void removeVisit()
	{
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
			{
				content[i][j].setVisited(false);
				content[i][j].setF(0);
				content[i][j].setG(0);
				content[i][j].setH(0);
			}
	}
	
	/**launch the recomputation of the best path*/
	public void recompute()
	{
		algo.reCompute();		
	}

	
	public void setMethod(TypeDistance method)
	{
		algo.setTypeDistance(method);
	}
	
	public void setMethodNeighbouring(TypeNeighborhood _typeNeighbouring)
	{
		algo.setTypeneighborhood(_typeNeighbouring);
	}
	
	////// getters and setters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public Cell[][] getContent() {
		return content;
	}

	public Cell getStart() {
		return start;
	}

	public void setStart(Cell start) {
		this.start = start;
	}

	public Cell getGoal() {
		return goal;
	}

	public void setGoal(Cell goal) {
		this.goal = goal;
	}
	
	public void setSolution(ArrayList<Cell> solution) {
		this.solution = solution;
	}

	public ArrayList<Cell> getSolution() {
		return solution;
	}

	/**
	 * @return the coefPoisonNeightbourg
	 */
	public int getCoefPoisonNeightbourg() {
		return coefPoisonNeightbourg;
	}

	/**
	 * @param coefPoisonNeightbourg the coefPoisonNeightbourg to set
	 */
	public void setCoefPoisonNeightbourg(int coefPoisonNeightbourg) {
		this.coefPoisonNeightbourg = coefPoisonNeightbourg;
	}

	////// end of getters and setters

}
