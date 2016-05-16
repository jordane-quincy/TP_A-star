package algo;

import java.util.Arrays;
import java.util.List;

import environment.Cell;

public class TestDirection {

	public static void main(String[] args) {

		// testDirection(Arrays.asList(Direction.NE));
		testDirection(Arrays.asList(Direction.values()));
	}

	public static void testDirection(List<Direction> lstDirectionToTest) {
		for (Direction prevDir : lstDirectionToTest) {
			if (((int) prevDir.getValue() == prevDir.getValue())) {
				System.out.println("\t" + prevDir);
				Cell start = new Cell(20, 10);
				for (int i = -2; i <= 2; i++) {
					for (int j = -2; j <= 2; j++) {
						int x = start.getX() + i;
						int y = start.getY() + j;
						Cell end = new Cell(x, y);
						Direction direction = Direction.getDirection(start, end);
						if (prevDir.isAcceptableDirection(direction)) {
							System.out.println(start + " ->" + end + " : " + direction);
						}
					}
				}
			}
		}
	}

}
