package Models.Live;

import Models.Game.GameState;

public class GameStateListMode {
    public PlayerListMode player1, player2;
    public int gameID;
    public GameStateListMode(GameState gameState) {
        player1 = new PlayerListMode(gameState.player1);
        player2 = new PlayerListMode(gameState.player2);
        gameID = gameState.player1.GameID;
    }

}
