package algo.actions;

import java.util.ArrayList;

import algo.Direction;
import environment.Cell;
import environment.Entrepot;

public interface ComputeNeighborhood {
	/** return the neighborhood of a node n, can go inside an obstacle */
	ArrayList<Cell> getNeighbors(Cell n, Entrepot ent, int vitesse, Direction direction);
}
