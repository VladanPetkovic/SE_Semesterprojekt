package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {
        private String username;
        private String password;
        private short coins;
        private short eloPoints;
        private static short profileCount;

        Profile()
        {
            profileCount += 1;
        }
        public void decreaseEloPoints(short number)
        {
            this.eloPoints -= number;
        }
        public void increaseEloPoints(short number)
        {
            this.eloPoints += number;
        }
}
