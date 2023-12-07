package org.example.frontend;

import java.util.Random;

public class CardMonster extends Card {

    CardMonster(int damage, ElementType et)
    {
        super(damage, et);
    }
    public void makeDamage()
    {
        System.out.println("make 1000000 monster damage");
    }
}
