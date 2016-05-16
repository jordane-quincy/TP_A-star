package algo;

import java.util.ArrayList;

import algo.actions.TypeNeighborhood;
import environment.Cell;
import environment.Entrepot;

public class TestBB8 {

	public static void main(String[] args) {
		AlgoAStar algo = new AlgoAStar();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		algo.setTypeneighborhood(TypeNeighborhood.BB8);
		Entrepot ent = algo.ent;
		Cell start = ent.getCell(20, 10);
		start.setParent(null);
		ent.setStart(start);
		Cell goal = ent.getCell(21, 15);// ent.getCell(24, 10);
										// ent.getCell(24, 14);
		ent.setGoal(goal);
		ent.setDensity(0D);

		algo.reCompute();

		ArrayList<Cell> solution = ent.getSolution();
		System.out.println("Parcours solution :\n" + ent.getStart());
		for (Cell cs : solution) {
			System.out.println(cs);
		}

	}

}
