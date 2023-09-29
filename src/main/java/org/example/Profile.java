package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {
        private String username;
        private String password;
        private short coins;
        private int eloPoints;
        private static short profileCount;

        Profile()
        {
            profileCount += 1;
        }
}
