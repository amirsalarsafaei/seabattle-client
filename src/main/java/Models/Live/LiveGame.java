package Models.Live;

import Models.Game.Coordinate;
import Models.Game.GamePhase;
import Models.Game.GameState;
import Models.Game.Ship;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LiveGame {
    public ArrayList<Coordinate> rightLandedShots;
    public ArrayList<Coordinate> rightMissedShots;
    public ArrayList<Ship> rightDeadShips;
    public ArrayList<Coordinate> leftLandedShots;
    public ArrayList<Coordinate> leftMissedShots;
    public ArrayList<Ship> leftDeadShips;
    public LocalDateTime localDateTime;
    public GamePhase gamePhase;
    public String rightPlayer, leftPlayer;
    public LiveGame(GameState gameState) {
        leftDeadShips = gameState.player2.BrokeShips;
        leftMissedShots = gameState.player2.missedShots;
        leftLandedShots = gameState.player2.landedShots;
        rightDeadShips = gameState.player1.BrokeShips;
        rightMissedShots = gameState.player1.missedShots;
        rightLandedShots = gameState.player1.landedShots;
        localDateTime = gameState.player1.turnEndTime;
        gamePhase = gameState.player1.gamePhase;
        leftPlayer = gameState.player1.username;
        rightPlayer = gameState.player2.username;
    }
}
