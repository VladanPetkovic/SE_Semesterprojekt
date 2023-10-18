package org.example.frontend;

import java.util.Random;

public class CardMonster extends Card {

    CardMonster(int damage, ElementType et)
    {
        super(damage, et);
        this.setName(getRandomName());
    }
    private String getRandomName()
    {
        Random random = new Random();
        int randomName = random.nextInt(1,7 + 1);   // we have seven names

        if(randomName == 1) {
            return "Goblin";
        } else if(randomName == 2) {
            return "Dragon";
        } else if(randomName == 3) {
            return "Wizard";
        } else if(randomName == 4) {
            return "Ork";
        } else if(randomName == 5) {
            return "Knights";
        } else if(randomName == 6) {
            return "Kraken";
        } else {
            return "Elves";
        }
    }
    public void makeDamage()
    {
        System.out.println("make 1000000 monster damage");
    }
}
