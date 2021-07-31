package Models.Game;

import Utils.RandomGenerator;

import java.util.ArrayList;

public class ShipLayout {
    public ArrayList<Ship> ships;

    public ShipLayout() {
        ships = new ArrayList<>();
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(3));
        ships.add(new Ship(3));
        ships.add(new Ship(4));
        for (int z = 0; z < ships.size(); z++) {
            Ship ship = ships.get(z);
            Grid grid1 = new Grid();
            while(true) {
                if (grid1.cells.size() == 0) {
                    grid1 = new Grid();
                    again();
                }
                Coordinate firstCell = grid1.getRandom();
                grid1.cells.remove(firstCell);
                Grid grid = new Grid();
                Boolean vert = true, horz = true;
                for (int i = 0; i < ship.size; i++) {
                    Coordinate v = grid.getCell(firstCell.x, firstCell.y + i);
                    Coordinate h = grid.getCell(firstCell.x + i, firstCell.y);
                    if (v == null) {
                        vert = false;
                    } else {
                        for (int j = 0; j < z; j++) {
                            for (Coordinate tmp : ships.get(j).ship) {
                                if (Math.abs(tmp.x - v.x) + Math.abs(tmp.y - v.y) <= 1 ||
                                        ( Math.abs(tmp.x - v.x) == 1 && Math.abs(tmp.y - v.y) == 1 )) {
                                    vert = false;
                                }
                            }
                        }
                    }
                    if (h == null) {
                        horz = false;
                    } else {
                        for (int j = 0; j < z; j++) {
                            for (Coordinate tmp : ships.get(j).ship) {
                                if (Math.abs(tmp.x - h.x) + Math.abs(tmp.y - h.y) <= 1 ||
                                        ( Math.abs(tmp.x - h.x) == 1 && Math.abs(tmp.y - h.y) == 1 )) {
                                    horz = false;
                                }
                            }
                        }
                    }
                }
                if (!( vert || horz )) {
                    continue;
                }
                if (vert && horz) {
                    if (RandomGenerator.RandomInt(2) == 1)
                        horz = false;
                    else
                        vert = false;
                }

                if (horz) {
                    for (int i = 0; i < ship.size; i++) {
                        Coordinate h = grid.getCell(firstCell.x + i, firstCell.y);
                        ship.ship.add(h);
                    }
                    ship.orientation = Orientation.Horizontal;
                }
                else {
                    for (int i = 0; i < ship.size; i++) {
                        Coordinate v = grid.getCell(firstCell.x, firstCell.y + i);
                        ship.ship.add(v);
                    }
                    ship.orientation = Orientation.Vertical;
                }
                break;
            }
        }
    }

    public void again() {
        ships = new ArrayList<>();
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(1));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(2));
        ships.add(new Ship(3));
        ships.add(new Ship(3));
        ships.add(new Ship(4));
        for (int z = 0; z < ships.size(); z++) {
            Ship ship = ships.get(z);
            Grid grid1 = new Grid();
            while(true) {
                if (grid1.cells.size() == 0) {
                    grid1 = new Grid();
                    again();
                }
                Coordinate firstCell = grid1.getRandom();
                grid1.cells.remove(firstCell);
                Grid grid = new Grid();
                Boolean vert = true, horz = true;
                for (int i = 0; i < ship.size; i++) {
                    Coordinate v = grid.getCell(firstCell.x, firstCell.y + i);
                    Coordinate h = grid.getCell(firstCell.x + i, firstCell.y);
                    if (v == null) {
                        vert = false;
                    } else {
                        for (int j = 0; j < z; j++) {
                            for (Coordinate tmp : ships.get(z).ship) {
                                if (Math.abs(tmp.x - v.x) + Math.abs(tmp.y - v.y) <= 1 ||
                                        ( Math.abs(tmp.x - v.x) == 1 && Math.abs(tmp.y - v.y) == 1 )) {
                                    vert = false;
                                }
                            }
                        }
                    }
                    if (h == null) {
                        horz = false;
                    } else {
                        for (int j = 0; j < z; j++) {
                            for (Coordinate tmp : ships.get(z).ship) {
                                if (Math.abs(tmp.x - h.x) + Math.abs(tmp.y - h.y) <= 1 ||
                                        ( Math.abs(tmp.x - h.x) == 1 && Math.abs(tmp.y - h.y) == 1 )) {
                                    horz = false;
                                }
                            }
                        }
                    }
                }
                if (!( vert || horz )) {
                    continue;
                }
                if (vert && horz) {
                    if (RandomGenerator.RandomInt(2) == 1)
                        horz = false;
                    else
                        vert = false;
                }

                if (horz) {
                    for (int i = 0; i < ship.size; i++) {
                        Coordinate h = grid.getCell(firstCell.x + i, firstCell.y);
                        ship.ship.add(h);
                    }
                    ship.orientation = Orientation.Horizontal;
                }
                else {
                    for (int i = 0; i < ship.size; i++) {
                        Coordinate v = grid.getCell(firstCell.x, firstCell.y + i);
                        ship.ship.add(v);
                    }
                    ship.orientation = Orientation.Vertical;
                }
                break;
            }
        }
    }
}
