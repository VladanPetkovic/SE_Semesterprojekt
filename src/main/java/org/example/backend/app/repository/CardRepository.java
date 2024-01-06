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

    public ArrayList<Card> getDeck(int user_id) {
        return getCardDAO().readDeck(user_id);
    }

    public void updateDeck(String card_id) {
        // sets card_id in deck = true
        getCardDAO().update(card_id);
    }

    public void removeDeck(int user_id) {
        // sets all deck = false for a user_id
        getCardDAO().removeDeck(user_id);
    }

    public ArrayList<Card> getPackage() {
        return getCardDAO().readPackage();
    }

    public void updatePackage(int user_id) {
        getCardDAO().update(user_id);
    }

    @Override
    public void add(Card card) {
        getCardDAO().create(card);
    }

    @Override
    public void update(Card card) {
        getCardDAO().update(card);
    }

    public void update(String card_id, int user_id) {
        getCardDAO().update(card_id, user_id);
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
