package org.example.frontend;

public class Shop {
    private static final int cardsInPackage = 5;
    private static final int priceForPackage = 5;
    private ElementType getElementType(int randomElement)
    {
        if(randomElement == 1) {
            return ElementType.FIRE;
        } else if(randomElement == 2) {
            return ElementType.WATER;
        } else {
            return ElementType.NORMAL;
        }
    }
    public void tradeCards(Card cardToBeRemoved)
    {

    }
}
