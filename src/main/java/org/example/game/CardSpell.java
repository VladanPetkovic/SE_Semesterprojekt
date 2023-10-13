package org.example.game;

public class CardSpell extends Card {

    CardSpell(int damage, ElementType et)
    {
        super(damage, et);
        if(et == ElementType.FIRE) {
            this.setName("FireSpell");
        } else if(et == ElementType.NORMAL) {
            this.setName("RegularSpell");
        } else {
            this.setName("WaterSpell");
        }
    }
    public void makeDamage()
    {
        System.out.println("make 100 spelling damage");
    }
}
