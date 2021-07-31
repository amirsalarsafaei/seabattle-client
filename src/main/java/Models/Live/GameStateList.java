package Models.Live;

import Models.Game.GameState;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameStateList {
    public ArrayList<GameStateListMode> gameStateLists;

    public GameStateList(ArrayList<GameState> gameStates) {
        for (GameState gameState:gameStates) {
            gameStateLists.add(new GameStateListMode(gameState));
        }
    }
}
