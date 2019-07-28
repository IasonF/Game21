package com.objectivepartners.game21.model;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void testWin() {
        List<Rank> cards = new LinkedList<>();
        cards.add(Rank.ACE);
        cards.add(Rank.ACE);
        cards.add(Rank.NINE);
        cards.add(Rank.EIGHT);
        Game g = new Game(new Deck(cards));
        g.hit();
        g.stand();
        assertTrue(g.currentState().getState() == State.WIN);
    }

    @Test
    public void testLose() {
        List<Rank> cards = new LinkedList<>();
        cards.add(Rank.ACE);
        cards.add(Rank.ACE);
        cards.add(Rank.EIGHT);
        cards.add(Rank.NINE);
        Game g = new Game(new Deck(cards));
        g.hit();
        g.stand();
        assertTrue(g.currentState().getState() == State.LOSE);
    }

    @Test
    public void testBothHit21() {
        List<Rank> cards = new LinkedList<>();
        cards.add(Rank.ACE);
        cards.add(Rank.ACE);
        cards.add(Rank.TEN);
        cards.add(Rank.TEN);
        Game g = new Game(new Deck(cards));
        g.hit();
        assertTrue(g.currentState().getState() == State.LOSE);
    }

    @Test
    public void testSplitDraw() {
        List<Rank> cards = new LinkedList<>();
        cards.add(Rank.ACE);
        cards.add(Rank.ACE);
        cards.add(Rank.ACE);
        cards.add(Rank.TEN);
        cards.add(Rank.NINE);
        cards.add(Rank.EIGHT);
        cards.add(Rank.EIGHT);
        Game g = new Game(new Deck(cards));
        g.hit();
        g.split();
        g.hit();
        g.hit();
        g.stand();
        assertTrue(g.currentState().getState() == State.WIN);
    }

    @Test
    public void testSplitWin() {
        List<Rank> cards = new LinkedList<>();
        cards.add(Rank.ACE);
        cards.add(Rank.ACE);
        cards.add(Rank.ACE);
        cards.add(Rank.TEN);
        cards.add(Rank.NINE);
        cards.add(Rank.EIGHT);
        cards.add(Rank.EIGHT);
        Game g = new Game(new Deck(cards));
        g.hit();
        g.split();
        g.hit();
        g.hit();
        g.stand();
        assertTrue(g.currentState().getState() == State.WIN);
    }

//    @Test
//    public void testSplitLose() {
//        List<Card> cards = new LinkedList<>();
//        cards.add(new Card(Rank.ACE));
//        cards.add(new Card(Rank.ACE));
//        cards.add(new Card(Rank.ACE));
//        cards.add(new Card(Rank.SEVEN));
//        cards.add(new Card(Rank.FIVE));
//        cards.add(new Card(Rank.NINE));
//        Game g = new Game(new Deck(cards));
//        g.hit();
//        g.split();
//        g.hit();
//        g.stand();
//        g.hit();
//        g.stand();
//        assertTrue(g.currentState().getState() == State.LOSE);
//    }


}