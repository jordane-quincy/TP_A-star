package environment;

import java.io.Serializable;


/** a Cell has a position and is either a container, or a robot or is empty, can be a start or a goal or being visited <br>
 * a Cell owns its f, g and h values and a link to its parent */
@SuppressWarnings("serial")
public class Cell implements Serializable, Comparable<Cell>{
	/**Dock where is located the cell*/
	static Entrepot dock;
	/** nb of Cell instances*/
	static int nb = 0;
	/** no of the Cell*/
	int no = 0;
	/** the Cell contain a container or not*/
	boolean container;
	/** cell chosen by the robot*/
	boolean visited = false;
	/** start cell*/
	boolean start = false;
	/** goal cell*/
	boolean goal = false;
	/** evaluation of the distance from start to goal, through this cell*/
	int f;
	/** evaluation of the distance from start to this cell*/
	int g;
	/** evaluation of the distance from this cell to the goal */
	int h;
	/** coordinates*/
	int x,y;
	/** type cell*/
	TypeCell type;
	/**value of the cell -> difficulty to go on the cell*/
	double value;

	/**parent of the node*/
	Cell parent = null;
	
	

	Cell()
	{
		no = ++nb;
		type = TypeCell.HERBE;
		value = TypeCell.HERBE.getValue();
	}

	Cell(int i, int j)
	{
		this();
		x = i; y = j;
	}
	
	public String toString()
	{
		String r = "("+x+", "+y+")";
		return r;
	}

	//////// getters and setters
	public boolean isContainer() {
		return container;
	}

	public void setContainer(boolean container) {
		this.container = container;
		if(container)
		{
			this.type = TypeCell.OBSTACLE;
			this.value = TypeCell.OBSTACLE.getValue();
		}
	}
	
	

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public static int getNb() {
		return nb;
	}

	public int getNo() {
		return no;
	}


	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isGoal() {
		return goal;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public TypeCell getType() {
		return type;
	}

	public void setType(TypeCell type) {
		this.type = type;
		this.value = type.getValue();
	}


	public Cell getParent() {
		return parent;
	}

	public void setParent(Cell parent) {
		this.parent = parent;
	}
	//////// end of getters and setters

	/**reset f, g, and h to 0*/
	public void reset()
	{
		f = g = h = 0;		
	}

	/**compare the Cell, on base of F*/
	public int compareTo(Cell o) {
		int r = (f - o.getF());
		return r;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the dock
	 */
	public static Entrepot getDock() {
		return dock;
	} 

	/**
	 * @param dock the dock to set
	 */
	public static void setDock(Entrepot dock) {
		Cell.dock = dock;
	}
}
