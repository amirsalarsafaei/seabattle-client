package Models.Game;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Player {
    public String username, versusUsername;
    public ArrayList<Coordinate> landedShots, missedShots, enemyLandedShots, enemyMissedShots;
    public ArrayList<Ship> BrokeShips;
    public ShipLayout shipLayout;
    public int GameID;
    public GridChoice gridChoice;
    public int timesReArranged;
    public GamePhase gamePhase;
    public LocalDateTime turnEndTime;
    public Player(String username, String versusUsername,int id, GridChoice gridChoice) {
        this.gridChoice =gridChoice;
        this.username = username;
        gamePhase = GamePhase.Arranging;
        timesReArranged = 0;
        GameID = id;
        landedShots = new ArrayList<>();
        missedShots = new ArrayList<>();
        enemyLandedShots = new ArrayList<>();
        enemyMissedShots = new ArrayList<>();
        BrokeShips = new ArrayList<>();
        shipLayout = new ShipLayout();
        turnEndTime = LocalDateTime.now().plusSeconds(30);
    }
}
