package Models.Game;

import java.util.ArrayList;

public class Ship {
    public ArrayList<Coordinate> ship;
    public int size;
    public Orientation orientation;
    public Ship(int size) {
        this.size = size;
        ship = new ArrayList<>();
    }
}
