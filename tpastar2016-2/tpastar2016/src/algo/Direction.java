package algo;

import environment.Cell;

public enum Direction {
	N, S, E, W, //
	NE, SE, NW, SW;

	// public static Direction getDirection(Entrepot ent, int xDep,int yDep,int
	// xFin,int yFin){
	// Cell depart = ent.getCell(xDep, yDep);
	// Cell fin = ent.getCell(xFin, yFin);
	//
	// }
	public static Direction getDirection(Cell depart) {
		Cell dest = depart.getParent();
		if (dest == null) {
			return null;
		}
		Direction dir = null;

		int diffX = depart.getX() - dest.getX();
		int diffY = depart.getY() - dest.getY();

		if (diffY <= -1) {
			dir = N;
			if (diffX <= -1) {
				dir = NE;
			} else if (diffX >= 1) {
				dir = NW;
			}
		} else if (diffY >= 1) {
			dir = S;
			if (diffX <= -1) {
				dir = SE;
			} else if (diffX >= 1) {
				dir = SW;
			}
		} else if (diffY == 0) {
			if (diffX <= -1) {
				dir = W;
			} else if (diffX >= 1) {
				dir = E;
			}
		}

		System.out.println("Direction " + dir + " (" + diffX + "," + diffY + ") ");

		return dir;
	}
}
