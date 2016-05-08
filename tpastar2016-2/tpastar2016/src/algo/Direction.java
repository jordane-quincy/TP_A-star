package algo;

import environment.Cell;
import environment.Entrepot;

public enum Direction {
	N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);

	public static Direction getDirection(Entrepot ent, Cell depart) {
		if (depart == null) {
			return null;
		}
		Cell parent = depart.getParent();
		if (parent == null) {
			return null;
		}
		return getDirection(ent, depart.getX(), depart.getY(), parent.getX(), parent.getY());
	}

	public static Direction getDirection(Entrepot ent, int xDep, int yDep, int xFin, int yFin) {

		Cell depart = ent.getCell(xDep, yDep);
		Cell dest = ent.getCell(xFin, yFin);

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

		return dir;
	}

	private int value;

	private Direction(int value) {
		this.value = value;
	}

	public boolean isAcceptableDirection(Direction directionToCheck) {
		if (directionToCheck == null) {
			return false;
		}
		boolean isAcceptable = false;
		int deg = this.value;
		int degToCheck = directionToCheck.value;

		int diffDeg = Math.abs(deg - degToCheck);
		// 45 degres a gauche ou a droite dans un sens ou dans l'autre
		if (diffDeg <= 45 || diffDeg >= 315) {
			isAcceptable = true;
		}

		return isAcceptable;
	}
}
