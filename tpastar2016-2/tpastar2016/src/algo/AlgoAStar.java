package algo;

import java.util.ArrayList;
import java.util.Collections;

import algo.actions.TypeNeighborhood;
import environment.Cell;
import environment.Entrepot;
import gui.EnvironmentGui;

/** this class propose an implementation of the a star algorithm */
public class AlgoAStar {

	public static void main(String[] args) {
		new AlgoAStar(200, 100, 20);
	}

	/** nodes to be evaluated */
	ArrayList<Cell> freeNodes;

	/** evaluated nodes */
	ArrayList<Cell> closedNodes;
	/** Start Cell */
	Cell start;

	/** Goal Cell */
	Cell goal;

	/** distance method */
	TypeDistance typeDistance;

	/** type of method to build neighborhood of a node */
	TypeNeighborhood typeNeighborhood;
	/** graphe / map of the nodes */
	Entrepot ent;

	/** gui */
	EnvironmentGui gui;

	/**
	 * initialize the environment (100 x 100 with a density of container +- 20%)
	 */
	AlgoAStar() {
		ent = new Entrepot(100, 100, 0.2, this);
		gui = new EnvironmentGui(ent);
		typeDistance = TypeDistance.Manhattan;
		typeNeighborhood = TypeNeighborhood.avoidHurtObstacle;
		reCompute();
	}

	/**
	 * initialize the environment
	 *
	 * @param width
	 *            width of the environment
	 * @param height
	 *            height of the environment
	 * @param density
	 *            percent of bloc in the environment
	 */
	AlgoAStar(int width, int height, double density) {
		ent = new Entrepot(width, height, (density / 100.0), this);
		gui = new EnvironmentGui(ent);
		typeDistance = TypeDistance.Manhattan;
		typeNeighborhood = TypeNeighborhood.avoidHurtObstacle;
		reCompute();
	}

	/**
	 * a* algorithm to find the best path between two states
	 *
	 * @param _start
	 *            initial state
	 * @param _goal
	 *            final state
	 */
	ArrayList<Cell> algoASTAR(Cell _start, Cell _goal) {
		start = _start;
		goal = _goal;
		// list of visited nodes
		closedNodes = new ArrayList<Cell>();
		// list of nodes to evaluate
		freeNodes = new ArrayList<Cell>();
		freeNodes.add(start);
		// no cost to go from start to start
		// TODO: g(start) <- 0
		start.setG(0);
		// TODO: h(start) <- evaluation(start)
		start.setH(evaluation(start));
		// TODO: f(start) <- h(start)
		start.setF(start.getH());
		// while there is still a node to evaluate
		while (!freeNodes.isEmpty()) {
			// choose the node having a F minimal
			Cell n = chooseBestNode();
			// stop if the node is the goal
			if (isGoal(n)) {
				return rebuildPath(n);
			}
			// TODO: freeNodes <- freeNodes - {n}
			freeNodes.remove(n);
			// TODO: closedNodes <- closedNodes U {n}
			closedNodes.add(n);
			// construct the list of neighbourgs
			ArrayList<Cell> nextDoorNeighbours = neighboursAvoidObstacle(n); // neighbours(n);
			for (Cell ndn : nextDoorNeighbours) {
				// if the neighbour has been visited, do not reevaluate it
				if (closedNodes.contains(ndn)) {
					continue;
				}
				// cost to reach the neighbour is the cost to reach n + cost
				// from n to the neighbourg
				// TODO: int cost = ...
				int cost = n.getG() + costBetween(n, ndn);
				boolean bestCost = false;
				// if the neighbour has not been evaluated
				if (!freeNodes.contains(ndn)) {
					// TODO: freeNodes <- freeNodes U {ndn}
					freeNodes.add(ndn);
					// TODO: h(ndn) -> evaluation(ndn)
					ndn.setH(evaluation(ndn));
					bestCost = true;
				} else
				// if the neighbour has been evaluated to a more important cost,
				// change its evaluation
				if (cost < ndn.getG()) {
					bestCost = true;
				}
				if (bestCost) {
					ndn.setParent(n);
					// TODO : g(ndn) <- cost
					ndn.setG(cost);
					// TODO : f(ndn) <- g(ndn) + h(ndn)
					ndn.setF(ndn.getG() + ndn.getH());
				}
			}
		}
		return null;
	}

	/** return the node having the minimal f */
	Cell chooseBestNode() {
		Collections.sort(freeNodes);
		return freeNodes.get(0);
	}

	/**
	 * compute the distance recorded in the solution
	 *
	 * @param solution
	 *            list of points from start to goal
	 * @return the distance from start to goal
	 */
	private int computeDistance(ArrayList<Cell> solution) {
		int distance = 0;
		if (solution != null) {
			Cell end = solution.get(solution.size() - 1);
			if (solution.size() > 1) {
				for (int i = solution.size() - 2; i > 0; i--) {
					Cell parent = solution.get(i);
					distance += costBetweenWithoutNuisance(parent, end);
					end = parent;
				}
			}
		}
		return distance;
	}

	/**
	 * return the cost from n to c : 10 for a lateral move, 14
	 * (squareroot(2)*10) for a diagonal move
	 *
	 * @param n
	 *            a node/cell
	 * @param c
	 *            a node/cel close to n
	 * @return distance between the two adjacent nodes n and c
	 */
	int costBetween(Cell n, Cell c) {
		int difx = Math.abs(n.getX() - c.getX());
		int dify = Math.abs(n.getY() - c.getY());
		assert difx <= 1 && dify <= 1 : "pb in costBetween, n and c are not adjacent !! ";
		int retour = 10 * (difx + dify);
		if (retour >= 20) {
			retour = 14;
		}
		retour = (int) (retour * c.getValue());
		return retour;
	}

	/**
	 * return the cost from n to c : 10 for a longitudinal move, 14
	 * (squareroot(2)*10) for a diagonal move
	 *
	 * @param n
	 *            a node/cell
	 * @param c
	 *            a node/cel close to n
	 * @return distance between the two adjacent nodes n and c
	 */
	int costBetweenWithoutNuisance(Cell n, Cell c) {
		int difx = Math.abs(n.getX() - c.getX());
		int dify = Math.abs(n.getY() - c.getY());
		assert difx <= 1 && dify <= 1 : "pb in costBetween, n and c are not adjacent !! ";
		int retour = 10 * (difx + dify);
		if (retour >= 20) {
			retour = 14;
		}
		retour = (int) (retour * c.getType().getValue());
		return retour;
	}

	/** return the distance between the cells */
	double distance(Cell a, Cell b) {
		double retour = 0.0;
		retour = typeDistance.distance.compute(a, b);
		return retour;
	}

	/** return the estimation of the distance from c to the goal */
	int evaluation(Cell c) {
		int retour = 10 * (int) distance(c, goal);
		retour = (int) (retour * c.getValue());
		return retour;
	}

	/**
	 * @return the typeDistance
	 */
	public TypeDistance getTypeDistance() {
		return typeDistance;
	}

	/** return if n is a goal or not */
	boolean isGoal(Cell n) {
		return (n.getX() == goal.getX() && n.getY() == goal.getY());
	}

	/** return the neighborhood of a node n; a diagonal avoid the containers */
	ArrayList<Cell> neighboursAvoidObstacle(Cell n) {
		ArrayList<Cell> r = new ArrayList<Cell>();
		int width = ent.getWidth();
		int height = ent.getHeight();
		int x = n.getX();
		int y = n.getY();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int xi = x + i;
				int yj = y + j;
				if (xi >= 0 && xi < width && yj >= 0 && yj < height) {
					Cell v = ent.getCell(xi, yj);
					if (!v.isContainer()) {
						if ((i == -1 || i == 1) && (j == -1 || j == 1)) {
							if (!ent.getCell(xi, y).isContainer() && !ent.getCell(x, yj).isContainer()) {
								r.add(v);
							}
						} else {
							r.add(v);
						}
					}

				}
			}
		}
		return r;
	}

	/** return the path from start to the node n */
	ArrayList<Cell> rebuildPath(Cell n) {
		if (n.getParent() != null) {
			ArrayList<Cell> p = rebuildPath(n.getParent());
			n.setVisited(true);
			p.add(n);
			return p;
		} else {
			return (new ArrayList<Cell>());
		}
	}

	/** algo called to (re)launch a star algo */
	public void reCompute() {
		System.gc();
		ent.removeVisit();
		if (goal != null) {
			goal.reset();
		}
		long dateBefore = System.nanoTime();
		ArrayList<Cell> solution = algoASTAR(ent.getStart(), ent.getGoal());
		long elapsedTime = (System.nanoTime() - dateBefore) / 1000;
		gui.setFieldComputeTime("" + (elapsedTime / 1000.0));
		ent.setSolution(solution);
		if (solution == null) {
			System.out.println("solution IMPOSSIBLE");
		} else {
			int nbNoeudsClos = closedNodes.size();
			int nbNoeudsLibres = freeNodes.size();
			int distanceWithNuisance = goal.getF();
			gui.setFieldClosedNodes("" + nbNoeudsClos);
			gui.setFieldFreeNodes("" + nbNoeudsLibres);
			gui.setFieldDistanceWithNuisance("" + distanceWithNuisance);
			// System.err.println("solution found, goal.f=" + goal.getF() + ",
			// goal.g=" + goal.getG() + ", goal.h=" + goal.getH() );
			gui.setFieldDistance("" + computeDistance(solution));
		}
		gui.repaint();
		// gui.setSize(gui.getWidth(), gui.getHeight());
		// gui.pack();
	}

	/** algo called to (re)launch a star algo */
	public void reComputeAll() {
		TypeDistance[] tabMethods = TypeDistance.values();
		// , TypeDistance.Adam};
		int distanceMin = Integer.MAX_VALUE;
		int nbNoeudsClosMin = 0;
		int nbNoeudsLibresMin = 0;
		TypeDistance meilleureMethode = TypeDistance.Manhattan;
		ArrayList<Cell> meilleureSolution = null;
		for (TypeDistance method : tabMethods) {
			ent.removeVisit();
			if (goal != null) {
				goal.reset();
			}
			typeDistance = method;
			ArrayList<Cell> solution = algoASTAR(ent.getStart(), ent.getGoal());
			ent.setSolution(solution);
			if (solution == null) {
				System.out.println("solution IMPOSSIBLE");
			} else {
				int nbNoeudsClos = closedNodes.size();
				int nbNoeudsLibres = freeNodes.size();
				int distance = goal.getF();
				if (distance < distanceMin) {
					nbNoeudsClosMin = nbNoeudsClos;
					nbNoeudsLibresMin = nbNoeudsLibres;
					meilleureMethode = method;
					distanceMin = distance;
					meilleureSolution = solution;
				}
				String chaine = " methode " + method + "; nb de noeuds clos =  " + nbNoeudsClos
						+ "; nb de noeuds libres =  " + nbNoeudsLibres + "; distance =  " + distance;
				System.out.println(chaine);
				gui.repaint();
			}
		}
		String chaine = "meilleure methode " + meilleureMethode + "; nb de noeuds clos =  " + nbNoeudsClosMin
				+ "; nb de noeuds libres =  " + nbNoeudsLibresMin + "; distance =  " + distanceMin;
		System.out.println(chaine);
		System.out.println();
		typeDistance = meilleureMethode;
		ent.removeVisit();
		ent.setSolution(meilleureSolution);
		gui.repaint();

	}

	/**
	 * @param typeDistance
	 *            the typeDistance to set
	 */
	public void setTypeDistance(TypeDistance typeDistance) {
		this.typeDistance = typeDistance;
	}

	/**
	 * @param typeNeighborhood
	 *            the typeneighborhood to set
	 */
	public void setTypeneighborhood(TypeNeighborhood _typeneighborhood) {
		// System.err.println(""+this.getClass().getName() + "::"
		// +_typeneighborhood );
		typeNeighborhood = _typeneighborhood;
	}

}
