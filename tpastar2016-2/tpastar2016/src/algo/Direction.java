package algo;

import environment.Cell;

public enum Direction {
	N(0), NNE(22.5), NE(45), ENE(77.5), E(90), ESE(112.5), SE(135), SSE(157.5), S(180), SSW(202.5), SW(225), WSW(
			247.5), W(270), WNW(292.5), NW(315), NNW(337.5);

	public static Direction getDirection(Cell depart, Cell dest) {
		if (depart == null || dest == null) {
			return null;
		}

		Direction dir = null;

		int diffX = dest.getX() - depart.getX();
		int diffY = dest.getY() - depart.getY();

		if (diffX == -2) {
			switch (diffY) {
			case -2:
				dir = NW;
				break;
			case -1:
				dir = WNW;
				break;
			case 0:
				dir = W;
				break;
			case 1:
				dir = WSW;
				break;
			case 2:
				dir = SW;
				break;
			}
		} else if (diffX == -1) {
			switch (diffY) {
			case -2:
				dir = NNW;
				break;
			case -1:
				dir = NW;
				break;
			case 0:
				dir = W;
				break;
			case 1:
				dir = SW;
				break;
			case 2:
				dir = SSW;
				break;
			}
		} else if (diffX == 0) {
			switch (diffY) {
			case -2:
			case -1:
				dir = N;
				break;
			case 0:
				dir = null;
				break;
			case 1:
			case 2:
				dir = S;
				break;
			}
		} else if (diffX == 1) {
			switch (diffY) {
			case -2:
				dir = NNE;
				break;
			case -1:
				dir = NE;
				break;
			case 0:
				dir = E;
				break;
			case 1:
				dir = SE;
				break;
			case 2:
				dir = SSE;
				break;
			}
		} else if (diffX == 2) {
			switch (diffY) {
			case -2:
				dir = NE;
				break;
			case -1:
				dir = ENE;
				break;
			case 0:
				dir = E;
				break;
			case 1:
				dir = ESE;
				break;
			case 2:
				dir = SE;
				break;
			}
		}

		return dir;
	}

	private double value;

	private Direction(double value) {
		this.value = value;
	}

	protected double getValue() {
		return this.value;
	}

	public boolean isAcceptableDirection(Direction directionToCheck) {
		if (directionToCheck == null) {
			return false;
		}
		boolean isAcceptable = false;
		double deg = this.value;
		double degToCheck = directionToCheck.value;

		int diffDeg = (int) Math.floor(Math.abs(deg - degToCheck));
		// arrondi de 360/16 et de 360 - 360/16
		// if (diffDeg <= 23 || diffDeg >= 337) {
		if (diffDeg < 45 || diffDeg > 315) {
			isAcceptable = true;
		}

		return isAcceptable;
	}
}
