package org.example.backend.app.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.Card;
import org.example.backend.daos.CardDAO;

import java.util.ArrayList;

public class CardRepository implements Repository<Card> {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    CardDAO cardDAO;

    public CardRepository(CardDAO cardDAO) {
        setCardDAO(cardDAO);
    }

    @Override
    public Card get(int id) {
        // NOT WORKING --> purpose
        return getCardDAO().read(id);
    }

    public Card get(String card_id) {
        return getCardDAO().read(card_id);
    }

    @Override
    public ArrayList<Card> getAll() {
        return getCardDAO().readAll();
    }

    public ArrayList<Card> getAll(int user_id) {
        return getCardDAO().readAll(user_id);
    }

    @Override
    public void add(Card card) {
        getCardDAO().create(card);
    }

    @Override
    public void update(Card card) {
        getCardDAO().update(card);
    }

    @Override
    public void remove(int id) {
        // NOT WORKING --> purpose
        getCardDAO().delete(id);
    }

    public void remove(String card_id) {
        getCardDAO().delete(card_id);
    }
}
