package org.example.frontend;

public class CardSpell extends Card {

    CardSpell(int damage, ElementType et)
    {
        super(damage, et);
    }
    public void makeDamage()
    {
        System.out.println("make 100 spelling damage");
    }
}
