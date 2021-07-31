package Models.Profile;

import Models.User;

public class Profile {
    public String username;
    public int wins, loses, points;

    public Profile(User user) {
        username = user.getUsername();
        wins = user.wins;
        loses = user.loses;
        points = wins - loses;
    }
}
