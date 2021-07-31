package Models.Live;

import Models.Game.Player;

public class PlayerListMode {
    public int landedShots, missedShots, killedShips, Ships;
    public String username;
    public PlayerListMode(Player player) {
        landedShots = player.landedShots.size();
        missedShots = player.missedShots.size();
        killedShips = player.BrokeShips.size();
        Ships = player.shipLayout.ships.size();
        username = player.username;
    }
}
