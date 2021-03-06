package algo.actions;

import java.util.ArrayList;

import algo.Direction;
import environment.Cell;
import environment.Entrepot;

/** return the neighborhood of a node n, can hit an obstacle */
public class NeighborhoudWithObstacleBB8 implements ComputeNeighborhood {

	@Override
	public ArrayList<Cell> getNeighbors(Cell n, Entrepot ent, int vitesse, Direction direction) {
		ArrayList<Cell> r = new ArrayList<Cell>();
		int x = n.getX();
		int y = n.getY();
		if (vitesse == 1) {
			// vitesse de 1 : pas d'inertie : toutes cases autour dispos
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i == 0 && j == 0) {
						continue;
					}

					int xi = x + i;
					int yj = y + j;
					if (xi >= 0 && xi < ent.getWidth() && yj >= 0 && yj < ent.getHeight()) {
						Cell v = ent.getCell(xi, yj);
						r.add(v);
					}
				}
			}
		} else {
			// vitesse de 2 : inertie
			for (int i = -vitesse; i <= vitesse; i++) {
				for (int j = -vitesse; j <= vitesse; j++) {
					if (i == 0 && j == 0) {
						continue;
					}

					int xi = x + i;
					int yj = y + j;
					if (xi >= 0 && xi < ent.getWidth() && yj >= 0 && yj < ent.getHeight()) {
						Cell v = ent.getCell(xi, yj);
						Direction directionVersVoisinV = Direction.getDirection(n, v);
						// meme direction de 1 ou 2 cases
						if (directionVersVoisinV == direction ||
								// si autour d'une case a gauche ou a droite
								direction.isAcceptableDirection(directionVersVoisinV)) {
							r.add(v);
						}
					}
				}
			}
		}

		return r;
	}

}
