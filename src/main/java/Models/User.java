package Models;

import Models.BaseUser;

import java.time.LocalDateTime;

public class User extends BaseUser {

    public int wins, loses;

    public LocalDateTime lastOnline;

    public User(String username, String password) {

        super(username, password);
        wins = loses = 0;
    }

    public String getUsername() { return username; }

}

