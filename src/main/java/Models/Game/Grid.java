package Models.Game;

import Utils.RandomGenerator;

import java.util.ArrayList;

public class Grid {
    ArrayList<Coordinate> cells;
    public Grid() {
        cells = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                cells.add(new Coordinate(i, j));
    }

    public boolean cellExist(int x, int y) {
        return Math.min(x, y) >= 0 && Math.max(x, y) < 10;
    }

    public Coordinate getCell(int x, int y) {
        if (!cellExist(x, y))
            return null;
        for (Coordinate cell: cells)
            if (cell.x == x && cell.y == y)
                return cell;
        return null;
    }

    public Coordinate getRandom() {
        return cells.get(RandomGenerator.RandomInt(cells.size()));
    }
}
