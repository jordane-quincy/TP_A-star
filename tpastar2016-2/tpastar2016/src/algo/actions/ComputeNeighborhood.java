package algo.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import algo.Direction;
import environment.Cell;
import environment.Entrepot;

public interface ComputeNeighborhood {
	static boolean containPosition(Map<Integer, Integer> coordNeighbordPossible, int i, int j) {
		Integer value = coordNeighbordPossible.get(i);
		return value != null && value == j;
	}

	static Map<Integer, Integer> isAccessible(Cell n, int i, int j, int vitesse) {
		if (n == null || n.getParent() == null) {
			return null;
		}
		Map<Integer, Integer> coordNeighbordPossible = new HashMap<Integer, Integer>();
		Cell p = n.getParent();

		int xDec = n.getX() - p.getX();
		int yDec = n.getY() - p.getY();

		if (Math.abs(xDec) == 1 && yDec == 0) {
			// deplacement horizontal
			coordNeighbordPossible.put(-xDec, 0);
			coordNeighbordPossible.put(-xDec, -1);
			coordNeighbordPossible.put(-xDec, 1);
		} else if (Math.abs(yDec) == 1 && xDec == 0) {
			// deplacement vertical
			coordNeighbordPossible.put(0, -yDec);
			coordNeighbordPossible.put(-1, -yDec);
			coordNeighbordPossible.put(1, -yDec);
		} else {
			// deplacement diagonale
			coordNeighbordPossible.put(-xDec, 0);
			coordNeighbordPossible.put(0, -yDec);
			coordNeighbordPossible.put(-xDec, -yDec);
		}

		System.out.println(" isAccessible : " + n + ", i=" + i + ", j=" + j + ", vitesse=" + vitesse + "coord :"
				+ coordNeighbordPossible);

		return coordNeighbordPossible;
	}

	/** return the neighborhood of a node n, can go inside an obstacle */
	ArrayList<Cell> getNeighbors(Cell n, Entrepot ent, int vitesse, Direction direction);
}
