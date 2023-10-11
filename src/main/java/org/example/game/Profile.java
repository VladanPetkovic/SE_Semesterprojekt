package org.example.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {
        private String username;
        private String password;
        private int coins;
        private int eloPoints;
        private static int profileCount;

        Profile()
        {
                profileCount += 1;
        }
}
